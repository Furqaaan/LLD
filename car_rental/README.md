# Car Rental System - Design Patterns

This document explains the design patterns used in the Car Rental system implementation.

---

## Singleton Design Pattern

### What is the Singleton Design Pattern?

The **Singleton Pattern** is a creational design pattern that ensures a class has only one instance and provides a global point of access to that instance.

---

### Key Concepts of the Singleton Pattern

-   **Private Constructor**: Prevents instantiation from outside the class.
-   **Static Instance**: Holds the single instance of the class.
-   **Static Getter Method**: Provides access to the single instance.

---

### When to Use the Singleton Design Pattern

-   When you need exactly one instance of a class.
-   When you want to control access to a shared resource.
-   When you need a global point of access to an object.

---

## Example: Rental System Singleton (From Car Rental Project)

In the Car Rental system, the `RentalSystem` class uses the Singleton pattern to ensure there is only one rental system instance throughout the application.

#### Implementation

```java
public class RentalSystem {
    private static RentalSystem instance;
    private List<RentalStore> stores;
    private VehicleFactory vehicleFactory;
    private ReservationManager reservationManager;
    private PaymentProcessor paymentProcessor;
    private Map<Integer, User> users;

    private RentalSystem() {
        this.stores = new ArrayList<>();
        this.vehicleFactory = new VehicleFactory();
        this.reservationManager = new ReservationManager();
        this.paymentProcessor = new PaymentProcessor();
        this.users = new HashMap<>();
    }

    public static synchronized RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    // Other methods...
}
```

#### Usage

```java
public class Main {
    public static void main(String[] args) {
        // Get the Rental System instance (Singleton)
        RentalSystem rentalSystem = RentalSystem.getInstance();

        // Use the rental system...
    }
}
```

> The Singleton pattern ensures centralized rental management and prevents multiple instances that could cause data inconsistency across the application.

---

## Strategy Design Pattern

### What is the Strategy Design Pattern?

The **Strategy Pattern** is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from the clients that use it.

---

### Key Concepts of the Strategy Pattern

-   **Strategy Interface**: Declares an interface common to all supported algorithms.
-   **Concrete Strategies**: Implement the algorithm using the strategy interface.
-   **Context**: Maintains a reference to a strategy object and delegates work to it.

---

### When to Use the Strategy Design Pattern

-   When you have multiple algorithms for a specific task and want to switch between them at runtime.
-   When you want to avoid conditional statements for selecting different behaviors.
-   When you have a class with many behaviors that appear as multiple conditional statements.
-   When you want to isolate the algorithm logic from the code that uses it.

---

## Example 1: Payment Strategy (From Car Rental Project)

In the Car Rental system, `PaymentStrategy` defines how payments are processed. Different strategies can handle different payment methods (Credit Card, Cash, PayPal).

#### Step 1: Define the Strategy Interface

```java
public interface PaymentStrategy {
    void processPayment(double amount);
}
```

#### Step 2: Implement Concrete Strategies

**Credit Card Payment**

```java
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}
```

**Cash Payment**

```java
public class CashPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount);
    }
}
```

**PayPal Payment**

```java
public class PayPalPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}
```

#### Step 3: Context (PaymentProcessor Class)

```java
public class PaymentProcessor {
    public boolean processPayment(double amount, PaymentStrategy paymentStrategy) {
        paymentStrategy.processPayment(amount);
        return true;
    }
}
```

#### Step 4: Client Usage

```java
PaymentStrategy paymentStrategy = PaymentFactory.createPaymentStrategy(PaymentType.CREDIT_CARD);
boolean paymentSuccess = rentalSystem.processPayment(reservation1.getId(), paymentStrategy);
```

> The `PaymentProcessor` class doesn't know or care how the payment is processed. It delegates the responsibility to the strategy object, making it easy to add new payment methods without modifying existing code.

---

## Example 2: Pricing Strategy (From Car Rental Project)

The pricing system uses the Strategy Pattern to support different pricing models (Daily, Hourly).

#### Step 1: Define the Strategy Interface

```java
public interface PricingStrategy {
    double calculateRentalPrice(Vehicle vehicle, int rentalPeriod);
}
```

#### Step 2: Implement Concrete Strategies

**Daily Pricing Strategy**

```java
public class DailyPricingStrategy implements PricingStrategy {
    @Override
    public double calculateRentalPrice(Vehicle vehicle, int rentalPeriod) {
        return vehicle.getBaseRentalPrice() * rentalPeriod;
    }
}
```

**Hourly Pricing Strategy**

```java
public class HourlyPricingStrategy implements PricingStrategy {
    private static final double HOURLY_RATE_MULTIPLIER = 0.2; // 20% of daily rate per hour

    @Override
    public double calculateRentalPrice(Vehicle vehicle, int rentalPeriod) {
        double dailyRate = vehicle.getBaseRentalPrice();
        return dailyRate * HOURLY_RATE_MULTIPLIER * rentalPeriod;
    }
}
```

