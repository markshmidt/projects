package Board;

//  Group Project COMP 2080
//  Eduard Kosenko 101480050
//  Evgenii Baldin 101435160
//  Leonard Eriyo 101511592
//  Mariia Shmidt 101470474
//  Mateus Sfeir 101484904


public class Board implements IBoard, Cloneable {
    private int size;
    private Cell[][] board;
    private int moveCount;

    private Cell currentMove;
    private Cell winner = null;

    public Board(int size) {
        this.size = size;
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            board[i] = new Cell[size];
            for (int j = 0; j < size; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
        currentMove = Cell.BLACK;
    }

    /**
     * @return true if the move was winning for the player, otherwise false
     */
    public boolean makeMove(Move move) {
        var row = move.row();
        var col = move.col();
        var cell = move.cell();

        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Row or col out of bounds");
        }
        if (currentMove != cell) {
            throw new IllegalArgumentException("It is not the turn for the " + cell.getValue());
        }

        if (board[row][col] != Cell.EMPTY) {
            throw new IllegalArgumentException("Cell is already occupied");
        }

        board[row][col] = cell;
        moveCount++;

        if (checkWin(row, col)) {
            winner = cell;
            return true;
        }

        if (currentMove == Cell.BLACK)
            currentMove = Cell.WHITE;
        else
            currentMove = Cell.BLACK;
        return false;
    }

    public Cell[][] getState() {
        return this.clone().board;
    }

    public Cell getWinner() {
        return winner;
    }

    public Cell getCurrentMove() {
        return currentMove;
    }

    public boolean isFull() {
        return moveCount >= size * size;
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public boolean checkWin(int row, int col) {
        var piece = board[row][col];
        var directions = new int[][]{{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (var dir : directions) {
            var count = 1;
            count += countInDirection(row, col, dir[0], dir[1], piece);
            count += countInDirection(row, col, -dir[0], -dir[1], piece);

            if (count >= 5) {
                return true;
            }
        }
        return false;
    }

    private int countInDirection(int row, int col, int dx, int dy, Cell cell) {
        var count = 0;
        var x = row + dx;
        var y = col + dy;
        while (x >= 0 && x < this.size && y >= 0 && y < this.size && board[x][y] == cell) {
            count++;
            x += dx;
            y += dy;
        }
        return count;
    }

    @Override
    public Board clone() {
        Board nBoard;
        try {
            nBoard = (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        nBoard.size = size;
        nBoard.board = new Cell[size][size];
        nBoard.currentMove = currentMove;
        nBoard.winner = winner;
        for (int i = 0; i < this.board.length; i++) {
            nBoard.board[i] = board[i].clone();
        }
        return nBoard;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (Cell[] row : board) {
            for (Cell cell : row) {
                result = 31 * result + cell.getValue();
            }
        }
        result = result + currentMove.getValue();
        return result;
    }

    public Board copy(){
        Board copy = new Board(size);
        for (Cell[] row : board) {
            for (Cell cell : row) {
                copy.board[cell.getValue()][cell.getValue()] = cell;
            }
        }
        return copy;
    }

    // Debug set tile
    public void setTile(int row, int col, Cell cell) {
        board[row][col] = cell;
    }
}
