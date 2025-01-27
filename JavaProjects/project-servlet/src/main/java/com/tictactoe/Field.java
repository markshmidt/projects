package com.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Field {
    private final Map<Integer, Sign> field;
    private static final List<List<Integer>> WINNING_COMBINATIONS = List.of(
            List.of(0, 1, 2), // Row 1
            List.of(3, 4, 5), // Row 2
            List.of(6, 7, 8), // Row 3
            List.of(0, 3, 6), // Column 1
            List.of(1, 4, 7), // Column 2
            List.of(2, 5, 8), // Column 3
            List.of(0, 4, 8), // Diagonal 1
            List.of(2, 4, 6)  // Diagonal 2
    );


    public Field() {
        field = new HashMap<>();
        field.put(0, Sign.EMPTY);
        field.put(1, Sign.EMPTY);
        field.put(2, Sign.EMPTY);
        field.put(3, Sign.EMPTY);
        field.put(4, Sign.EMPTY);
        field.put(5, Sign.EMPTY);
        field.put(6, Sign.EMPTY);
        field.put(7, Sign.EMPTY);
        field.put(8, Sign.EMPTY);
    }

    public Map<Integer, Sign> getField() {
        return field;
    }

    public List<Sign> getFieldData() {
        return field.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }


    public Sign checkWin() {
        List<List<Integer>> winPossibilities = List.of(
                List.of(0, 1, 2),
                List.of(3, 4, 5),
                List.of(6, 7, 8),
                List.of(0, 3, 6),
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(0, 4, 8),
                List.of(2, 4, 6)
        );

        for (List<Integer> winPossibility : winPossibilities) {
            if (field.get(winPossibility.get(0)) == field.get(winPossibility.get(1))
                && field.get(winPossibility.get(0)) == field.get(winPossibility.get(2))) {
                return field.get(winPossibility.get(0));
            }
        }
        return Sign.EMPTY;
    }
    public List<Integer> getEmptyFieldIndices() {
        List<Integer> emptyIndices = new ArrayList<>();
        for (Map.Entry<Integer, Sign> entry : field.entrySet()) {
            if (entry.getValue() == Sign.EMPTY) {
                emptyIndices.add(entry.getKey());
            }
        }
        return emptyIndices;
    }
    public int getBlockingIndex(Sign opponentSign) {
        // Iterate over all possible winning combinations
        for (List<Integer> combination : WINNING_COMBINATIONS) {
            int emptyCount = 0;
            int opponentCount = 0;
            int emptyIndex = -1;

            // Check each cell in the combination
            for (int index : combination) {
                Sign currentSign = field.get(index);
                if (currentSign == Sign.EMPTY) {
                    emptyCount++;
                    emptyIndex = index;
                } else if (currentSign == opponentSign) {
                    opponentCount++;
                }
            }

            // If there's exactly one empty space and two opponent signs, block it
            if (emptyCount == 1 && opponentCount == 2) {
                return emptyIndex;
            }
        }

        // No blocking move found
        return -1;
    }


}