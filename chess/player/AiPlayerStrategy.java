package chess.player;

import chess.board.Board;
import chess.board.Move;
import java.util.List;
import java.util.Random;
import chess.board.Cell;
import chess.piece.Piece;
import java.util.ArrayList;

public class AiPlayerStrategy implements PlayerStrategy {
    public Move determineMove(Board board, boolean isWhiteSide) {
        List<Move> validMoves = generateValidMoves(board, isWhiteSide);

        if (validMoves.isEmpty()) {
            throw new IllegalStateException("No valid moves available");
        }

        Random random = new Random();
        return validMoves.get(random.nextInt(validMoves.size()));
    }

    private List<Move> generateValidMoves(Board board, boolean isWhiteSide) {
        List<Move> validMoves = new ArrayList<>();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Cell startCell = board.getCell(row, col);
                Piece piece = startCell.getPiece();

                if (piece != null && piece.isWhite() == isWhiteSide) {
                    for (int destRow = 0; destRow < board.getSize(); destRow++) {
                        for (int destCol = 0; destCol < board.getSize(); destCol++) {
                            Cell endCell = board.getCell(destRow, destCol);
                            Move move = new Move(startCell, endCell);

                            if (move.isValid() && piece.canMove(board, startCell, endCell)) {
                                validMoves.add(move);
                            }
                        }
                    }
                }
            }
        }

        return validMoves;
    }
}