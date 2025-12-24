package strategy;

import models.Board;
import models.Position;

public interface PlayerStrategy {
    Position makeMove(Board board);
}
