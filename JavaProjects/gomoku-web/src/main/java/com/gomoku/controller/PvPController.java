package com.gomoku.controller;

import com.gomoku.model.Board;
import com.gomoku.model.Move;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pvp")
@CrossOrigin(origins = "*")
public class PvPController {

    private Board board = new Board(9);

    @PostMapping("/move")
    public Board makeMove(@RequestBody Move move) {
        board.makeMove(move);
        return board;
    }

    @GetMapping("/state")
    public Board getState() {
        return board;
    }

    @PostMapping("/reset")
    public void reset() {
        board = new Board(9);
    }
}
