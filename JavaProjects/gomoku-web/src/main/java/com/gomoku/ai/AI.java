package com.gomoku.ai;

import com.gomoku.model.Board;
import com.gomoku.model.Cell;

//  Group Project COMP 2080
//  Eduard Kosenko 101480050
//  Evgenii Baldin 101435160
//  Leonard Eriyo 101511592
//  Mariia Shmidt 101470474
//  Mateus Sfeir 101484904


public class AI {
    public int[] getNextMove(Board board, Cell cellType) {
        return minimax(board, cellType);
    }

    public int[] minimax(Board board, Cell playerCell) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        Cell opponentCell = (playerCell == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;

        int size = board.getSize();

        int[][] possibleMoves = getSortedPossibleMoves(board, playerCell);

        int[][] scores = new int[size][size];

        if (possibleMoves.length == 1){
            bestMove[0] = possibleMoves[0][0];
            bestMove[1] = possibleMoves[0][1];
            return bestMove;
        }

        for (int i = 0; i < possibleMoves.length; i++) {
            int row = possibleMoves[i][0];
            int col = possibleMoves[i][1];
            if (board.getCell(row, col) != Cell.EMPTY) {
                continue;
            }

            int[] move = {row, col};


            // Make move
            // Reuse the same instance of the board
            Board copy = board.clone();
            copy.setTile(row, col, playerCell);

            if (copy.checkWin(row, col)) {
                return new int[]{row, col}; // без board.setTile()
            }

            int score = minimaxValue(copy, 4, false, playerCell, opponentCell, alpha, beta); // используем copy

            scores[row][col] = score;

            if (score >= bestScore) {
                bestScore = score;
                bestMove[0] = row;
                bestMove[1] = col;
            }
            alpha = Math.max(alpha, bestScore);

        }
        // Log evaluation for best move
//        System.out.println("Best move: " + bestMove[0] + ", " + bestMove[1] + " with score: " + bestScore);
//        System.out.println("Scores: ");
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.printf("%-7d", scores[i][j]);
//            }
//            System.out.println();
//        }
        return bestMove;
    }


