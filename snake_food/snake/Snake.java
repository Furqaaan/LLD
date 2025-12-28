package snake_food.snake;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import snake_food.board.Cell;
import snake_food.snake.movement.MovementStrategy;
import snake_food.snake.movement.HumanMovementStrategy;
import snake_food.board.Board;

public class Snake {
    private Deque<Cell> body;
    private Map<Cell, Boolean> positionMap;
    private MovementStrategy movementStrategy;

    public Snake() {
        this.body = new LinkedList<>();
        this.positionMap = new HashMap<>();

        Cell initialPos = new Cell(0, 0);

        this.body.addFirst(initialPos);
        this.positionMap.put(initialPos, true);
        this.movementStrategy = new HumanMovementStrategy();
    }

    public void addHead(Cell cell) {
        this.body.addFirst(cell);
        this.positionMap.put(cell, true);
    }

    public void removeTail() {
        Cell tail = this.body.removeLast();
        this.positionMap.remove(tail);
    }

    public Cell getHead() {
        return this.body.peekFirst();
    }

    public Cell getTail() {
        return this.body.peekLast();
    }

    public Map<Cell, Boolean> getPositionMap() {
        return this.positionMap;
    }

    public Deque<Cell> getBody() {
        return this.body;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public boolean isCrossingBoundary(Board board, Cell cell) {
        return cell.getRow() < 0 || cell.getRow() >= board.getHeight() ||
                cell.getCol() < 0 || cell.getCol() >= board.getWidth();
    }

    public boolean isBitingItself(Cell cell) {
        Cell currentTail = this.getTail();

        return this.getPositionMap().containsKey(cell) &&
                !(cell.getRow() == currentTail.getRow() &&
                        cell.getCol() == currentTail.getCol());
    }

    public int move(Board board, String direction, int[][] food, int foodIndex) {
        Cell currentHead = this.getHead();
        Cell currentTail = this.getTail();

        Cell newHead = this.movementStrategy.getNextPosition(currentHead, direction);

        int newHeadRow = newHead.getRow();
        int newHeadColumn = newHead.getCol();

        boolean isCrossingBoundary = isCrossingBoundary(board, newHead);
        boolean bitesItself = isBitingItself(newHead);

        if (isCrossingBoundary || bitesItself) {
            return -1;
        }

        boolean ateFood = (foodIndex < food.length) &&
                (food[foodIndex][0] == newHeadRow) &&
                (food[foodIndex][1] == newHeadColumn);
        if (ateFood) {
            foodIndex++;
        } else {
            this.removeTail();
            this.getPositionMap().remove(currentTail);
        }

        this.addHead(newHead);
        this.getPositionMap().put(newHead, true);

        int score = this.getBody().size() - 1;
        return score;
    }
}