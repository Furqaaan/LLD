package chess.board;

import chess.piece.Piece;

public class Cell {
    private Piece piece;
    private int row;
    private int column;

    public Cell(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getLabel() {
        return row + "," + column;
    }
}
