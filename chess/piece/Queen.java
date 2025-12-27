package chess.piece;

import chess.piece.movement.QueenMovementStrategy;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, new QueenMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "Q" : "q";
    }
}

