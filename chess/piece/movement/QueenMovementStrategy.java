package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;

public class QueenMovementStrategy implements MovementStrategy {
    private final RookMovementStrategy rookStrategy = new RookMovementStrategy();
    private final BishopMovementStrategy bishopStrategy = new BishopMovementStrategy();

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        // Queen combines rook and bishop movement
        return rookStrategy.canMove(board, startCell, endCell)
                || bishopStrategy.canMove(board, startCell, endCell);
    }
}
