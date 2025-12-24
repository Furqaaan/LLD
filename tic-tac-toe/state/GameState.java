package state;

import models.Player;

public interface GameState {
    void next(GameContext context, Player player , boolean hasWon);
    boolean isGameOver();
}
