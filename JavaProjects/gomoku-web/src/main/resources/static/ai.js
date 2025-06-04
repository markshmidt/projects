
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

                if (value === "BLACK") cell.textContent = "●";
                else if (value === "WHITE") cell.textContent = "○";

                // Player's move
                cell.addEventListener("click", () => {
                    if (!waiting && value === "EMPTY") {
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
        cell.textContent = playerSymbol === "BLACK" ? "●" : "○";

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
            statusMessage.textContent = `The game has started! Your color is: ${playerSymbol}`;
            winnerMessage.textContent = "";

            document.getElementById("gameArea").style.display = "block";
            playBtn.style.display = "none";
            restartBtn.style.display = "inline";
            symbolSelect.style.display = "none";
            document.querySelector("h2").style.display = "none";

            fetchState();
        });
    }

    // Reset the game
    function resetGame() {
        fetch("/api/ai/reset", {
            method: "POST"
        }).then(() => {
            winnerMessage.textContent = "";

            fetchState();
        });
    }
    restartBtn.onclick = resetGame;

});
