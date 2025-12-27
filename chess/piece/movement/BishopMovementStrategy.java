package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;

public class BishopMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        int startRow = startCell.getRow();
        int startCol = startCell.getColumn();
        int endRow = endCell.getRow();
        int endCol = endCell.getColumn();

        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // Bishop moves diagonally (row and column differences must be equal)
        if (rowDiff != colDiff || rowDiff == 0) {
            return false;
        }

        // Check if path is clear
        return isPathClear(board, startRow, startCol, endRow, endCol);
    }

    private boolean isPathClear(Board board, int startRow, int startCol, int endRow, int endCol) {
        int rowDirection = Integer.compare(endRow, startRow);
        int colDirection = Integer.compare(endCol, startCol);

        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;

        while (currentRow != endRow || currentCol != endCol) {
            Cell cell = board.getCell(currentRow, currentCol);
            if (cell != null && cell.getPiece() != null) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }
}
