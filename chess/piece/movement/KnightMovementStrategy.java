package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;

public class KnightMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        int startRow = startCell.getRow();
        int startCol = startCell.getColumn();
        int endRow = endCell.getRow();
        int endCol = endCell.getColumn();

        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // Knight moves in L-shape: 2 squares in one direction and 1 in perpendicular
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}
