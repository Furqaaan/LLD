package factory;

import models.Player;
import models.Symbol;
import strategy.PlayerStrategy;

public interface PlayerFactory {
    Player createPlayer(Symbol symbol, PlayerStrategy strategy);
}