package inventory_management.replenishment;

import inventory_management.product.Product;

public class JustInTimeStrategy implements ReplenishmentStrategy {
    public void replenish(Product product) {
        System.out.println("Applying Just-In-Time replenishment for " + product.getName());
    }
}
