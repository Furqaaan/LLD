package tic_tac_toe.state;

import tic_tac_toe.models.Player;

public interface GameState {
    void next(GameContext context, Player player, boolean hasWon);
    boolean isGameOver();
}

