package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;
import chess.piece.Piece;

public class PawnMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        Piece pawn = startCell.getPiece();
        if (pawn == null) {
            return false;
        }

        int startRow = startCell.getRow();
        int startCol = startCell.getColumn();
        int endRow = endCell.getRow();
        int endCol = endCell.getColumn();

        boolean isWhite = pawn.isWhite();
        int direction = isWhite ? -1 : 1; // White moves up (decreasing row), Black moves down

        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

        // Forward move (no capture)
        if (colDiff == 0) {
            // Single step forward
            if (rowDiff == direction) {
                return endCell.getPiece() == null;
            }

            // Double step from starting position
            int startingRow = isWhite ? board.getSize() - 2 : 1;
            if (startRow == startingRow && rowDiff == 2 * direction) {
                Cell middleCell = board.getCell(startRow + direction, startCol);
                return middleCell != null && middleCell.getPiece() == null
                        && endCell.getPiece() == null;
            }
        }

        // Diagonal capture
        if (colDiff == 1 && rowDiff == direction) {
            Piece targetPiece = endCell.getPiece();
            return targetPiece != null && targetPiece.isWhite() != isWhite;
        }

        return false;
    }
}