    public int minimaxValue(Board board, int depth, boolean isMaximizing, Cell playerCell, Cell opponentCell, int alpha, int beta) {

        // Check of winning condition has to be performed before the function call
        // Casue I don't want to implement logic of checking win condition here, while I have checkWin in board class
        // Update: I found a pretty interesting way identifying winning condition (maybe could be applied to pattern matching as well)
        // https://medium.com/@LukeASalamone/creating-an-ai-for-gomoku-28a4c84c7a52#:~:text=To%20determine%20the%20score%20of,the%20state%20of%20the%20board.

        if (depth == 0) {
            return boardEvaluation(board, playerCell);
        }

        int boardSize = board.getSize();

        int totalScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;


        int[][] possibleMoves = getSortedPossibleMoves(board, opponentCell);


        for (int i = 0; i < possibleMoves.length; i++) {
            int row = possibleMoves[i][0];
            int col = possibleMoves[i][1];
            Board copy = board.clone();
            copy.setTile(row, col, isMaximizing ? playerCell : opponentCell);

            if (copy.checkWin(row, col)) {
                return isMaximizing ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            int score = minimaxValue(copy, depth - 1, !isMaximizing,
                    playerCell, opponentCell, alpha, beta);

            // Update score
            if (isMaximizing) {
                totalScore = Math.max(totalScore, score);
                alpha = Math.max(alpha, score);
            } else {
                totalScore = Math.min(totalScore, score);
                beta = Math.min(beta, score);
            }

            if (beta <= alpha) {
                break;
            }


        }
        return totalScore;
    }


    public Integer boardEvaluation(Board board, Cell playerCell) {
        int totalScore = 0;
        int size = board.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.getCell(row, col) == Cell.EMPTY) {
                    totalScore += evaluatePosition(board, row, col, playerCell);
                }
            }
        }
        return totalScore;
    }

    public int evaluatePosition(Board board, int row, int col, Cell playerCell) {

        int score = 0;
        int size = board.getSize();

        // Define the AI player and opponent
        Cell opponent = playerCell == Cell.BLACK ? Cell.WHITE : Cell.BLACK;  // Opponent is O

        // Check in all 4 directions
        int[][] directions = {
                {1, 0}, {0, 1}, {1, 1}, {1, -1}  // horizontal, vertical, diagonal, anti-diagonal
        };

        // Temporarily place AI player's piece at this position
        board.setTile(row, col, playerCell);

        // Evaluate for AI player (positive score)
        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];

            // Count consecutive pieces and check if blocked
            int count = 1;  // Start with 1 for the current position
            int blocked = 0;

            // Check forward direction
            int r = row + dRow;
            int c = col + dCol;
            while (r >= 0 && r < size && c >= 0 && c < size && board.getCell(r, c) == playerCell) {
                count++;
                r += dRow;
                c += dCol;
            }
            // Check if blocked
            if (r >= 0 && r < size && c >= 0 && c < size && board.getCell(r, c) == opponent) {
                blocked++;
            }

            // Check backward direction
            r = row - dRow;
            c = col - dCol;
            while (r >= 0 && r < size && c >= 0 && c < size && board.getCell(r, c) == playerCell) {
                count++;
                r -= dRow;
                c -= dCol;
            }
            // Check if blocked
            if (r >= 0 && r < size && c >= 0 && c < size && board.getCell(r, c) == opponent) {
                blocked++;
            }

            // Add AI's pattern score (positive)
            score += getPatternScore(count, blocked);
        }

        // Reset position
        board.setTile(row, col, Cell.EMPTY);

        return score;
    }

    public Integer getPatternScore(int count, int blocked) {

        // Well known problem:
        // Patterns with empty space in the middle (such as XXX.X) is not being recognized

        if (count > 5) {
            return 10000000;
        }
        switch (count) {
            case 5:
                return 10000000;  // Five
            case 4:
                if (blocked == 0) {
                    return 100000;
                } else if (blocked == 1) {
                    return 10000;
                }
                return 0;
            case 3:
                if (blocked == 0) {
                    return 1000;
                } else if (blocked == 1) {
                    return 100;
                }
                return 0;
            case 2:
                if (blocked == 0) {
                    return 100;
                } else if (blocked == 1) {
                    return 10;
                }
                return 0;
            case 1:
                if (blocked == 0) {
                    return 10;
                } else if (blocked == 1) {
                    return 1;
                }
                return 0;
            default:
                return 0;
        }
    }


    public int[][] getSortedPossibleMoves(Board board, Cell playerCell) {
        int size = board.getSize();

        int[][] allPossibleMoves_ = new int[size * size][];
        int count = 0;

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                // Assume if there is no other pieces in radius of 2, so this move would not much sence
                // Empty board case is handled separately
                int radius = 2;
                int min_r = row - radius;
                int max_r = row + radius;
                int min_c = column - radius;
                int max_c = column + radius;

                boolean isNearbyPieces = false;
                for (int r = min_r; r <= max_r; r++) {
                    for (int c = min_c; c <= max_c; c++) {
                        if (r < 0 || r >= size || c < 0 || c >= size) {
                            continue;
                        }
                        if (board.getCell(r, c) != Cell.EMPTY) {
                            isNearbyPieces = true;
                        }
                    }
                }

                if (!isNearbyPieces) {
                    continue;
                }

                if (board.getCell(row, column) == Cell.EMPTY) {
                    int moveScore = evaluatePosition(board, row, column, playerCell);
                    allPossibleMoves_[count] = new int[]{row, column, moveScore};
                    count++;
                }
            }
        }

        // If board is empty, no possible moves would be fined
        // So just place tile in the center
        if (count == 0) {
            int[][] ret = new int[1][];
            ret[0] = new int[]{size / 2, size / 2, 0};
            return ret;
        }

        // Reduce size of array
        int[][] allPossibleMoves = new int[count][];
        int index = 0;

        for (int i = 0; i < size * size; i++) {
            if (allPossibleMoves_[i] != null) {
                allPossibleMoves[index] = allPossibleMoves_[i];
                index++;
            }
        }

        sortPossibleMoves(allPossibleMoves);
        return allPossibleMoves;

    }


    public void sortPossibleMoves(int[][] possibleMoves) {
        this.MergeSort(0, possibleMoves.length - 1, possibleMoves);
    }

    public void MergeSort(int left, int right, int[][] array) {
        if (left >= right) {
            return;
        }

        // Split
        int mid = left + (right - left) / 2;
        this.MergeSort(left, mid, array);
        this.MergeSort(mid + 1, right, array);

        // Merge
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        int[][] leftArray = new int[leftSize][];
        int[][] rightArray = new int[rightSize][];

        // Copy data to temp arrays
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[left + i];
        }

        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // Merge the temp arrays back
        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i][2] > rightArray[j][2]) {  // Sort by the third element (index 2)
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
        }

    }

}