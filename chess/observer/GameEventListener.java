package chess.observer;

import chess.board.Move;
import chess.enums.Status;

public interface GameEventListener {
    void onMoveMade(Move move);
    void onGameStateChanged(Status state);
}