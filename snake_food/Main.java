package snake_food;

import snake_food.game.SnakeGame;

public class Main {
    public static void main(String[] args) {
        int width = 20;
        int height = 15;

        int[][] foodPositions = {
                { 5, 5 },
                { 10, 8 },
                { 3, 12 },
                { 8, 17 },
                { 12, 3 }
        };

        SnakeGame game = new SnakeGame(width, height, foodPositions);
        game.play();
    }
}
