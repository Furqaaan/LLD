package inventory_management.product;

import java.util.Date;
import inventory_management.enums.ProductCategory;

public class GroceryProduct extends Product {
    private Date expiryDate;
    private boolean refrigerated;

    public GroceryProduct(String sku, String name, double price, int quantity, int threshold) {
        super();
        setSku(sku);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setCategory(ProductCategory.GROCERY);
        setThreshold(threshold);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isRefrigerated() {
        return refrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        this.refrigerated = refrigerated;
    }
}