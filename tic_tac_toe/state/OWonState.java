package tic_tac_toe.state;

import tic_tac_toe.models.Player;

public class OWonState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon) {
        // Game is over, no next state
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}

