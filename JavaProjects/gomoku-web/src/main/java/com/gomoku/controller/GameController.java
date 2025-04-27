package com.gomoku.controller;


import com.gomoku.model.Board;
import com.gomoku.model.Cell;
import com.gomoku.model.Move;
import com.gomoku.ai.AI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private Board board = new Board(9);
    private final AI ai = new AI();
    private Cell playerSymbol = Cell.BLACK;
    private Cell aiSymbol = Cell.WHITE;

    // game state
    @GetMapping("/state")
    public Board getState() {
        return board;
    }

    @PostMapping("/start")
    public void startGame(@RequestBody String symbol) {
        symbol = symbol.replace("\"", "");
        playerSymbol = symbol.equalsIgnoreCase("B") ? Cell.BLACK : Cell.WHITE;
        aiSymbol = (playerSymbol == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
        board = new Board(9);
    }

    // player's move
    @PostMapping("/move")
    public Board makeMove(@RequestBody Move move) {
        board.makeMove(move);
        // ai move
        if (!board.isFull() && board.getWinner() == null) {
            int[] aiMove = ai.getNextMove(board, aiSymbol);
            board.makeMove(new Move(aiMove[0], aiMove[1], aiSymbol));
        }

        return board;
    }

    // reset game
    @PostMapping("/reset")
    public void resetGame() {
        board = new Board(9);
    }
    @GetMapping("/")
    public String hello() {
        return "Gomoku API is running!";
    }

}
