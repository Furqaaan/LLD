# Parking Lot - Design Notes

## Interface vs Abstract Class

### When to use Interface

> "I want to define **what you can do**, not what you are."

### When to use Abstract Class

> "I want to define **what you are**, and share common state and logic."

---

## Why Vehicle Should NOT Be an Interface

### ❌ Interfaces Are Poor at Holding State

Even though Java allows fields in interfaces:

```java
interface Vehicle {
    String licensePlate = ""; // ❌ public static final
}
```

**Problems:**

-   You cannot have per-object state
-   You cannot mutate fields

But in a parking lot:

-   Each vehicle has its own license plate
-   Each vehicle has its own type

👉 **This immediately disqualifies interface.**

---

## When SHOULD You Use an Interface?

Use an interface when defining a **capability**:

```java
public interface Parkable {
    int getRequiredSpots();
}

public class Car extends Vehicle implements Parkable {
    public int getRequiredSpots() {
        return 1;
    }
}
```

Here:

-   `Parkable` = capability
-   `Vehicle` = identity

💡 _A class **is a** Vehicle, but it **can be** Parkable._

---

## Why Abstract Class Instead of Concrete Class?

Both can be extended, so why use `abstract`?

### 1️⃣ Should Vehicle Be Instantiable?

Ask yourself:

```java
Vehicle v = new Vehicle("KA01AB1234");
```

❓ Does this make sense in real life?

👉 **No.**

-   You never park a generic vehicle
-   You park a `Car`, `Bike`, `Truck`, etc.

### 2️⃣ Abstract Class = "Incomplete Concept"

An abstract class means:

> "This class is a base template. It is not complete on its own."

`Vehicle` is:

-   A generalization
-   Missing concrete behavior
-   Not a real-world object by itself

So marking it `abstract` prevents wrong usage:

```java
Vehicle v = new Vehicle(); // ❌ compile-time error
```

✔ Design safety  
✔ Domain correctness

### 3️⃣ Abstract Class Communicates Intent

Marking it `abstract` tells every developer:

> ⚠️ "Do not create Vehicle directly. Always create a specific type of vehicle."

This is **self-documenting design**.

---

## The Golden Decision Rule 🧠

### The Core Question

> Should methods be defined in an interface or in an abstract class?

Ask two questions:

| Question                                                                                         | Answer                |
| ------------------------------------------------------------------------------------------------ | --------------------- |
| 1️⃣ Is this behavior **intrinsic** to the entity? ("Every Vehicle must do this")                  | ➡️ **Abstract class** |
| 2️⃣ Is this behavior a **capability** that may vary or be optional? ("Some Vehicles can do this") | ➡️ **Interface**      |

---

## Examples in Parking Lot 🚗🅿️

### Example 1: Parking Space Requirement

```java
public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;

    public abstract int getRequiredParkingSpots();
}
```

**Why abstract class?**

-   Every vehicle must occupy space
-   Behavior is core to being a `Vehicle`
-   Subclasses must implement it

✔ Correct choice

---

### Example 2: Electric Charging Capability 🔌

```java
public interface ElectricVehicle {
    int getChargingRate();
}

public class ElectricCar extends Vehicle implements ElectricVehicle {
    public int getChargingRate() {
        return 50;
    }
}
```

**Why interface?**

-   Not all vehicles are electric
-   It's an optional capability
-   Multiple inheritance is needed

✔ Interface fits better

---

## Summary

> Core behaviors that define **what an entity is** should live in an **abstract class**, while optional or cross-cutting capabilities should be modeled as **interfaces**.
>
> Abstract classes help enforce domain rules and shared state, whereas interfaces provide flexibility and allow multiple inheritance of behavior.

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

## Example 1: Parking Fee Strategy (From Parking Lot Project)

In the Parking Lot system, `ParkingFeeStrategy` defines how parking fees are calculated. Different strategies can apply different pricing models.

#### Step 1: Define the Strategy Interface

```java
public interface ParkingFeeStrategy {
    double calculateFee(VehicleType vehicleType, int duration, DurationType durationType);
}
```

#### Step 2: Implement Concrete Strategies

**Hourly Rate Strategy**

```java
public class HourlyRateStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        double multiplier = 1;

        if (durationType == DurationType.DAY) {
            multiplier = 24;
        }

        double price = duration * multiplier;

        switch (vehicleType) {
            case CAR:
                return price * 10.0;
            case MOTORCYCLE:
                return price * 5.0;
            case TRUCK:
                return price * 20.0;
            case OTHER:
                return price * 5.0;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
    }
}
```

**Premium Rate Strategy**

```java
public class PremiumRateStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        double premiumRate = 10.0;
        double multiplier = 1;

        if (durationType == DurationType.DAY) {
            multiplier = 24;
        }

        double price = duration * multiplier * premiumRate;

        switch (vehicleType) {
            case CAR:
                return price * 10.0;
            case MOTORCYCLE:
                return price * 5.0;
            case TRUCK:
                return price * 20.0;
            case OTHER:
                return price * 5.0;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
    }
}
```

