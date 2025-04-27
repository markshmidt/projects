package AI;

import Board.Board;
import Board.Cell;

public interface AII {

    public int[] getNextMove(Board board, Cell cellType);

}