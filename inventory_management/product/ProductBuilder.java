package inventory_management.product;

import inventory_management.enums.ProductCategory;
import java.util.Date;

public class ProductBuilder {
    // Common Product fields
    private String sku;
    private String name;
    private double price;
    private int quantity;
    private int threshold;
    private ProductCategory category;

    // ClothingProduct specific fields
    private String size;
    private String color;

    // ElectronicsProduct specific fields
    private String brand;
    private int warrantyPeriod;

    // GroceryProduct specific fields
    private Date expiryDate;
    private boolean refrigerated;

    // Common setters
    public ProductBuilder setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder setThreshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    public ProductBuilder setCategory(ProductCategory category) {
        this.category = category;
        return this;
    }

    // ClothingProduct specific setters
    public ProductBuilder setSize(String size) {
        this.size = size;
        return this;
    }

    public ProductBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    // ElectronicsProduct specific setters
    public ProductBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
        return this;
    }

    // GroceryProduct specific setters
    public ProductBuilder setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public ProductBuilder setRefrigerated(boolean refrigerated) {
        this.refrigerated = refrigerated;
        return this;
    }

    // Build methods for each product type
    public Product buildClothingProduct() {
        if (sku == null || name == null) {
            throw new IllegalArgumentException("SKU and Name are required");
        }
        ClothingProduct product = new ClothingProduct(sku, name, price, quantity, threshold);
        if (size != null)
            product.setSize(size);
        if (color != null)
            product.setColor(color);
        return product;
    }

    public Product buildElectronicsProduct() {
        if (sku == null || name == null) {
            throw new IllegalArgumentException("SKU and Name are required");
        }
        ElectronicsProduct product = new ElectronicsProduct(sku, name, price, quantity, threshold);
        if (brand != null)
            product.setBrand(brand);
        if (warrantyPeriod > 0)
            product.setWarrantyPeriod(warrantyPeriod);
        return product;
    }

    public Product buildGroceryProduct() {
        if (sku == null || name == null) {
            throw new IllegalArgumentException("SKU and Name are required");
        }
        GroceryProduct product = new GroceryProduct(sku, name, price, quantity, threshold);
        if (expiryDate != null)
            product.setExpiryDate(expiryDate);
        product.setRefrigerated(refrigerated);
        return product;
    }

    public Product build() {
        if (category == null) {
            throw new IllegalArgumentException("Category is required");
        }

        switch (category) {
            case CLOTHING:
                return buildClothingProduct();
            case ELECTRONICS:
                return buildElectronicsProduct();
            case GROCERY:
                return buildGroceryProduct();
            default:
                throw new IllegalArgumentException("Unsupported product category: " + category);
        }
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
}
