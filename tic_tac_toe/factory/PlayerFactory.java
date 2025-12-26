package tic_tac_toe.factory;

import tic_tac_toe.models.Player;
import tic_tac_toe.models.Symbol;
import tic_tac_toe.strategy.PlayerStrategy;

public interface PlayerFactory {
    Player createPlayer(Symbol symbol, PlayerStrategy strategy);
}

