package chess.player;

import chess.board.Board;
import chess.board.Move;

public interface PlayerStrategy {
    Move determineMove(Board board, boolean isWhiteSide);
}
