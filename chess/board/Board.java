package chess.board;

import chess.piece.PieceFactory;
import chess.enums.PieceType;

public class Board {
    private static Board instance;
    private Cell[][] board;

    private Board(int rows) {
        initializeBoard(rows);
    }

    public static Board getInstance(int rows) {
        if (instance == null) {
            instance = new Board(rows);
        }

        return instance;
    }

    private void initializeBoard(int rows) {
        board = new Cell[rows][rows];

        // Black pieces at top (row 0 = rank 8)
        setPieceRow(0, false);
        setPawnRow(1, rows, false);

        // White pieces at bottom (row 7 = rank 1)
        setPieceRow(rows - 1, true);
        setPawnRow(rows - 2, rows, true);

        for (int row = 2; row < rows - 2; row++) {
            for (int col = 0; col < rows; col++) {
                board[row][col] = new Cell(row, col, null);
            }
        }

        System.out.println("Board initialized");
        System.out.println(renderBoard());
    }

    private void setPieceRow(int row, boolean isWhite) {
        board[row][0] = new Cell(row, 0, PieceFactory.createPiece(PieceType.ROOK, isWhite));
        board[row][1] = new Cell(row, 1, PieceFactory.createPiece(PieceType.KNIGHT, isWhite));
        board[row][2] = new Cell(row, 2, PieceFactory.createPiece(PieceType.BISHOP, isWhite));
        board[row][3] = new Cell(row, 3, PieceFactory.createPiece(PieceType.QUEEN, isWhite));
        board[row][4] = new Cell(row, 4, PieceFactory.createPiece(PieceType.KING, isWhite));
        board[row][5] = new Cell(row, 5, PieceFactory.createPiece(PieceType.BISHOP, isWhite));
        board[row][6] = new Cell(row, 6, PieceFactory.createPiece(PieceType.KNIGHT, isWhite));
        board[row][7] = new Cell(row, 7, PieceFactory.createPiece(PieceType.ROOK, isWhite));
    }

    private void setPawnRow(int row, int rows, boolean isWhite) {
        for (int j = 0; j < rows; j++) {
            board[row][j] = new Cell(row, j, PieceFactory.createPiece(PieceType.PAWN, isWhite));
        }
    }

    public Cell getCellFromNotation(String notation) {
        if (notation.length() != 2) {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }

        char file = notation.charAt(0);
        char rank = notation.charAt(1);

        if (file < 'a' || file >= 'a' + getSize() || rank < '1' || rank >= '1' + getSize()) {
            throw new IllegalArgumentException("Invalid notation: " + notation);
        }

        int col = file - 'a';
        int row = getSize() - (rank - '0');

        return board[row][col];
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= getSize() || col < 0 || col >= getSize()) {
            return null;
        }

        return board[row][col];
    }

    public int getSize() {
        return board.length;
    }

    public String renderBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  +---+---+---+---+---+---+---+---+\n");

        for (int row = 0; row < getSize(); row++) {
            sb.append(getSize() - row).append(" |");
            for (int col = 0; col < getSize(); col++) {
                Cell cell = board[row][col];
                String symbol = " ";

                if (cell != null && cell.getPiece() != null) {
                    symbol = cell.getPiece().getSymbol();
                }

                sb.append(" ").append(symbol).append(" |");
            }
            sb.append("\n  +---+---+---+---+---+---+---+---+\n");
        }

        sb.append("    a   b   c   d   e   f   g   h\n");
        return sb.toString();
    }
}
