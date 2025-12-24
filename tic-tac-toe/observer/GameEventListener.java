package observer;

import models.Position;
import models.Symbol;
import state.GameState;

public interface GameEventListener {
    void onMoveMade(Position position, Symbol symbol);
    void onGameStateChanged(GameState state);
}
