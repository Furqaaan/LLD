package chess.piece;

import chess.enums.PieceType;

public class PieceFactory {
    public static Piece createPiece(PieceType pieceType, boolean isWhite) {
        switch (pieceType) {
            case PAWN:
                return new Pawn(isWhite);
            case ROOK:
                return new Rook(isWhite);
            case KNIGHT:
                return new Knight(isWhite);
            case BISHOP:
                return new Bishop(isWhite);
            case QUEEN:
                return new Queen(isWhite);
            case KING:
                return new King(isWhite);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}
