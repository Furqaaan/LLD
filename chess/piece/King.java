package chess.piece;

import chess.piece.movement.KingMovementStrategy;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite, new KingMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "K" : "k";
    }
}
