package chess.piece;

import chess.piece.movement.KnightMovementStrategy;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, new KnightMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "N" : "n";
    }
}