#### Step 3: Context (Vehicle Class)

```java
public abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private ParkingFeeStrategy feeStrategy;

    public Vehicle(String licensePlate, VehicleType type, ParkingFeeStrategy feeStrategy) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.feeStrategy = feeStrategy;
    }

    public double calculateFee(int duration, DurationType durationType) {
        return feeStrategy.calculateFee(type, duration, durationType);
    }
}
```

> The `Vehicle` class doesn't know or care how the fee is calculated. It delegates the responsibility to the strategy object, making it easy to add new pricing models like weekend rates, membership discounts, or surge pricing.

---

## Example 2: Payment Strategy (From Parking Lot Project)

The payment system uses the Strategy Pattern to support multiple payment methods.

#### Step 1: Define the Strategy Interface

```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

#### Step 2: Implement Concrete Strategies

**Card Payment**

```java
public class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Card");
    }
}
```

**Cash Payment**

```java
public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Cash");
    }
}
```

#### Step 3: Client Usage

```java
PaymentStrategy payment = new CardPayment();
payment.pay(50.00);  // Paid $50.0 using Card

payment = new CashPayment();
payment.pay(30.00);  // Paid $30.0 using Cash
```

> This design allows adding new payment methods (PayPal, Crypto, Mobile Wallet) without modifying existing code—just create a new strategy class.

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

## Example 1: Vehicle Factory (From Parking Lot Project)

The `VehicleFactory` creates different types of vehicles based on the vehicle type.

#### Step 1: Define the Product (Abstract Class)

```java
public abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private ParkingFeeStrategy feeStrategy;

    public Vehicle(String licensePlate, VehicleType type, ParkingFeeStrategy feeStrategy) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.feeStrategy = feeStrategy;
    }

    // ... getters and methods
}
```

#### Step 2: Implement Concrete Products

```java
public class CarVehicle extends Vehicle {
    public CarVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.CAR, feeStrategy);
    }
}

public class BikeVehicle extends Vehicle {
    public BikeVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.MOTORCYCLE, feeStrategy);
    }
}

