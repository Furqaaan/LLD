package chess.piece;

import chess.board.Board;
import chess.board.Cell;
import chess.piece.movement.MovementStrategy;

public abstract class Piece {
    private boolean isWhite;
    private boolean isKilled = false;
    private MovementStrategy movementStrategy;

    protected Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell);
    }

    public abstract String getSymbol();
}