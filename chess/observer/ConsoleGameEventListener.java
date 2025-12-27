package chess.observer;

import chess.board.Board;
import chess.board.Move;
import chess.enums.Status;

public class ConsoleGameEventListener implements GameEventListener {
    private final Board board;

    public ConsoleGameEventListener(Board board) {
        this.board = board;
    }

    @Override
    public void onMoveMade(Move move) {
        System.out.println("Move made from: " + move.getStartCell().getLabel() + " to " + move.getEndCell().getLabel());
        System.out.println(board.renderBoard());
    }

    @Override
    public void onGameStateChanged(Status state) {
        System.out.println("Game state changed to: " + state);
    }
}