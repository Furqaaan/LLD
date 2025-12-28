package snake_food.food;

public abstract class FoodItem {
    protected int row, column;
    protected int points;

    public FoodItem(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPoints() {
        return points;
    }
}