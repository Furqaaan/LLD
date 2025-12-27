package chess.game;

import chess.board.Board;
import chess.player.Player;
import chess.board.Move;
import chess.enums.Status;
import java.util.ArrayList;
import chess.piece.Piece;
import chess.piece.King;
import chess.board.Cell;
import chess.observer.GameEventListener;

public class ChessGame implements BoardGames {
    private Board board;
    private Player player1;
    private Player player2;
    private boolean isWhiteTurn;
    private ArrayList<Move> gameLog;
    private Status status;
    private GameEventListener listener;

    public ChessGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = Board.getInstance(8);
        this.isWhiteTurn = true;
        this.status = Status.ACTIVE;
        this.gameLog = new ArrayList<>();
    }

    public void start() {
        try {
            while (this.status == Status.ACTIVE) {
                Player currentPlayer = isWhiteTurn ? player1 : player2;
                System.out.println(
                        currentPlayer.getName() + "'s turn (" + (currentPlayer.isWhite() ? "White" : "Black") + ")");

                Move move = currentPlayer.determineMove(board);
                if (move == null) {
                    System.out.println("Invalid move: Please try again.");
                    continue;
                }

                makeMove(move, currentPlayer);
            }

            System.out.println("Game Over! Status: " + this.status);
        } catch (ExitGameException e) {
            System.out.println("\nGame exited by player. Thanks for playing!");
        }
    }

    public void makeMove(Move move, Player player) {
        Cell startCell = move.getStartCell();
        Cell endCell = move.getEndCell();

        if (move.isValid()) {
            Piece sourcePiece = startCell.getPiece();

            if (sourcePiece.canMove(this.board, startCell, endCell)) {
                Piece destinationPiece = endCell.getPiece();

                if (destinationPiece != null) {
                    if (destinationPiece instanceof King && isWhiteTurn) {
                        this.status = Status.WHITE_WIN;
                        notifyGameStateChanged(Status.WHITE_WIN);
                        return;
                    }

                    if (destinationPiece instanceof King && !isWhiteTurn) {
                        this.status = Status.BLACK_WIN;
                        notifyGameStateChanged(Status.BLACK_WIN);
                        return;
                    }

                    destinationPiece.setKilled(true);
                }

                gameLog.add(move);
                endCell.setPiece(sourcePiece);
                startCell.setPiece(null);

                isWhiteTurn = !isWhiteTurn;

                notifyMoveMade(move);
            }
        }
    }

    public void setObserver(GameEventListener listener) {
        this.listener = listener;
    }

    public Board getBoard() {
        return board;
    }

    private void notifyMoveMade(Move move) {
        if (listener != null) {
            listener.onMoveMade(move);
        }
    }

    private void notifyGameStateChanged(Status state) {
        if (listener != null) {
            listener.onGameStateChanged(state);
        }
    }
}
