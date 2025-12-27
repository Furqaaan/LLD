package chess.piece.movement;

import chess.board.Board;
import chess.board.Cell;

public interface MovementStrategy {
    boolean canMove(Board board, Cell startCell, Cell endCell);
}
