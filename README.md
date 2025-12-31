## When to Use Each Pattern (At a Glance)

### 🟢 State Pattern
**Use when:**  
- The same object changes its behavior as its internal state changes.
- There’s a clear life-cycle (phases, statuses, modes).
- You’re tempted to write more and more `if (state == ...)` code.

**Examples:**  
- Elevator: Idle → Moving → DoorOpen  
- Order process: Created → Paid → Shipped → Delivered  
- Traffic light: Red → Yellow → Green

**Mental shortcut:**  
*"I’m always the same thing, but I behave differently depending on my current state."*

---

### 🔵 Strategy Pattern
**Use when:**  
- You have several interchangeable algorithms/ways to do the same task.
- You want to switch behavior dynamically at runtime.
- You want to refactor big `if-else` or `switch` blocks that choose logic.

**Examples:**  
- Payment: Card / UPI / Wallet  
- Discount: Festival / Coupon / Loyalty  
- Sorting: QuickSort / MergeSort

**Mental shortcut:**  
*"I’m doing one job, but I can choose HOW to do it."*

---

### 🟣 Command Pattern
**Use when:**  
- You want to treat an action itself as an object.
- You need to queue, schedule, or undo actions.
- You want to decouple the sender from the logic (the invoker doesn’t know HOW the action happens).

**Examples:**  
- Button click  
- Background job  
- Undo / Redo  
- API request as a job

**Mental shortcut:**  
*"I want to wrap up an action and run it now or later — the caller just says 'do it'."*

---

## Quick Comparison Table

| Pattern    | Ask Yourself...              | Key Mental Question           |
|------------|------------------------------|------------------------------|
| State      | "What state am I in?"        | Am I changing behavior with state? |
| Strategy   | "Which way should I do it?"  | Am I choosing how to do one thing? |
| Command    | "What action should I perform?" | Should actions be objects?        |

---

## Mini Real-World Example: Food Order App 🍔

- **State:** Order status: `Placed`, `Cooking`, `Delivered`
- **Strategy:** Payment method: `UPI`, `Card`
- **Command:** Actions like `PlaceOrderCommand`, `CancelOrderCommand`

---

## Blazing-Fast Interview Summary 🎯

- **State:** Use when behavior changes as object state changes.
- **Strategy:** Use when you pick an algorithm at runtime.
- **Command:** Use when you need to treat requests/actions as objects (undo, queue, etc).


## Factory Pattern — The Essentials

### 1. What is the Factory Pattern?

The Factory Pattern is a **creational** design pattern that aims to:

- **Centralize and encapsulate the logic of object creation.**
- Instead of writing `new` in your main code, you let a dedicated factory class handle object creation.

---

### 2. The Core Principle

- **Clients don’t know (or care) about the exact classes being instantiated.**
- They interact only with an abstract type (interface/abstract class), not with specific implementations.

---

### 3. Is an Abstract Class Required in a Factory?

- ❌ Not strictly required.
- ✅ **Very common.**

Factories just need to return a common type, such as:
- **Abstract class** (if objects share fields/logic — most common)
- **Interface** (if only behavior matters)
- **Concrete class** (rarely)

---

### 4. Why Pair Abstract Classes with Factories?

Use an abstract class in your hierarchy when:
- Objects share fields or behaviors.
- You want to enforce a shared contract.
- You don’t want direct instantiation of the base type.
- Subclasses add special fields/logic.

**Example:**
```java
abstract class Product {}
class ElectronicsProduct extends Product {}
class ClothingProduct extends Product {}
```
*(This is classic inheritance, not the pattern itself!)*

---

### 5. Why Use a Factory? What Problem Does It Solve?

**Without a Factory (bad):**
```java
if (category == ELECTRONICS) {
    new ElectronicsProduct();
}
```
Problems:
- Tight coupling
- Harder to extend
- Violates open/closed principle

**With a Factory (good):**
```java
Product p = productFactory.createProduct(category);
```
Benefits:
- Creation logic hidden from client
- Client uses only the abstraction
- Easy extensibility and cleaner code

---

### 6. Factory vs. Abstract Class — Key Difference

| Concept        | Responsibility                |
|---------------|------------------------------|
| Abstract class| Defines what is common        |
| Subclass      | Defines the specifics         |
| Factory       | Decides *which* object to make|

👉 **Use them together for optimal OO design.**

---

### 7. The Typical Pattern in Practice

- **Abstract base** (`Product`)
- **Concrete subclasses** (`ElectronicsProduct`, etc.)
- **Factory** (`ProductFactory`)

**Result:** Clean and robust design, favored in interviews.

---

### 8. When Should You Use the Factory Pattern?

Use Factory Pattern if:
- ✅ Object creation depends on category/type
- ✅ You want to hide `new` from users
- ✅ You seek loose coupling
- ✅ You expect to add more product types
- ✅ You want a crisp, simple API

**Perfect for:**
- Product objects
- Payments
- Notifications
- Vehicles
- Accounts

---

### 9. When NOT to Use a Factory

Don’t bother if:
- ❌ There’s only one concrete class
- ❌ Object creation never varies
- ❌ Just calling `new` is enough

(Overusing factories leads to needless complexity.)

---

### 10. Factory vs. Strategy vs. Command (At a Glance)

| Pattern   | The Big Question                    |
|-----------|-------------------------------------|
| Factory   | “Which object should I create?”      |
| Strategy  | “Which algorithm/how should I do it?”|
| Command   | “What action should I perform?”      |

---

### 11. Interview Gotcha! ❌

> “Factory Pattern means you must have an abstract class.”

**Wrong.**  
Usually factories return an abstract class or interface, but it isn’t a rule.

---

### 12. One-Line Interview Definition 🎯

>The Factory Pattern encapsulates object creation logic and hands out objects through a common abstraction, keeping clients decoupled from details and concrete implementations.

---

### 13. Mental Model Cheat Sheet 🧠

- **Factory**: WHO creates
- **Abstract class**: WHAT is common
- **Subclass**: WHAT is different
- 


## Singleton Pattern: Overview & Java Example

The **Singleton Pattern** ensures a class has only one instance, providing a single, global point of access. Use it when just one object needs to coordinate actions or maintain shared state—like a configuration manager, logger, cache, or inventory controller.

> **Note:** Just naming a class `Manager` doesn’t make it a Singleton. Only use Singleton when there should be exactly one instance. Otherwise, avoid it; global state can introduce tight coupling and hinder testing.

#### Java Singleton – Typical Approach

A standard Java Singleton uses:
- A private static variable to hold the single instance
- A private constructor to block external instantiation
- A public static method (`getInstance()`) to provide controlled access

```java
public class InventoryManager {
    private static InventoryManager instance;

    private InventoryManager() {
        // Prevent instantiation from outside
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void manageInventory() {
        System.out.println("Managing inventory...");
    }
}
```

**Usage:**
```java
InventoryManager manager = InventoryManager.getInstance();
manager.manageInventory();
```

This pattern makes sure only one `InventoryManager` object exists and all callers share that instance.

> **Pro Tip:** Singletons are easy in Java with `getInstance()`, but don’t overuse! Dependency injection frameworks can provide single instances without static access; this can help testing and flexibility when scaling.