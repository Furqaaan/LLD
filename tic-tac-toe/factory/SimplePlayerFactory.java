package factory;

import models.Player;
import models.Symbol;
import strategy.PlayerStrategy;

public class SimplePlayerFactory implements PlayerFactory {
    @Override
    public Player createPlayer(Symbol symbol, PlayerStrategy strategy) {
        return new Player(symbol, strategy);
    }
}
