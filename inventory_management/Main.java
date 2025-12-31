package inventory_management;

import inventory_management.inventory.InventoryManager;
import inventory_management.warehouse.Warehouse;
import inventory_management.product.ProductFactory;
import inventory_management.product.Product;
import inventory_management.enums.ProductCategory;
import inventory_management.replenishment.JustInTimeStrategy;
import inventory_management.replenishment.BulkOrderStrategy;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();

        Warehouse warehouse1 = new Warehouse(1, "Warehouse 1", "Location 1");
        Warehouse warehouse2 = new Warehouse(2, "Warehouse 2", "Location 2");
        inventoryManager.addWarehouse(warehouse1);
        inventoryManager.addWarehouse(warehouse2);

        ProductFactory productFactory = new ProductFactory();
        Product laptop = productFactory.createProduct(
                ProductCategory.ELECTRONICS, "SKU123", "Laptop", 1000.0, 50, 25);
        Product tShirt = productFactory.createProduct(
                ProductCategory.CLOTHING, "SKU456", "T-Shirt", 20.0, 200, 100);
        Product apple = productFactory.createProduct(
                ProductCategory.GROCERY, "SKU789", "Apple", 1.0, 100, 200);

        warehouse1.addProduct(laptop, 15);
        warehouse1.addProduct(tShirt, 20);
        warehouse2.addProduct(apple, 50);

        inventoryManager.setReplenishmentStrategy(new JustInTimeStrategy());
        inventoryManager.performInventoryCheck();
        inventoryManager.setReplenishmentStrategy(new BulkOrderStrategy());
        inventoryManager.checkAndReplenish("SKU123");
    }
}