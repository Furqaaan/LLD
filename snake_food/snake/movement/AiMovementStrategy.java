package snake_food.snake.movement;

import snake_food.board.Cell;

public class AiMovementStrategy implements MovementStrategy {
    @Override
    public Cell getNextPosition(Cell currentHead, String direction) {
        return currentHead;
    }
}
