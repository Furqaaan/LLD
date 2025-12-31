package inventory_management.product;

import inventory_management.enums.ProductCategory;

public class ProductFactory {
    public Product createProduct(ProductCategory category, String sku, String name, double price, int quantity,
            int threshold) {
        return ProductBuilder.builder()
                .setCategory(category)
                .setSku(sku)
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setThreshold(threshold)
                .build();
    }
}
