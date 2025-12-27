# Chess Game - Design Patterns

This document explains the design patterns used in the Chess game implementation.

---

## Strategy Design Pattern

### What is the Strategy Design Pattern?

The **Strategy Pattern** is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from the clients that use it.

---

### Key Concepts of the Strategy Pattern

-   **Strategy Interface**: Declares an interface common to all supported algorithms.
-   **Concrete Strategies**: Implement the algorithm using the strategy interface.
-   **Context**: Maintains a reference to a strategy object and delegates work to it.

---

### When to Use the Strategy Design Pattern

-   When you have multiple algorithms for a specific task and want to switch between them at runtime.
-   When you want to avoid conditional statements for selecting different behaviors.
-   When you have a class with many behaviors that appear as multiple conditional statements.
-   When you want to isolate the algorithm logic from the code that uses it.

---

## Example 1: Movement Strategy (From Chess Project)

In the Chess game, each piece type (Pawn, Rook, Bishop, Knight, Queen, King) has different movement rules. Instead of having a large `switch-case` or `if-else` block in the `Piece` class, movement logic is encapsulated in separate strategy classes.

#### Step 1: Define the Strategy Interface

```java
public interface MovementStrategy {
    boolean canMove(Board board, Cell startCell, Cell endCell);
}
```

#### Step 2: Implement Concrete Strategies

**Pawn Movement Strategy**

```java
public class PawnMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        Piece pawn = startCell.getPiece();
        if (pawn == null) {
            return false;
        }

        int startRow = startCell.getRow();
        int startCol = startCell.getColumn();
        int endRow = endCell.getRow();
        int endCol = endCell.getColumn();

        boolean isWhite = pawn.isWhite();
        int direction = isWhite ? -1 : 1; // White moves up, Black moves down

        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

        // Forward move (no capture)
        if (colDiff == 0) {
            // Single step forward
            if (rowDiff == direction) {
                return endCell.getPiece() == null;
            }

            // Double step from starting position
            int startingRow = isWhite ? board.getSize() - 2 : 1;
            if (startRow == startingRow && rowDiff == 2 * direction) {
                Cell middleCell = board.getCell(startRow + direction, startCol);
                return middleCell != null && middleCell.getPiece() == null
                        && endCell.getPiece() == null;
            }
        }

        // Diagonal capture
        if (colDiff == 1 && rowDiff == direction) {
            Piece targetPiece = endCell.getPiece();
            return targetPiece != null && targetPiece.isWhite() != isWhite;
        }

        return false;
    }
}
```

**Other Movement Strategies**

Similar strategies exist for:

-   `RookMovementStrategy` - Handles horizontal and vertical movement
-   `BishopMovementStrategy` - Handles diagonal movement
-   `KnightMovementStrategy` - Handles L-shaped movement
-   `QueenMovementStrategy` - Handles horizontal, vertical, and diagonal movement
-   `KingMovementStrategy` - Handles one-square movement in any direction

#### Step 3: Context (Piece Class)

```java
public abstract class Piece {
    private boolean isWhite;
    private boolean isKilled = false;
    private MovementStrategy movementStrategy;

    protected Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell);
    }

    public abstract String getSymbol();
}
```

#### Step 4: Concrete Piece Classes

```java
public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "P" : "p";
    }
}
```

> The `Piece` class doesn't know or care how movement is validated. It delegates the responsibility to the strategy object, making it easy to add new piece types or modify movement rules without changing the `Piece` class.

---

## Example 2: Player Strategy (From Chess Project)

In the Chess game, `PlayerStrategy` defines how a player makes moves. This allows for different player types: human players, AI players, or network players.

#### Step 1: Define the Strategy Interface

```java
public interface PlayerStrategy {
    Move determineMove(Board board, boolean isWhiteSide);
}
```

#### Step 2: Implement Concrete Strategies

**Human Player Strategy**

```java
public class HumanPlayerStrategy implements PlayerStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Move determineMove(Board board, boolean isWhiteSide) {
        System.out.println((isWhiteSide ? "White" : "Black") + " player's turn.");
        System.out.println("(Enter 'exit' to leave the game)");

        // Get source and destination positions from user input
        String source = getValidNotation("Enter source position (e.g., a2): ");
        String destination = getValidNotation("Enter destination position (e.g., a4): ");

        Cell startCell = board.getCellFromNotation(source);
        Cell endCell = board.getCellFromNotation(destination);

        if (startCell == null || startCell.getPiece() == null) {
            System.out.println("Invalid move: No piece at source cell.");
            return null;
        }

        return new Move(startCell, endCell);
    }
}
```

