const BOARD_SIZE = 9;
const boardEl = document.getElementById("board");
const statusEl = document.getElementById("status");

let currentPlayer = "BLACK";

function drawBoard(state) {
    boardEl.innerHTML = "";
    const table = document.createElement("table");

    for (let i = 0; i < state.length; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < state[i].length; j++) {
            const cell = document.createElement("td");
            const value = state[i][j];
            if (value === "BLACK") cell.textContent = "●";
            else if (value === "WHITE") cell.textContent = "○";

            if (value === "EMPTY") {
                cell.addEventListener("click", () => {
                    makeMove(i, j);
                });
            }

            row.appendChild(cell);
        }
        table.appendChild(row);
    }

    boardEl.appendChild(table);
}

function fetchState() {
    fetch("/api/pvp/state")
        .then(res => res.json())
        .then(data => {
            drawBoard(data.state);
            statusEl.textContent = `Ходит: ${data.currentMove}`;
        });
}

function makeMove(row, col) {
    const cell = currentPlayer === "BLACK" ? 0 : 1;

    fetch("/api/pvp/move", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ row, col, cell })
    })
        .then(() => {
            currentPlayer = currentPlayer === "BLACK" ? "WHITE" : "BLACK";
            fetchState();
        });
}

function restartGame() {
    fetch("/api/pvp/reset", { method: "POST" }).then(fetchState);
}

fetchState();
