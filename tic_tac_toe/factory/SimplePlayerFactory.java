package tic_tac_toe.factory;

import tic_tac_toe.models.Player;
import tic_tac_toe.models.Symbol;
import tic_tac_toe.strategy.PlayerStrategy;

public class SimplePlayerFactory implements PlayerFactory {
    @Override
    public Player createPlayer(Symbol symbol, PlayerStrategy strategy) {
        return new Player(symbol, strategy);
    }
}

