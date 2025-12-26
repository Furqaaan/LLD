package tic_tac_toe.strategy;

import tic_tac_toe.models.Board;
import tic_tac_toe.models.Position;

public interface PlayerStrategy {
    Position makeMove(Board board);
}

