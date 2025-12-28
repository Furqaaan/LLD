package snake_food.snake.movement;

import snake_food.board.Cell;

public interface MovementStrategy {
    Cell getNextPosition(Cell currentHead, String direction);
  }