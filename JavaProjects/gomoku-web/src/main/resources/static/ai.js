
document.addEventListener("DOMContentLoaded", () => {
    // Getting all DOM-elements
    const boardContainer = document.getElementById("gameBoard");
        const playBtn = document.getElementById("playBtn");
        if (playBtn) {
            playBtn.onclick = startGame;
        } else {
            console.error("Кнопка playBtn не найдена в DOM");
        }


    const restartBtn = document.getElementById("restartButton");
    const statusMessage = document.getElementById("statusMessage");
    const winnerMessage = document.getElementById("winnerMessage");
    const symbolSelect = document.getElementById("playerSymbol");

    // Player's symbol (BLACK or WHITE)
    let playerSymbol = null;
    let waiting = false;

    // Drawing board
    function drawBoard(state) {
        boardContainer.innerHTML = "";
        const table = document.createElement("table");

        for (let i = 0; i < state.length; i++) {
            const row = document.createElement("tr");
            for (let j = 0; j < state[i].length; j++) {
                const cell = document.createElement("td");
                const value = state[i][j];
                if (value === "BLACK") {
                    const img = document.createElement("img");
                    img.src = "/images/black.png";
                    img.classList.add("piece");
                    cell.appendChild(img);
                } else if (value === "WHITE") {
                    const img = document.createElement("img");
                    img.src = "/images/white.png";
                    img.classList.add("piece");
                    cell.appendChild(img);
                }


                cell.addEventListener("click", () => {
                    if (!waiting && state[i][j] === "EMPTY") {
                        makeMove(i, j);
                    }
                });

                row.appendChild(cell);
            }
            table.appendChild(row);
        }

        boardContainer.appendChild(table);
    }

    // Receiving JSON state from server
    function fetchState() {
        fetch("/api/ai/state")
            .then(res => res.json())
            .then(data => {
                console.log("DEBUG fetchState():", data);
                const boardState = data.state || data;
                drawBoard(boardState);
                if (statusMessage)
                    statusMessage.textContent = `Current move: ${data.currentMove || "..."}`;
                if (data.winner && data.winner !== "EMPTY") {
                    winnerMessage.textContent = `Winner is: ${data.winner}`;
                    statusMessage.textContent = "";
                } else if (data.full && !data.winner) {
                    winnerMessage.textContent = "It's a draw!";
                    statusMessage.textContent = "";
                }
            });
    }

    // Player's move
    function makeMove(row, col) {
        if (waiting) return;
        waiting = true;

        // Put player's move locally into DOM before server's answer
        const cell = boardContainer.querySelectorAll("tr")[row].children[col];
        const img = document.createElement("img");
        img.src = playerSymbol === "BLACK" ? "/images/black.png" : "/images/white.png";
        img.classList.add("piece");
        cell.appendChild(img);


        // "AI thinking" animation
        statusMessage.textContent = "AI is thinking...";
        let dots = 0;
        aiThinkingInterval = setInterval(() => {
            dots = (dots + 1) % 4;
            statusMessage.textContent = "Ai is thinking" + ".".repeat(dots);
        }, 400);

        // Send player's move to server (coordinates and cell index)
        const cellIndex = playerSymbol === "BLACK" ? 0 : 1;
        fetch("/api/ai/move", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ row, col, cell: cellIndex })
        })
            .then(res => res.json())
            .then(() => {
                // After Ai response
                setTimeout(() => {
                    clearInterval(aiThinkingInterval);
                    aiThinkingInterval = null;
                    fetchState();
                    waiting = false;
                }, 300);
            });
    }



    // Start game
    function startGame() {
        const symbol = symbolSelect.value;
        playerSymbol = symbol;

        fetch("/api/ai/start", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(symbol)
        }).then(() => {
            winnerMessage.textContent = "";

            fetchState();
        });
    }

    // Reset the game
    function resetGame() {
        fetch("/api/ai/reset", {
            method: "POST"
        }).then(() => {
            winnerMessage.textContent = "";
            statusMessage.textContent = "Choose your color:";
            document.getElementById("gameArea").style.display = "none";
            playBtn.style.display = "inline-block";
            restartBtn.style.display = "none";
            symbolSelect.style.display = "inline-block";

            const title = document.getElementById("aiTitle");
            if (title) title.style.display = "block";

            fetchState();
        });
    }
    restartBtn.onclick = resetGame;

});