#### Step 3: Context (Reservation Class)

```java
public class Reservation {
    private PricingStrategy pricingStrategy;

    public Reservation(..., PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double getTotalAmount() {
        int rentalPeriod = calculateRentalPeriod();
        return pricingStrategy.calculateRentalPrice(vehicle, rentalPeriod);
    }
}
```

#### Step 4: Client Usage

```java
Reservation reservation1 = rentalSystem.createReservation(
    user1.getId(),
    economyCar.getRegistrationNumber(),
    store1.getId(),
    store1.getId(),
    startDate,
    endDate,
    new DailyPricingStrategy()
);
```

> This design allows adding new pricing models (Weekly, Monthly, Seasonal) without modifying existing code—just create a new strategy class.

---

## Factory Design Pattern

### What is the Factory Design Pattern?

The **Factory Pattern** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. Instead of calling a constructor directly, you call a factory method which returns an instance of the object.

---

### Key Concepts of the Factory Pattern

-   **Factory Interface/Class**: Defines the method(s) for creating objects.
-   **Concrete Factory**: Implements the factory to create specific objects.
-   **Product**: The object being created by the factory.

---

### When to Use the Factory Design Pattern

-   When a class doesn't know ahead of time what class of objects it needs to create.
-   When you want to delegate object creation to a centralized location.
-   When you need to centralize complex object creation logic.
-   When you want to decouple the code that uses objects from the code that creates them.

---

## Example 1: Vehicle Factory (From Car Rental Project)

The `VehicleFactory` creates different types of vehicles based on the vehicle type.

#### Step 1: Define the Product (Abstract Class)

```java
public abstract class Vehicle {
    private String registrationNumber;
    private String model;
    private VehicleType type;
    private VehicleStatus status;
    private double baseRentalPrice;

    public Vehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.baseRentalPrice = baseRentalPrice;
    }

    public abstract double calculateRentalFee(int days);
}
```

#### Step 2: Implement Concrete Products

```java
public class EconomyVehicle extends Vehicle {
    public EconomyVehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registrationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * 1.0;
    }
}

public class LuxuryVehicle extends Vehicle {
    public LuxuryVehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registrationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * 1.5; // Luxury vehicles have premium pricing
    }
}

public class SUVVehicle extends Vehicle {
    public SUVVehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registrationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * 1.2; // SUVs have slightly higher pricing
    }
}
```

#### Step 3: Implement the Factory

```java
public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType vehicleType, String registrationNumber,
            String model, double baseRentalPrice) {
        switch (vehicleType) {
            case ECONOMY:
                return new EconomyVehicle(registrationNumber, model, vehicleType, baseRentalPrice);
            case LUXURY:
                return new LuxuryVehicle(registrationNumber, model, vehicleType, baseRentalPrice);
            case SUV:
                return new SUVVehicle(registrationNumber, model, vehicleType, baseRentalPrice);
            default:
                throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        }
    }
}
```

#### Step 4: Client Code

```java
// Create vehicles using Factory Pattern
Vehicle economyCar = VehicleFactory.createVehicle(
    VehicleType.ECONOMY, "EC001", "Toyota", 50.0);
Vehicle luxuryCar = VehicleFactory.createVehicle(
    VehicleType.LUXURY, "LX001", "Mercedes", 200.0);
Vehicle suvCar = VehicleFactory.createVehicle(
    VehicleType.SUV, "SV001", "Honda", 75.0);
```

> The client code doesn't need to know which specific vehicle class is instantiated. It delegates creation to the factory, making it easy to add new vehicle types or modify vehicle creation logic in one place.

---

## Example 2: Payment Factory (From Car Rental Project)

The `PaymentFactory` creates the appropriate payment strategy based on the payment type.

#### Implementation

```java
public class PaymentFactory {
    public static PaymentStrategy createPaymentStrategy(PaymentType paymentType) {
        switch (paymentType) {
            case CREDIT_CARD:
                return new CreditCardPayment();
            case CASH:
                return new CashPayment();
            case PAYPAL:
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
```

#### Usage

```java
// User selects payment method
int choice = 1; // Credit Card
PaymentType paymentType;
switch (choice) {
    case 1:
        paymentType = PaymentType.CREDIT_CARD;
        break;
    case 2:
        paymentType = PaymentType.CASH;
        break;
    case 3:
        paymentType = PaymentType.PAYPAL;
        break;
    default:
        paymentType = PaymentType.CREDIT_CARD;
        break;
}

// Create payment strategy using Factory Pattern
PaymentStrategy paymentStrategy = PaymentFactory.createPaymentStrategy(paymentType);
```

> This factory pattern centralizes payment strategy creation logic and makes it easy to extend with new payment methods without modifying client code.

---

## Template Method Pattern (Implicit)

### What is the Template Method Pattern?

