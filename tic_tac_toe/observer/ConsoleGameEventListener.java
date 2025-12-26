package tic_tac_toe.observer;

import tic_tac_toe.models.Position;
import tic_tac_toe.models.Symbol;
import tic_tac_toe.state.GameState;

public class ConsoleGameEventListener implements GameEventListener {
    @Override
    public void onMoveMade(Position position, Symbol symbol) {
        System.out.println("Move made at position: " + position + " by " + symbol);
    }

    @Override
    public void onGameStateChanged(GameState state) {
        System.out.println("Game state changed to: " + state);
    }
}

