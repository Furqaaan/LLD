package snake_food.food;

public class NormalFood extends FoodItem {
    public NormalFood(int row, int column) {
        super(row, column);
        this.points = 1;
    }
}