The **Template Method Pattern** defines the skeleton of an algorithm in a base class, allowing subclasses to override specific steps without changing the algorithm's structure.

---

### Example: Vehicle Abstract Class (From Car Rental Project)

The `Vehicle` abstract class provides a template for all vehicle types, with subclasses implementing specific behavior.

#### Base Class (Template)

```java
public abstract class Vehicle {
    private String registrationNumber;
    private String model;
    private VehicleType type;
    private VehicleStatus status;
    private double baseRentalPrice;

    public Vehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.baseRentalPrice = baseRentalPrice;
    }

    // Common methods shared by all vehicles
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    // Template method - subclasses must implement
    public abstract double calculateRentalFee(int days);
}
```

#### Concrete Implementations

```java
public class EconomyVehicle extends Vehicle {
    public EconomyVehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registrationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * 1.0;
    }
}

public class LuxuryVehicle extends Vehicle {
    public LuxuryVehicle(String registrationNumber, String model, VehicleType type, double baseRentalPrice) {
        super(registrationNumber, model, type, baseRentalPrice);
    }

    @Override
    public double calculateRentalFee(int days) {
        return getBaseRentalPrice() * days * 1.5;
    }
}
```

> The `Vehicle` abstract class defines the common structure and behavior for all vehicles, while allowing each vehicle type to customize its rental fee calculation. This ensures consistency while providing flexibility.

---

## Design Patterns Summary

| Pattern             | Type       | Purpose                                  | Key Benefit                                           |
| ------------------- | ---------- | ---------------------------------------- | ----------------------------------------------------- |
| **Singleton**       | Creational | Ensures single system instance           | Controlled access to shared resource                  |
| **Strategy**        | Behavioral | Encapsulates interchangeable algorithms  | Runtime algorithm selection without conditionals      |
| **Factory**         | Creational | Abstracts object creation                | Decouples client from concrete classes                |
| **Template Method** | Behavioral | Defines algorithm skeleton in base class | Code reuse and consistent structure across subclasses |

---

## Design Patterns in This Project

| Pattern             | Implementation                       | Purpose                                     |
| ------------------- | ------------------------------------ | ------------------------------------------- |
| **Singleton**       | `RentalSystem`                       | Single rental system instance               |
| **Strategy**        | `PaymentStrategy`, `PricingStrategy` | Flexible payment methods and pricing models |
| **Factory**         | `VehicleFactory`, `PaymentFactory`   | Centralized object creation                 |
| **Template Method** | `Vehicle` (abstract class)           | Common structure for all vehicles           |

---

## Why These Patterns?

### Singleton Pattern for Rental System

The rental system should be unique throughout the application:

-   **Single source of truth**: All components reference the same rental system
-   **State consistency**: Prevents multiple system instances with different states
-   **Resource management**: Ensures efficient memory usage and centralized management

### Strategy Pattern for Payments

Different payment methods (Credit Card, Cash, PayPal) have different processing logic:

-   **Flexible payment methods**: Switch between payment methods easily
-   **Extensibility**: Add new payment methods (Crypto, Mobile Wallet) without changing existing code
-   **Testability**: Mock payment strategies for testing

### Strategy Pattern for Pricing

Different pricing models (Daily, Hourly) calculate rental costs differently:

-   **Flexible pricing**: Switch between pricing models at runtime
-   **Easy to extend**: Add new pricing models (Weekly, Monthly, Seasonal) without modifying reservation logic
-   **Separation of concerns**: Pricing logic is isolated from reservation management

### Factory Pattern for Vehicles

Creating vehicles involves selecting the right class based on vehicle type:

-   **Centralized creation**: All vehicle creation logic in one place
-   **Simplified client code**: Client code doesn't need to know vehicle constructors
-   **Easy maintenance**: Change vehicle creation logic in one location

### Factory Pattern for Payments

Creating payment strategies based on user selection:

-   **Centralized creation**: All payment strategy creation logic in one place
-   **Type safety**: Uses enum instead of integers for better type safety
-   **Easy to extend**: Add new payment methods by updating the factory

### Template Method Pattern for Vehicles

All vehicles share common structure but differ in specific details:

-   **Code reuse**: Common behavior defined once in base class
-   **Consistency**: All vehicles follow the same interface
-   **Flexibility**: Subclasses customize only what's needed (rental fee calculation)

---

## Benefits of This Design

1.  **Maintainability**: Each pattern isolates concerns, making code easier to understand and modify
2.  **Extensibility**: New vehicle types, payment methods, or pricing models can be added without changing existing code
3.  **Testability**: Strategies and factories can be easily mocked for unit testing
4.  **Separation of Concerns**: Payment logic, pricing logic, and vehicle creation are cleanly separated
5.  **Flexibility**: Runtime selection of algorithms (strategies) allows for dynamic behavior
6.  **Type Safety**: Using enums for vehicle types and payment types provides compile-time type checking
