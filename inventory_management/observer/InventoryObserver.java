package inventory_management.observer;

import inventory_management.product.Product;

public interface InventoryObserver {
    void update(Product product);
}