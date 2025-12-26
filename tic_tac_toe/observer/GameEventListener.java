package tic_tac_toe.observer;

import tic_tac_toe.models.Position;
import tic_tac_toe.models.Symbol;
import tic_tac_toe.state.GameState;

public interface GameEventListener {
    void onMoveMade(Position position, Symbol symbol);
    void onGameStateChanged(GameState state);
}

