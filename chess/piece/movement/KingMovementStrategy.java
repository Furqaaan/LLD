package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;

public class KingMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        int startRow = startCell.getRow();
        int startCol = startCell.getColumn();
        int endRow = endCell.getRow();
        int endCol = endCell.getColumn();

        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // King moves one square in any direction
        return rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0);
    }
}
