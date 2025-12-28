package snake_food.snake.movement;

import snake_food.board.Cell;

public class HumanMovementStrategy implements MovementStrategy {
    @Override
    public Cell getNextPosition(Cell currentHead, String direction) {
        int row = currentHead.getRow();
        int col = currentHead.getCol();
        
        switch (direction.toUpperCase()) {
            case "W":
                row--;
                break;
            case "S":
                row++;
                break;
            case "A":
                col--;
                break;
            case "D":
                col++;
                break;
            default:
                return currentHead;
        }

        return new Cell(row, col);
    }
}