**AI Player Strategy**

```java
public class AiPlayerStrategy implements PlayerStrategy {
    @Override
    public Move determineMove(Board board, boolean isWhiteSide) {
        List<Move> validMoves = generateValidMoves(board, isWhiteSide);

        if (validMoves.isEmpty()) {
            throw new IllegalStateException("No valid moves available");
        }

        Random random = new Random();
        return validMoves.get(random.nextInt(validMoves.size()));
    }

    private List<Move> generateValidMoves(Board board, boolean isWhiteSide) {
        // Generate all valid moves for the AI player
        // Implementation details...
    }
}
```

#### Step 3: Context (Player Class)

```java
public class Player {
    private String name;
    private boolean isWhite;
    private PlayerStrategy strategy;

    public Player(String name, boolean isWhite, PlayerStrategy strategy) {
        this.name = name;
        this.isWhite = isWhite;
        this.strategy = strategy;
    }

    public Move determineMove(Board board) {
        return strategy.determineMove(board, isWhite);
    }

    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }
}
```

> This design allows you to create human players, AI players, or even network players by simply providing different strategy implementations. The `Player` class doesn't need to know how moves are determined—it just delegates to the strategy.

---

## Factory Design Pattern

### What is the Factory Design Pattern?

The **Factory Pattern** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. Instead of calling a constructor directly, you call a factory method which returns an instance of the object.

---

### Key Concepts of the Factory Pattern

-   **Factory Interface/Class**: Defines the method(s) for creating objects.
-   **Concrete Factory**: Implements the factory to create specific objects.
-   **Product**: The object being created by the factory.

---

### When to Use the Factory Design Pattern

-   When a class doesn't know ahead of time what class of objects it needs to create.
-   When you want to delegate object creation to a centralized location.
-   When you need to centralize complex object creation logic.
-   When you want to decouple the code that uses objects from the code that creates them.

---

## Example: Piece Factory (From Chess Project)

The `PieceFactory` creates different types of chess pieces based on the piece type and color.

#### Step 1: Define the Product (Abstract Class)

```java
public abstract class Piece {
    private boolean isWhite;
    private boolean isKilled = false;
    private MovementStrategy movementStrategy;

    protected Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell);
    }

    public abstract String getSymbol();
}
```

#### Step 2: Implement Concrete Products

```java
public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "P" : "p";
    }
}

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "R" : "r";
    }
}

// Similar implementations for Knight, Bishop, Queen, King
```

#### Step 3: Implement the Factory

```java
public class PieceFactory {
    public static Piece createPiece(PieceType pieceType, boolean isWhite) {
        switch (pieceType) {
            case PAWN:
                return new Pawn(isWhite);
            case ROOK:
                return new Rook(isWhite);
            case KNIGHT:
                return new Knight(isWhite);
            case BISHOP:
                return new Bishop(isWhite);
            case QUEEN:
                return new Queen(isWhite);
            case KING:
                return new King(isWhite);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}
```

#### Step 4: Client Code (Board Initialization)

```java
public class Board {
    private void setPieceRow(int row, boolean isWhite) {
        board[row][0] = new Cell(row, 0, PieceFactory.createPiece(PieceType.ROOK, isWhite));
        board[row][1] = new Cell(row, 1, PieceFactory.createPiece(PieceType.KNIGHT, isWhite));
        board[row][2] = new Cell(row, 2, PieceFactory.createPiece(PieceType.BISHOP, isWhite));
        board[row][3] = new Cell(row, 3, PieceFactory.createPiece(PieceType.QUEEN, isWhite));
        board[row][4] = new Cell(row, 4, PieceFactory.createPiece(PieceType.KING, isWhite));
        board[row][5] = new Cell(row, 5, PieceFactory.createPiece(PieceType.BISHOP, isWhite));
        board[row][6] = new Cell(row, 6, PieceFactory.createPiece(PieceType.KNIGHT, isWhite));
        board[row][7] = new Cell(row, 7, PieceFactory.createPiece(PieceType.ROOK, isWhite));
    }
}
```

> The client code doesn't need to know which specific piece class is instantiated. It delegates creation to the factory, making it easy to add new piece types or modify piece creation logic in one place.

---

## Observer Design Pattern

### What is the Observer Design Pattern?

The **Observer Pattern** is a behavioral design pattern that defines a one-to-many dependency between objects. When one object (the subject) changes state, all its dependents (observers) are notified and updated automatically.

