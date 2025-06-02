package com.gomoku.ai;

import com.gomoku.model.Board;
import com.gomoku.model.Cell;

public interface AII {

    public int[] getNextMove(Board board, Cell cellType);

}