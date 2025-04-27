//  Mariia Shmidt 101470474 (UI)
import java.util.Scanner;

import AI.AI;
import Board.*;


//  Group Project COMP 2080
//  Eduard Kosenko 101480050
//  Evgenii Baldin 101435160
//  Leonard Eriyo 101511592
//  Mariia Shmidt 101470474
//  Mateus Sfeir 101484904

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int BOARD_SIZE = 9;
        Board board = new Board(BOARD_SIZE);


        System.out.println("Welcome to Gomoku!");
        System.out.println("Select game mode:");
        while (true) {
            System.out.println("1. Single Player Mode (vs AI)");
            System.out.println("2. Two Player Mode");
            String gameMode = scanner.nextLine();
            System.out.println();
            if (gameMode.equals("1")) {
                playSinglePlayerMode(scanner, board);
                break;
            } else if (gameMode.equals("2")) {
                playTwoPlayerMode(scanner, board);
                break;
            } else {
                System.out.println("Invalid selection. Please select either 1 or 2.");
            }
        }


    }

    private static void playSinglePlayerMode(Scanner scanner, Board board) {
        System.out.println("Single Player Mode Selected.");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        playerName = playerName.substring(0, 1).toUpperCase() + playerName.substring(1).toLowerCase();
        Cell playerPiece = null;
        while (true) {
            System.out.print(playerName + ", choose your symbol. B for Black, W for White: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("B")) {
                playerPiece = Cell.BLACK;
                break;
            } else if (input.equals("W")) {
                playerPiece = Cell.WHITE;
                break;
            } else {
                System.out.println("Invalid input. Please enter B or W.");
            }
        }

        Cell aiPiece = (playerPiece == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
        AI ai = new AI();

        boolean gameEnded = false;
        while (!gameEnded) {
            Cell currentPlayer = board.getCurrentMove();
            String currentPlayerName = (currentPlayer == playerPiece) ? playerName : "AI";
            printBoard(board.getState());
            System.out.println(currentPlayerName + "'s turn (" + currentPlayer + ")");

            int row, col;

            if (currentPlayer == aiPiece) {
                var response = ai.getNextMove(board, currentPlayer);
                row = response[0];
                col = response[1];
            } else {
                col = getUserInput(scanner, "column");
                row = getUserInput(scanner, "row");
            }

            try {
                boolean win = board.makeMove(new Move(row, col, currentPlayer));
                if (win) {
                    printBoard(board.getState());
                    System.out.println(currentPlayerName + " has won the game!");
                    gameEnded = true;
                } else if (board.isFull()) {
                    printBoard(board.getState());
                    System.out.println("The game resulted in a tie.");
                    gameEnded = true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move: " + e.getMessage());
            }
        }
    }

    // Player 1
    private static void playTwoPlayerMode(Scanner scanner, Board board) {
        System.out.println("Two Player Mode Selected.");
        System.out.print("Player one enter your name: ");
        String playerOneName = scanner.nextLine();
        playerOneName = playerOneName.substring(0, 1).toUpperCase() + playerOneName.substring(1).toLowerCase();
        Cell playerOnePiece = null;
        while (true) {
            System.out.print(playerOneName + ", choose your symbol. B for Black, W for White: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("B")) {
                playerOnePiece = Cell.BLACK;
                break;
            } else if (input.equals("W")) {
                playerOnePiece = Cell.WHITE;
                break;
            } else {
                System.out.println("Invalid input. Please enter B or W.");
            }
        }

        System.out.print("Player two enter your name: ");
        String playerTwoName = scanner.nextLine();
        playerTwoName = playerTwoName.substring(0, 1).toUpperCase() + playerTwoName.substring(1).toLowerCase();
        Cell playerTwoPiece = (playerOnePiece == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
        System.out.println(playerTwoName + " will be " + playerTwoPiece);

        boolean gameEnded = false;
        while (!gameEnded) {
            Cell currentPlayer = board.getCurrentMove();
            String currentPlayerName = (currentPlayer == playerOnePiece) ? playerOneName : playerTwoName;
            printBoard(board.getState());
            System.out.println(currentPlayerName + "'s turn (" + currentPlayer + ")");

            int row = getUserInput(scanner, "row");
            int col = getUserInput(scanner, "column");

            try {
                boolean win = board.makeMove(new Move(row, col, currentPlayer));
                if (win) {
                    printBoard(board.getState());
                    System.out.println(currentPlayerName + " has won the game!");
                    gameEnded = true;
                } else if (board.isFull()) {
                    printBoard(board.getState());
                    System.out.println("The game resulted in a tie.");
                    gameEnded = true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move: " + e.getMessage());
            }
        }
    }

    private static int getUserInput(Scanner scanner, String type) {
        int value;
        while (true) {
            System.out.print("Enter " + type + " (0-8): ");
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 0 && value < 9) break;
                else System.out.println(type + " must be between 0 and 8.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number between 0 and 8.");
            }
        }
        return value;
    }


    private static void printBoard(Cell[][] board) {
        int size = board.length;
        System.out.println("Current Board:");
        System.out.print("  ");
        for (int col = 0; col < size; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < size; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < size; col++) {
                Cell p = board[row][col];
                System.out.print((p == null || p == Cell.EMPTY ? "." : p.toString()) + " ");
            }
            System.out.println();
        }
    }

}