---

### Key Concepts of the Observer Pattern

-   **Subject**: The object being observed. It maintains a list of observers and notifies them of state changes.
-   **Observer Interface**: Defines the update method that observers must implement.
-   **Concrete Observers**: Implement the observer interface to react to state changes.

---

### When to Use the Observer Design Pattern

-   When changes to one object require changing others, and you don't know how many objects need to change.
-   When an object should notify other objects without knowing who those objects are.
-   When you want to implement distributed event handling systems.
-   When you need a publish-subscribe relationship between objects.

---

## Example: Game Event Listener (From Chess Project)

In the Chess game, a `GameEventListener` observes game events like moves and state changes. This enables features like logging, UI updates, or analytics without modifying the core game logic.

#### Step 1: Define the Observer Interface

```java
public interface GameEventListener {
    void onMoveMade(Move move);
    void onGameStateChanged(Status state);
}
```

#### Step 2: Implement Concrete Observer

```java
public class ConsoleGameEventListener implements GameEventListener {
    private final Board board;

    public ConsoleGameEventListener(Board board) {
        this.board = board;
    }

    @Override
    public void onMoveMade(Move move) {
        System.out.println("Move made from: " + move.getStartCell().getLabel()
            + " to " + move.getEndCell().getLabel());
        System.out.println(board.renderBoard());
    }

    @Override
    public void onGameStateChanged(Status state) {
        System.out.println("Game state changed to: " + state);
    }
}
```

#### Step 3: Subject (ChessGame Class)

```java
public class ChessGame implements BoardGames {
    private GameEventListener listener;

    public void setObserver(GameEventListener listener) {
        this.listener = listener;
    }

    public void makeMove(Move move, Player player) {
        // ... move logic ...

        if (destinationPiece instanceof King) {
            this.status = isWhiteTurn ? Status.WHITE_WIN : Status.BLACK_WIN;
            notifyGameStateChanged(this.status);
            return;
        }

        // ... move execution ...

        notifyMoveMade(move);
    }

    private void notifyMoveMade(Move move) {
        if (listener != null) {
            listener.onMoveMade(move);
        }
    }

    private void notifyGameStateChanged(Status state) {
        if (listener != null) {
            listener.onGameStateChanged(state);
        }
    }
}
```

#### Step 4: Client Code

```java
public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player1", true, new HumanPlayerStrategy());
        Player player2 = new Player("Player2", false, new AiPlayerStrategy());

        ChessGame chessGame = new ChessGame(player1, player2);
        chessGame.setObserver(new ConsoleGameEventListener(chessGame.getBoard()));
        chessGame.start();
    }
}
```

> This pattern enables features like logging, UI updates, network synchronization, or analytics without modifying the core game logic. Just add new listeners! For example, you could create a `FileLoggerEventListener` to log moves to a file, or a `NetworkEventListener` to broadcast moves over a network.

---

## Singleton Design Pattern

### What is the Singleton Design Pattern?

The **Singleton Pattern** is a creational design pattern that ensures a class has only one instance and provides a global point of access to that instance.

---

### Key Concepts of the Singleton Pattern

-   **Private Constructor**: Prevents instantiation from outside the class.
-   **Static Instance**: Holds the single instance of the class.
-   **Static Getter Method**: Provides access to the single instance.

---

### When to Use the Singleton Design Pattern

-   When you need exactly one instance of a class.
-   When you want to control access to a shared resource.
-   When you need a global point of access to an object.

---

## Example: Board Singleton (From Chess Project)

In the Chess game, the `Board` class uses the Singleton pattern to ensure there is only one board instance throughout the game.

#### Implementation

```java
public class Board {
    private static Board instance;
    private Cell[][] board;

    private Board(int rows) {
        initializeBoard(rows);
    }

    public static Board getInstance(int rows) {
        if (instance == null) {
            instance = new Board(rows);
        }
        return instance;
    }

    private void initializeBoard(int rows) {
        board = new Cell[rows][rows];
        // Initialize board with pieces...
    }

    // Other board methods...
}
```

#### Usage

```java
public class ChessGame {
    public ChessGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = Board.getInstance(8); // Get the singleton instance
        this.isWhiteTurn = true;
        this.status = Status.ACTIVE;
        this.gameLog = new ArrayList<>();
    }
}
```

> The Singleton pattern ensures that there is only one board instance in the game, preventing inconsistencies and ensuring all game components reference the same board state.

