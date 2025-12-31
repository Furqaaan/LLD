package inventory_management.replenishment;

import inventory_management.product.Product;

public interface ReplenishmentStrategy {
    void replenish(Product product);
}
