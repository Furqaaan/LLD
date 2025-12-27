package chess.piece;

import chess.piece.movement.PawnMovementStrategy;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "P" : "p";
    }
}
