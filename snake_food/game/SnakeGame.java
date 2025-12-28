package snake_food.game;

import snake_food.board.Board;
import snake_food.board.Cell;
import snake_food.snake.Snake;
import java.util.Scanner;

public class SnakeGame {
    private Board board;
    private Snake snake;
    private int[][] food;
    private int foodIndex;

    public SnakeGame(int width, int height, int[][] food) {
        this.board = Board.getInstance(width, height);
        this.food = food;
        this.foodIndex = 0;
        this.snake = new Snake();
    }

    public void play() {
        System.out.println("===== SNAKE GAME =====");
        System.out.println(
                "Controls: W (Up), S (Down), A (Left), D (Right), Q (Quit)");
        System.out.println("Eat food to grow your snake and increase your score.");
        System.out.println("Don't hit the walls or bite yourself!");
        System.out.println("=======================");

        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;
        int score = 0;

        while (gameRunning) {
            displayGameState(this);

            System.out.print("Enter move (W/A/S/D) or Q to quit: ");
            String direction = scanner.nextLine().toUpperCase();

            if (direction.equals("Q")) {
                System.out.println("Game ended by player. Final score: " + score);
                gameRunning = false;
                continue;
            }

            if (direction.isEmpty()) {
                System.out.println("Invalid input! Use W/A/S/D to move or Q to quit.");
                continue;
            }

            int bodySizeBefore = snake.getBody().size();
            score = snake.move(board, direction, food, this.foodIndex);
            int bodySizeAfter = snake.getBody().size();

            // Update foodIndex if snake ate food (body size increased)
            if (bodySizeAfter > bodySizeBefore && this.foodIndex < food.length) {
                this.foodIndex++;
            }

            if (score == -1) {
                System.out.println("GAME OVER! You hit a wall or bit yourself.");
                System.out.println("Final score: " + (snake.getBody().size() - 1));
                gameRunning = false;
            } else {
                System.out.println("Score: " + score);
            }
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }

    private static void displayGameState(SnakeGame game) {
        int width = game.board.getWidth();
        int height = game.board.getHeight();

        // Create a 2D char array to represent the board
        char[][] grid = new char[height][width];

        // Initialize all cells as empty
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '.';
            }
        }

        // Mark food position first if available
        if (game.foodIndex < game.food.length) {
            int foodRow = game.food[game.foodIndex][0];
            int foodCol = game.food[game.foodIndex][1];
            if (foodRow >= 0 && foodRow < height && foodCol >= 0 && foodCol < width) {
                grid[foodRow][foodCol] = 'F';
            }
        }

        // Mark all snake body positions as 'S' (trails) - this will overwrite food if
        // snake is eating
        // Iterate through all positions in the snake's body
        // The body is a Deque, so we iterate through it to get all positions
        for (Cell cell : game.snake.getBody()) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (row >= 0 && row < height && col >= 0 && col < width) {
                grid[row][col] = 'S';
            }
        }

        // Display the board
        System.out.println("\n--- Game Board ---");
        System.out.println("Current snake length: " + game.snake.getBody().size());
        System.out.println("Score: " + (game.snake.getBody().size() - 1));

        // Print top border
        System.out.print("  ");
        for (int j = 0; j < width; j++) {
            System.out.print("-");
        }
        System.out.println();

        // Print board with row numbers
        for (int i = 0; i < height; i++) {
            System.out.print(String.format("%2d|", i));
            for (int j = 0; j < width; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("|");
        }

        // Print bottom border
        System.out.print("  ");
        for (int j = 0; j < width; j++) {
            System.out.print("-");
        }
        System.out.println();

        // Print column numbers
        System.out.print("   ");
        for (int j = 0; j < width && j < 10; j++) {
            System.out.print(j);
        }
        for (int j = 10; j < width; j++) {
            System.out.print(j % 10);
        }
        System.out.println();
        System.out.println("Legend: S = Snake (all positions), F = Food, . = Empty");
    }
}