package com.gomoku.ai;

import java.util.Date;

import com.gomoku.model.Board;
import com.gomoku.model.Cell;

public class AiTest {
    public static void main(String[] args) {
        AI ai = new AI();


//        //Evaluation Tests
//        // Five in a row
//        Board board5 = new Board(9);
//        board5.setTile(0, 0, Cell.BLACK);
//        board5.setTile(1, 0, Cell.BLACK);
//        board5.setTile(2, 0, Cell.BLACK);
//        board5.setTile(3, 0, Cell.BLACK);
//        board5.setTile(4, 0, Cell.BLACK);
//        int boardScore = ai.boardEvaluation(board5);
//        System.out.println("Five in a row: " + boardScore);
//
//        // Four
//        Board board4= new Board(9);
//        board4.setTile(0, 0, Cell.BLACK);
//        board4.setTile(1, 0, Cell.BLACK);
//        board4.setTile(2, 0, Cell.BLACK);
//        board4.setTile(3, 0, Cell.BLACK);
//        boardScore = ai.boardEvaluation(board4);
//        System.out.println("Four in a column: " + boardScore);
//
//        // Three
//        Board board3= new Board(9);
//        board3.setTile(0, 0, Cell.BLACK);
//        board3.setTile(1, 0, Cell.BLACK);
//        board3.setTile(2, 0, Cell.BLACK);
//        boardScore = ai.boardEvaluation(board3);
//        System.out.println("Three in a column: " + boardScore);
//
//        // Two open threes
//        Board board23 = new Board(9);
//        board23.setTile(1, 0, Cell.BLACK);
//        board23.setTile(2, 0, Cell.BLACK);
//        board23.setTile(3, 0, Cell.BLACK);
//        board23.setTile(1, 5, Cell.BLACK);
//        board23.setTile(2, 5, Cell.BLACK);
//        board23.setTile(3, 5, Cell.BLACK);
//        boardScore = ai.boardEvaluation(board23);
//        System.out.println("Two open threes: " + boardScore);

        // Next move tests:

        // For in a row next move
        Board board1 = new Board(9);
//        board1.setTile(1, 0, Cell.BLACK);
//        board1.setTile(2, 0, Cell.BLACK);
//        board1.setTile(3, 0, Cell.BLACK);
//        board1.setTile(4, 0, Cell.BLACK);

//        int[][] sortedPossibleMoves = ai.getSortedPossibleMoves(board1, Cell.BLACK);
//        for (int i = 0; i < sortedPossibleMoves.length; i++) {
//            System.out.println("Move: " + sortedPossibleMoves[i][0] + " " + sortedPossibleMoves[i][1] + " " + sortedPossibleMoves[i][2]);
//        }

        Date start = new Date();
        int[] whileBest1 = ai.getNextMove(board1, Cell.WHITE);
        System.out.println("Best move for WHITE: " + whileBest1[0] + " " + whileBest1[1]);
        Date end = new Date();
        System.out.println("Time taken: " + (end.getTime() - start.getTime()) + " milliseconds");
        start = new Date();
        int[] blackBest1 = ai.getNextMove(board1, Cell.BLACK);
        System.out.println("Best move for BLACK: " + blackBest1[0] + " " + blackBest1[1]);
        end = new Date();
//        System.out.println("Time taken: " + (end.getTime() - start.getTime()) + " milliseconds");
//        System.out.println(ai.evaluatePosition(board1,0, 0, Cell.BLACK));
//        System.out.println(ai.evaluatePosition(board1,1, 0, Cell.BLACK));
//        System.out.println(ai.evaluatePosition(board1,1, 1, Cell.BLACK));
//        System.out.println(ai.evaluatePosition(board1,2, 1, Cell.BLACK));
//
//        System.out.println(ai.evaluatePosition(board1,0, 0, Cell.WHITE));
//        System.out.println(ai.evaluatePosition(board1,1, 0, Cell.WHITE));
//        System.out.println(ai.evaluatePosition(board1,1, 1, Cell.WHITE));
//        System.out.println(ai.evaluatePosition(board1,2, 1, Cell.WHITE));


// Print the board
        for (int i = 0; i < board1.getSize(); i++) {
            for (int j = 0; j < board1.getSize(); j++) {
                System.out.printf("%-7d", board1.getCell(i, j).getValue());
            }
            System.out.println();
        }



    }
}
