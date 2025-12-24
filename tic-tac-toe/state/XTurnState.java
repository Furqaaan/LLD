package state;

import models.Player;
import models.Symbol;

public class XTurnState implements GameState {
    @Override
    public void next(GameContext context, Player player , boolean hasWon) {
        if (hasWon) {
            if (player.getSymbol() == Symbol.X) {
                context.setState(new XWonState());
            } else {
                context.setState(new OWonState());
            }
        } else {
            context.setState(new OTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
