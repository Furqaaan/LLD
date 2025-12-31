# Inventory Management - Design Patterns

## Singleton Design Pattern

### Inventory Manager Singleton

The **Singleton Pattern** ensures there is only one `InventoryManager` instance throughout the application.

#### Implementation

```java
public class InventoryManager {
    private static InventoryManager instance;

    private InventoryManager() {
        warehouses = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }
}
```

> The Singleton pattern ensures centralized inventory management and prevents multiple instances that could cause data inconsistency.

---

## Strategy Design Pattern

### Replenishment Strategy

The **Strategy Pattern** is used for inventory replenishment, allowing different replenishment algorithms (Just-In-Time vs Bulk Order).

#### Implementation

**Strategy Interface**

```java
public interface ReplenishmentStrategy {
    void replenish(Product product);
}
```

**Concrete Strategies**

-   `JustInTimeStrategy` - Replenishes products just when needed
-   `BulkOrderStrategy` - Orders products in bulk quantities

**Context (InventoryManager)**

```java
public class InventoryManager {
    private ReplenishmentStrategy replenishmentStrategy;

    public void setReplenishmentStrategy(ReplenishmentStrategy strategy) {
        this.replenishmentStrategy = strategy;
    }

    public void checkAndReplenish(String sku) {
        // ... check logic ...
        if (replenishmentStrategy != null) {
            replenishmentStrategy.replenish(product);
        }
    }
}
```

> The `InventoryManager` delegates replenishment logic to the strategy, making it easy to switch between different replenishment approaches without modifying core inventory logic.

---

## Observer Design Pattern

### Inventory Observer

The **Observer Pattern** notifies multiple observers when inventory levels fall below threshold.

#### Implementation

**Observer Interface**

```java
public interface InventoryObserver {
    void update(Product product);
}
```

**Concrete Observers**

-   `DashboardAlertSystem` - Displays alerts on dashboard
-   `SupplierNotifier` - Notifies suppliers when stock is low

**Subject (InventoryManager)**

```java
public class InventoryManager {
    private List<InventoryObserver> observers;

    public void notifyObservers(Product product) {
        for (InventoryObserver observer : observers) {
            observer.update(product);
        }
    }
}
```

> The Observer pattern enables loose coupling between inventory management and notification systems, allowing easy addition of new alert mechanisms.

---

## Factory Design Pattern

### Product Factory

The **Factory Pattern** is used to create different types of products (Electronics, Clothing, Grocery) based on category.

#### Implementation

```java
public class ProductFactory {
    public Product createProduct(ProductCategory category, String sku,
            String name, double price, int quantity, int threshold) {
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
```

> The factory centralizes product creation logic and uses the builder pattern internally for flexible product construction.

---

## Builder Design Pattern

### Product Builder

The **Builder Pattern** constructs products step-by-step with a fluent interface, supporting different product types with optional fields.

#### Implementation

```java
public class ProductBuilder {
    public ProductBuilder setSku(String sku) { ... }
    public ProductBuilder setName(String name) { ... }
    public ProductBuilder setPrice(double price) { ... }
    // ... other setters ...

    public Product build() {
        switch (category) {
            case CLOTHING:
                return buildClothingProduct();
            case ELECTRONICS:
                return buildElectronicsProduct();
            case GROCERY:
                return buildGroceryProduct();
        }
    }
}
```

**Usage**

```java
Product product = ProductBuilder.builder()
    .setSku("SKU123")
    .setName("Laptop")
    .setPrice(1000.0)
    .setQuantity(50)
    .setThreshold(25)
    .setCategory(ProductCategory.ELECTRONICS)
    .setBrand("TechBrand")
    .setWarrantyPeriod(24)
    .build();
```

> The Builder pattern provides a clean, readable way to construct complex product objects with many optional fields, avoiding telescoping constructors.

---

## Design Patterns Summary

| Pattern       | Type       | Purpose                           | Implementation          |
| ------------- | ---------- | --------------------------------- | ----------------------- |
| **Singleton** | Creational | Ensures single manager instance   | `InventoryManager`      |
| **Strategy**  | Behavioral | Encapsulates replenishment logic  | `ReplenishmentStrategy` |
| **Observer**  | Behavioral | Inventory change notifications    | `InventoryObserver`     |
| **Factory**   | Creational | Abstracts product creation        | `ProductFactory`        |
| **Builder**   | Creational | Step-by-step product construction | `ProductBuilder`        |
