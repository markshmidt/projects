const BOARD_SIZE = 9;
const boardEl = document.getElementById("board");
const turnEl = document.getElementById("turn");


function drawBoard(state) {
    boardEl.innerHTML = "";

    for (let row = 0; row < BOARD_SIZE; row++) {
        const tr = document.createElement("tr");
        for (let col = 0; col < BOARD_SIZE; col++) {
            const td = document.createElement("td");
            const cell = state[row][col];

            td.textContent = cell === "EMPTY" ? "" : (cell === "BLACK" ? "●" : "○");
            td.dataset.row = row;
            td.dataset.col = col;

            td.addEventListener("click", () => makeMove(row, col));

            tr.appendChild(td);
        }
        boardEl.appendChild(tr);
    }
}

function fetchState() {
    fetch("/api/state")
        .then(response => response.json())
        .then(data => {
            drawBoard(data.state);
            turnEl.textContent = data.currentMove;
        });
}

function makeMove(row, col) {
    fetch("/api/state")
        .then(response => response.json())
        .then(data => {
            const current = data.currentMove;
            fetch("/api/move", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ row, col, cell: current })
            }).then(() => {
                setTimeout(fetchState, 200);
            });
        });
}

fetchState();
