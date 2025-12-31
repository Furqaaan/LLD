package vending_machine.item;

import java.util.List;
import java.util.ArrayList;

public class ItemShelf {
    private int code;
    private List<Item> items;
    private boolean isSoldOut;

    public ItemShelf(int code) {
        this.code = code;
        this.items = new ArrayList<>();
        this.isSoldOut = false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean checkIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        if (isSoldOut)
            setIsSoldOut(false);
    }

    public void addItem(Item item) {
        items.add(item);
        if (isSoldOut)
            setIsSoldOut(false);
    }

    public void removeItem(Item item) {
        items.remove(item);
        if (items.isEmpty())
            setIsSoldOut(true);
    }

    public boolean hasItems() {
        return !checkIsSoldOut();
    }
}
