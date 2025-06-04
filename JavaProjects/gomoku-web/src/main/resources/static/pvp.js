document.addEventListener("DOMContentLoaded", () => {
    const boardContainer = document.getElementById("pvpBoard");
    const statusMessage = document.getElementById("pvpStatus");
    const winnerMessage = document.getElementById("pvpWinner");
    const restartBtn = document.getElementById("pvpRestart");

    let currentPlayer = "BLACK"; // Начинаем с чёрных

    restartBtn.style.display = "none"; // ❌ Скрываем кнопку при загрузке

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

                // Событие на клик
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

    function fetchState() {
        fetch("/api/pvp/state")
            .then(res => res.json())
            .then(data => {
                drawBoard(data.state || data);

                if (data.winner && data.winner !== "EMPTY") {
                    winnerMessage.textContent = `The winner is: ${data.winner}`;
                    statusMessage.textContent = "";
                } else if (data.full && !data.winner) {
                    winnerMessage.textContent = "It's a draw!";
                    statusMessage.textContent = "";
                } else {
                    statusMessage.textContent = `Turn: ${data.currentMove}`;
                }
            });
    }

    function makeMove(row, col) {
        const cellIndex = currentPlayer === "BLACK" ? 0 : 1;

        fetch("/api/pvp/move", {
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
        fetch("/api/pvp/reset", {
            method: "POST"
        }).then(() => {
            currentPlayer = "BLACK";
            winnerMessage.textContent = "";
            statusMessage.textContent = "Текущий ход: BLACK";
            restartBtn.style.display = "none";
            fetchState();
        });
    }

    restartBtn.onclick = resetGame;

    fetchState();
});
