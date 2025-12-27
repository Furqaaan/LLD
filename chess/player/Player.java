package chess.player;

import chess.board.Board;
import chess.board.Move;

public class Player {
    private String name;
    private boolean isWhite;
    private PlayerStrategy strategy;

    public Player(String name, boolean isWhite, PlayerStrategy strategy) {
        this.name = name;
        this.isWhite = isWhite;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }

    public Move determineMove(Board board) {
        return strategy.determineMove(board, isWhite);
    }

    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }
}
