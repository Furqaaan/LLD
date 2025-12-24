package state;

import models.Player;
import models.Symbol;

public class OTurnState implements GameState {
    @Override
    public void next(GameContext context, Player player , boolean hasWon) {
        if (hasWon) {
            if (player.getSymbol() == Symbol.O) {
                context.setState(new OWonState());
            } else {
                context.setState(new XWonState());
            }
        } else {
            context.setState(new XTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
