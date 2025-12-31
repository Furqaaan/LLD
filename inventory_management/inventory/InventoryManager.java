package inventory_management.inventory;

import java.util.List;
import inventory_management.warehouse.Warehouse;
import inventory_management.replenishment.ReplenishmentStrategy;
import java.util.ArrayList;
import inventory_management.product.Product;
import inventory_management.observer.InventoryObserver;

public class InventoryManager {
    private static InventoryManager instance;
    private List<InventoryObserver> observers;
    private List<Warehouse> warehouses;
    private ReplenishmentStrategy replenishmentStrategy;

    private InventoryManager() {
        warehouses = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Synchronized to prevent multiple threads from creating multiple instances
    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void setReplenishmentStrategy(ReplenishmentStrategy strategy) {
        this.replenishmentStrategy = strategy;
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    public void removeWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }

    public Product getProductBySku(String sku) {
        for (Warehouse warehouse : warehouses) {
            Product product = warehouse.getProductBySku(sku);
            if (product != null) {
                return product;
            }
        }
        return null;
    }

    public void checkAndReplenish(String sku) {
        Product product = getProductBySku(sku);
        if (product != null) {
            if (product.getQuantity() < product.getThreshold()) {
                notifyObservers(product);
                if (replenishmentStrategy != null) {
                    replenishmentStrategy.replenish(product);
                }
            }
        }
    }

    public void performInventoryCheck() {
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getAllProducts()) {
                if (product.getQuantity() < product.getThreshold()) {
                    notifyObservers(product);
                    if (replenishmentStrategy != null) {
                        replenishmentStrategy.replenish(product);
                    }
                }
            }
        }
    }

    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
      }
    
      public void removeObserver(InventoryObserver observer) {
        observers.remove(observer);
      }
    
      public void notifyObservers(Product product) {
        for (InventoryObserver observer : observers) {
          observer.update(product);
        }
      }
}