public class TruckVehicle extends Vehicle {
    public TruckVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.TRUCK, feeStrategy);
    }
}
```

#### Step 3: Implement the Factory

```java
public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type, String licensePlate, ParkingFeeStrategy feeStrategy) {
        switch (type) {
            case CAR:
                return new CarVehicle(licensePlate, feeStrategy);
            case MOTORCYCLE:
                return new BikeVehicle(licensePlate, feeStrategy);
            case TRUCK:
                return new TruckVehicle(licensePlate, feeStrategy);
            case OTHER:
                return new OtherVehicle(licensePlate, feeStrategy);
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}
```

#### Step 4: Client Code

```java
ParkingFeeStrategy feeStrategy = new HourlyRateStrategy();

Vehicle car = VehicleFactory.createVehicle(VehicleType.CAR, "KA01AB1234", feeStrategy);
Vehicle bike = VehicleFactory.createVehicle(VehicleType.MOTORCYCLE, "KA02CD5678", feeStrategy);
Vehicle truck = VehicleFactory.createVehicle(VehicleType.TRUCK, "KA03EF9012", feeStrategy);
```

> The client code doesn't need to know which specific vehicle class is instantiated. It delegates creation to the factory, making it easy to add new vehicle types.

---

## Example 2: Payment Strategy Factory (From Parking Lot Project)

The `PaymentStrategyFactory` creates the appropriate payment strategy based on user selection.

```java
public class PaymentStrategyFactory {
    public static PaymentStrategy createPaymentStrategy(int paymentMethod) {
        switch (paymentMethod) {
            case 1:
                return new CardPayment();
            case 2:
                return new CashPayment();
            default:
                throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
    }
}
```

#### Usage

```java
// User selects payment method 1 (Card)
PaymentStrategy payment = PaymentStrategyFactory.createPaymentStrategy(1);
payment.pay(100.00);  // Paid $100.0 using Card

// User selects payment method 2 (Cash)
payment = PaymentStrategyFactory.createPaymentStrategy(2);
payment.pay(50.00);   // Paid $50.0 using Cash
```

> This factory pattern centralizes object creation logic and makes it easy to extend with new payment methods.

---

## Builder Design Pattern

### What is the Builder Design Pattern?

The **Builder Pattern** is a creational design pattern that lets you construct complex objects step by step. It allows you to produce different types and representations of an object using the same construction code.

---

### Key Concepts of the Builder Pattern

-   **Builder**: Provides methods to build different parts of the product.
-   **Product**: The complex object being built.
-   **Director (Optional)**: Orchestrates the building process using the builder.
-   **Fluent Interface**: Methods return `this` to enable method chaining.

---

### When to Use the Builder Design Pattern

-   When constructing complex objects with many optional parameters.
-   When object creation involves multiple steps.
-   When you want to avoid telescoping constructors (constructors with many parameters).
-   When the same construction process should create different representations.

---

## Example: Parking Lot Builder (From Parking Lot Project)

The `ParkingLotBuilder` constructs a `ParkingLot` with multiple floors, each containing different types of parking spots.

#### Step 1: Define the Builder

```java
public class ParkingLotBuilder {
    private List<ParkingFloor> floors;

    public ParkingLotBuilder() {
        this.floors = new ArrayList<>();
    }

    public ParkingLotBuilder addFloor(ParkingFloor floor) {
        floors.add(floor);
        return this;
    }

    public ParkingLotBuilder createFloor(int floorNumber, int numOfCarSpots,
            int numOfBikeSpots, int... otherSpotCounts) {
        ParkingFloor floor = new ParkingFloor(floorNumber);

        for (int i = 0; i < numOfCarSpots; i++) {
            floor.addParkingSpot(new CarParkingSpot(i + 1));
        }

        for (int i = 0; i < numOfBikeSpots; i++) {
            floor.addParkingSpot(new BikeParkingSpot(numOfCarSpots + i + 1));
        }

        int spotOffset = numOfCarSpots + numOfBikeSpots;
        for (int k = 0; k < otherSpotCounts.length; k++) {
            floor.addParkingSpot(new OtherParkingSpot(spotOffset + k + 1));
        }

        floors.add(floor);
        return this;
    }

    public ParkingLot build() {
        return new ParkingLot(floors);
    }
}
```

#### Step 2: Client Code (Using the Builder)

```java
ParkingLot parkingLot = new ParkingLotBuilder()
    .createFloor(1, 50, 30, 5)    // Floor 1: 50 car spots, 30 bike spots, 5 other spots
    .createFloor(2, 40, 20, 3)    // Floor 2: 40 car spots, 20 bike spots, 3 other spots
    .createFloor(3, 30, 25, 2)    // Floor 3: 30 car spots, 25 bike spots, 2 other spots
    .build();
```

**Benefits of the Builder Pattern here:**

-   **Fluent API**: Method chaining makes the code readable
-   **Step-by-step construction**: Each floor is added incrementally
-   **Flexible configuration**: Easy to create different parking lot layouts
-   **Encapsulated complexity**: The builder hides the details of creating floors and spots

> The Builder Pattern is ideal for the Parking Lot because constructing a lot involves multiple floors, each with varying numbers of different spot types. Without the builder, you'd need complex constructors or multiple setter calls.

---

## Design Patterns Summary

| Pattern      | Type       | Purpose                                 | Key Benefit                                      |
| ------------ | ---------- | --------------------------------------- | ------------------------------------------------ |
| **Strategy** | Behavioral | Encapsulates interchangeable algorithms | Runtime algorithm selection without conditionals |
| **Factory**  | Creational | Abstracts object creation               | Decouples client from concrete classes           |
| **Builder**  | Creational | Constructs complex objects step by step | Readable construction of objects with many parts |

---

## Design Patterns in This Project

| Pattern      | Implementation                                 | Purpose                               |
| ------------ | ---------------------------------------------- | ------------------------------------- |
| **Strategy** | `ParkingFeeStrategy`, `PaymentStrategy`        | Flexible fee calculation and payments |
| **Factory**  | `VehicleFactory`, `PaymentStrategyFactory`     | Centralized object creation           |
| **Builder**  | `ParkingLotBuilder`                            | Step-by-step parking lot construction |
| **Template** | `Vehicle` (abstract), `ParkingSpot` (abstract) | Common structure with specialization  |

When do we use the State Pattern?

State Pattern is appropriate when:

The behavior of an object changes based on its internal state,
and that behavior is implemented inside the same object.

The State pattern is used when a single object’s behavior changes based on its internal state. This is why it fits Tic Tac Toe perfectly: the Game object behaves differently depending on the state (X’s turn, O’s turn, Game Over). For example, the same method makeMove() is valid during XTurnState or OTurnState, but invalid in GameOverState. The rules and allowed actions change based on the game’s state, and modeling this with State avoids large if/else blocks and keeps behavior cleanly encapsulated.

In a Parking Lot, however, what you see is a workflow, not state-driven behavior in a single object. Steps like vehicle arrived → spot allocated → parked → payment → exit are handled by different entities (ParkingLot, ParkingSpot, Payment, Exit). Since behavior doesn’t change inside one dominant object, using State at the ParkingLot level would be forced and over-engineered. A good place where State does fit in Parking Lot is the ParkingTicket, e.g., ACTIVE → PAID → CLOSED, where methods like pay() or exit() are allowed or disallowed based on the ticket’s state.