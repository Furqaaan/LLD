package chess.piece;

import chess.piece.movement.RookMovementStrategy;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "R" : "r";
    }
}
