package snake_food.food;

public class FoodFactory {
    public static FoodItem createFood(int[] position, String type) {
        if ("bonus".equals(type)) {
            return new BonusFood(position[0], position[1]);
        }
        
        return new NormalFood(position[0], position[1]);
    }
}