---

## Template Method Pattern (Implicit)

### What is the Template Method Pattern?

The **Template Method Pattern** defines the skeleton of an algorithm in a base class, allowing subclasses to override specific steps without changing the algorithm's structure.

---

### Example: Piece Abstract Class (From Chess Project)

The `Piece` abstract class provides a template for all chess pieces, with subclasses implementing specific behavior.

#### Base Class (Template)

```java
public abstract class Piece {
    private boolean isWhite;
    private boolean isKilled = false;
    private MovementStrategy movementStrategy;

    protected Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    // Common methods shared by all pieces
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell, endCell);
    }

    // Template method - subclasses must implement
    public abstract String getSymbol();
}
```

#### Concrete Implementations

```java
public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "P" : "p";
    }
}

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMovementStrategy());
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "R" : "r";
    }
}
```

> The `Piece` abstract class defines the common structure and behavior for all pieces, while allowing each piece type to customize its symbol representation. This ensures consistency while providing flexibility.

---

## Design Patterns Summary

| Pattern             | Type       | Purpose                                  | Key Benefit                                           |
| ------------------- | ---------- | ---------------------------------------- | ----------------------------------------------------- |
| **Strategy**        | Behavioral | Encapsulates interchangeable algorithms  | Runtime algorithm selection without conditionals      |
| **Factory**         | Creational | Abstracts object creation                | Decouples client from concrete classes                |
| **Observer**        | Behavioral | One-to-many dependency notification      | Loose coupling between subject and observers          |
| **Singleton**       | Creational | Ensures single instance of a class       | Controlled access to shared resource                  |
| **Template Method** | Behavioral | Defines algorithm skeleton in base class | Code reuse and consistent structure across subclasses |

---

## Design Patterns in This Project

| Pattern             | Implementation                                  | Purpose                                  |
| ------------------- | ----------------------------------------------- | ---------------------------------------- |
| **Strategy**        | `MovementStrategy`, `PlayerStrategy`            | Flexible movement rules and player types |
| **Factory**         | `PieceFactory`                                  | Centralized piece creation               |
| **Observer**        | `GameEventListener`, `ConsoleGameEventListener` | Game event notification system           |
| **Singleton**       | `Board`                                         | Single board instance                    |
| **Template Method** | `Piece` (abstract class)                        | Common structure for all pieces          |

---

## Why These Patterns?

### Strategy Pattern for Movement

Each chess piece has unique movement rules. Using the Strategy pattern:

-   **Eliminates complex conditionals**: No need for large `if-else` or `switch` statements
-   **Easy to extend**: Add new piece types by creating new movement strategies
-   **Separation of concerns**: Movement logic is isolated from piece representation

### Strategy Pattern for Players

Different players (human, AI, network) have different move determination logic:

-   **Flexible player types**: Switch between human and AI players easily
-   **Testability**: Mock player strategies for testing
-   **Extensibility**: Add new player types (e.g., network players) without changing core game logic

### Factory Pattern for Pieces

Creating pieces involves selecting the right class and movement strategy:

-   **Centralized creation**: All piece creation logic in one place
-   **Simplified client code**: Board initialization doesn't need to know piece constructors
-   **Easy maintenance**: Change piece creation logic in one location

### Observer Pattern for Game Events

Game events (moves, state changes) need to be communicated to various components:

-   **Loose coupling**: Game logic doesn't depend on specific listeners
-   **Multiple observers**: Add logging, UI updates, analytics without modifying game code
-   **Event-driven architecture**: Clean separation between game logic and presentation

### Singleton Pattern for Board

The chess board should be unique throughout the game:

-   **Single source of truth**: All components reference the same board
-   **State consistency**: Prevents multiple board instances with different states
-   **Resource management**: Ensures efficient memory usage

### Template Method Pattern for Pieces

All pieces share common structure but differ in specific details:

-   **Code reuse**: Common behavior defined once in base class
-   **Consistency**: All pieces follow the same interface
-   **Flexibility**: Subclasses customize only what's needed (symbol representation)

---

## Benefits of This Design

1.  **Maintainability**: Each pattern isolates concerns, making code easier to understand and modify
2.  **Extensibility**: New piece types, player types, or listeners can be added without changing existing code
3.  **Testability**: Strategies and observers can be easily mocked for unit testing
4.  **Separation of Concerns**: Movement logic, player logic, and game events are cleanly separated
5.  **Flexibility**: Runtime selection of algorithms (strategies) allows for dynamic behavior
