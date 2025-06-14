document.addEventListener("DOMContentLoaded", () => {
    const boardContainer = document.getElementById("pvpBoard");
    const statusMessage = document.getElementById("pvpStatus");
    const winnerMessage = document.getElementById("pvpWinner");
    const restartBtn = document.getElementById("pvpRestart");
    let gameState = {};

    let currentPlayer = "BLACK";

    restartBtn.style.display = "none";

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
                    img.src = "images/black.png";
                    img.classList.add("piece");
                    cell.appendChild(img);
                } else if (value === "WHITE") {
                    const img = document.createElement("img");
                    img.src = "images/white.png";
                    img.classList.add("piece");
                    cell.appendChild(img);
                }


                // Click event
                cell.addEventListener("click", () => {
                    if (value === "EMPTY") {
                        makeMove(i, j);
                    }
                });

                row.appendChild(cell);
            }
            table.appendChild(row);
        }

        boardContainer.appendChild(table);
    }
    const basePath = window.location.pathname.replace(/\/[^\/]*$/, "/");

    function fetchState() {
        fetch(basePath+"api/pvp/state")
            .then(res => res.json())
            .then(data => {
                gameState = data;
                drawBoard(data.state || data);

                if (data.winner && data.winner !== "EMPTY") {
                    winnerMessage.textContent = `The winner is: ${data.winner}`;
                    statusMessage.textContent = "";
                    boardContainer.classList.add("disabled");
                } else if (data.full && !data.winner) {
                    winnerMessage.textContent = "It's a draw!";
                    statusMessage.textContent = "";
                    boardContainer.classList.add("disabled");
                } else {
                    statusMessage.textContent = `Turn: ${data.currentMove}`;
                    boardContainer.classList.remove("disabled");
                }
            });
    }

    function makeMove(row, col) {
        const cellIndex = currentPlayer === "BLACK" ? 0 : 1;
        if (gameState.winner && gameState.winner !== "EMPTY") {
            console.warn("no more moves allowed");
            return;
        }


        fetch("api/pvp/move", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ row, col, cell: cellIndex })
        })
            .then(res => res.json())
            .then(data => {
                fetchState();
                restartBtn.style.display = "inline";

                currentPlayer = currentPlayer === "BLACK" ? "WHITE" : "BLACK";
            });
    }

    function resetGame() {
        fetch("api/pvp/reset", {
            method: "POST"
        }).then(() => {
            currentPlayer = "BLACK";
            winnerMessage.textContent = "";
            statusMessage.textContent = "Turn: BLACK";
            restartBtn.style.display = "none";
            fetchState();
        });
    }

    restartBtn.onclick = resetGame;

    fetchState();
});
