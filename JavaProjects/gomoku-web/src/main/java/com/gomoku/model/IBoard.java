package com.gomoku.model;


public interface IBoard {
    boolean makeMove(Move move);
    int getSize();
    Board copy();
    Cell getCell(int row, int col);
    Cell[][] getState();
    Cell getWinner();
}