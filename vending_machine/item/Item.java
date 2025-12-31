package vending_machine.item;

import vending_machine.enums.ItemType;

public class Item {
    private ItemType type;

    private int price;

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
