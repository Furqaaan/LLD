# Snake Food Game - Design Patterns

## Strategy Design Pattern

### Movement Strategy

The **Strategy Pattern** is used for snake movement, allowing different movement behaviors (human-controlled vs AI-controlled).

#### Implementation

**Strategy Interface**

```java
public interface MovementStrategy {
    Cell getNextPosition(Cell currentHead, String direction);
}
```

**Concrete Strategies**

-   `HumanMovementStrategy` - Processes user input (W/A/S/D) to determine next position
-   `AiMovementStrategy` - Calculates AI-driven movement decisions

**Context (Snake Class)**

```java
public class Snake {
    private MovementStrategy movementStrategy;

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public int move(Board board, String direction, int[][] food, int foodIndex) {
        Cell newHead = this.movementStrategy.getNextPosition(currentHead, direction);
        // ... move logic
    }
}
```

> The `Snake` class delegates movement calculation to the strategy, making it easy to switch between human and AI control without modifying core game logic.

---

## Factory Design Pattern

### Food Factory

The **Factory Pattern** is used to create different types of food items (normal food vs bonus food).

#### Implementation

```java
public class FoodFactory {
    public static FoodItem createFood(int[] position, String type) {
        if ("bonus".equals(type)) {
            return new BonusFood(position[0], position[1]);
        }
        return new NormalFood(position[0], position[1]);
    }
}
```

> The factory centralizes food creation logic, making it easy to add new food types without modifying client code.

---

## Singleton Design Pattern

### Board Singleton

The **Singleton Pattern** ensures there is only one game board instance throughout the game.

#### Implementation

```java
public class Board {
    private static Board instance;

    private Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Board getInstance(int width, int height) {
        if (instance == null) {
            instance = new Board(width, height);
        }
        return instance;
    }
}
```

> The Singleton pattern ensures all game components reference the same board, maintaining state consistency.

---

## Design Patterns Summary

| Pattern       | Type       | Purpose                          | Implementation     |
| ------------- | ---------- | -------------------------------- | ------------------ |
| **Strategy**  | Behavioral | Encapsulates movement algorithms | `MovementStrategy` |
| **Factory**   | Creational | Abstracts food creation          | `FoodFactory`      |
| **Singleton** | Creational | Ensures single board instance    | `Board`            |
