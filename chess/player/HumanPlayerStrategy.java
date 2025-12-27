package chess.player;

import chess.board.Board;
import chess.board.Move;
import java.util.Scanner;
import chess.board.Cell;
import chess.game.ExitGameException;

public class HumanPlayerStrategy implements PlayerStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Move determineMove(Board board, boolean isWhiteSide) {
        System.out.println((isWhiteSide ? "White" : "Black") + " player's turn.");
        System.out.println("(Enter 'exit' to leave the game)");

        String source = null;
        String destination = null;

        // Get source position with error handling
        while (true) {
            System.out.print("Enter source position (e.g., a2): ");
            source = scanner.nextLine().trim();

            if (source.equalsIgnoreCase("exit")) {
                throw new ExitGameException("Player chose to exit the game.");
            }

            try {
                board.getCellFromNotation(source);
                break; // Valid input, exit loop
            } catch (IllegalArgumentException _) {
                System.out.println("Invalid notation: " + source + ". Please try again.");
            }
        }

        // Get destination position with error handling
        while (true) {
            System.out.print("Enter destination position (e.g., a4): ");
            destination = scanner.nextLine().trim();

            if (destination.equalsIgnoreCase("exit")) {
                throw new ExitGameException("Player chose to exit the game.");
            }

            try {
                board.getCellFromNotation(destination);
                break; // Valid input, exit loop
            } catch (IllegalArgumentException _) {
                System.out.println("Invalid notation: " + destination + ". Please try again.");
            }
        }

        Cell startCell = board.getCellFromNotation(source);
        Cell endCell = board.getCellFromNotation(destination);

        if (startCell == null || startCell.getPiece() == null) {
            System.out.println("Invalid move: No piece at source cell.");
            return null;
        }

        return new Move(startCell, endCell);
    }
}