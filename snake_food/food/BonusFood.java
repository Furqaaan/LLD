package snake_food.food;

public class BonusFood extends FoodItem {
    public BonusFood(int row, int column) {
        super(row, column);
        this.points = 3;
    }
}
