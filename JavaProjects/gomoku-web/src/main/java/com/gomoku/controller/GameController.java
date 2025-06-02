package com.gomoku.controller;

import com.gomoku.model.Board;
import com.gomoku.model.Cell;
import com.gomoku.model.Move;
import com.gomoku.ai.AI;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class GameController {

    private Board board;
    private final AI ai = new AI();
    private boolean isPvP = false;


    private Cell playerSymbol;
    private Cell aiSymbol;

    // Board state, current move and winner
    @GetMapping("/state")
    public Board getState() {
        return board;
    }

    // Assigning symbol to player and ai, initializing board
    @PostMapping("/start")
    public void startGame(@RequestBody String symbol) {
        symbol = symbol.replace("\"", "").toUpperCase();

        playerSymbol = Cell.valueOf(symbol);

        aiSymbol = (playerSymbol == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;

        board = new Board(9);
        // If AI is assigned Black, it makes the first move
        if (aiSymbol == Cell.BLACK) {
            int[] aiMove = ai.getNextMove(board, aiSymbol);
            board.makeMove(new Move(aiMove[0], aiMove[1], aiSymbol));
        }
    }

    // Setting play mode (two players or player vs computer)
    @PostMapping("/mode")
    public void setMode(@RequestBody String mode) {
        isPvP = "pvp".equalsIgnoreCase(mode);
    }


    // Makes player's move, then if not PvP mode, makes ai move
    @PostMapping("/move")
    public Board makeMove(@RequestBody Move move) {
        //if cell is not empty or move is illegal
        try {
            board.makeMove(move);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal move: " + e.getMessage());
            return board;
        }

        // AI makes move if it is not two players mode, board is not full and there is no winner yet
        if (!isPvP && !board.isFull() && board.getWinner() == null) {
                int[] aiMove = ai.getNextMove(board, aiSymbol);
                try {
                    board.makeMove(new Move(aiMove[0], aiMove[1], aiSymbol));
                } catch (IllegalArgumentException e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                    return board;

            }
        }

        return board;
    }


    // Resetting (creating empty board)
    @PostMapping("/reset")
    public void resetGame() {
        board = new Board(9);
    }

}
