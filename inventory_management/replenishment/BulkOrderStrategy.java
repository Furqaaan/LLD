package inventory_management.replenishment;

import inventory_management.product.Product;

public class BulkOrderStrategy implements ReplenishmentStrategy {
    public void replenish(Product product) {
        System.out.println("Applying Bulk Order replenishment for " + product.getName());
    }
}
