package tic_tac_toe.state;

import tic_tac_toe.models.Player;

public class GameContext {
    private GameState currentState;

    public GameContext(GameState state) {
        this.currentState = state;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }

    public void next(Player player, boolean hasWon) {
        currentState.next(this, player, hasWon);
    }
}

