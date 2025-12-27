package chess.piece;

import chess.piece.movement.BishopMovementStrategy;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, new BishopMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "B" : "b";
    }
}
