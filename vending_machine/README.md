# Vending Machine - Design Patterns

This document explains the design patterns used in the Vending Machine system implementation.

---

## State Design Pattern

### What is the State Design Pattern?

The **State Pattern** is a behavioral design pattern that allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

---

### Key Concepts of the State Pattern

-   **State Interface**: Defines the interface for state-specific behavior.
-   **Concrete States**: Implement state-specific behavior.
-   **Context**: Maintains a reference to the current state and delegates state-specific behavior to the current state object.

---

### When to Use the State Design Pattern

-   When an object's behavior depends on its state and it must change its behavior at runtime depending on that state.
-   When you have many conditional statements that depend on the object's state.
-   When you want to avoid large if-else or switch statements based on state.
-   When state transitions are well-defined and the number of states is manageable.

---

## Example: Vending Machine State (From Vending Machine Project)

In the Vending Machine system, the `VendingMachineContext` uses the State pattern to manage different states of the vending machine (Idle, HasMoney, Selection, Dispense, OutOfStock).

#### Step 1: Define the State Interface

```java
public interface VendingMachineState {
    String getStateName();
    VendingMachineState next(VendingMachineContext context);
}
```

#### Step 2: Implement Concrete States

**Idle State**

```java
public class IdleState implements VendingMachineState {
    @Override
    public String getStateName() {
        return "IdleState";
    }

    @Override
    public VendingMachineState next(VendingMachineContext context) {
        if (!context.getInventory().hasItems()) {
            return new OutOfStockState();
        }
        if (!context.getCoinList().isEmpty()) {
            return new HasMoneyState();
        }
        return this;
    }
}
```

**HasMoney State**

```java
public class HasMoneyState implements VendingMachineState {
    @Override
    public String getStateName() {
        return "HasMoneyState";
    }

    @Override
    public VendingMachineState next(VendingMachineContext context) {
        return new SelectionState();
    }
}
```

**Selection State**

```java
public class SelectionState implements VendingMachineState {
    @Override
    public String getStateName() {
        return "SelectionState";
    }

    @Override
    public VendingMachineState next(VendingMachineContext context) {
        return new DispenseState();
    }
}
```

**Dispense State**

```java
public class DispenseState implements VendingMachineState {
    @Override
    public String getStateName() {
        return "DispenseState";
    }

    @Override
    public VendingMachineState next(VendingMachineContext context) {
        context.resetBalance();
        context.resetSelection();
        return new IdleState();
    }
}
```

#### Step 3: Context (VendingMachineContext Class)

```java
public class VendingMachineContext {
    private VendingMachineState currentState;

    public VendingMachineContext() {
        currentState = new IdleState();
    }

    public void advanceState() {
        VendingMachineState nextState = currentState.next(this);
        currentState = nextState;
        System.out.println("Current state: " + currentState.getStateName());
    }

    public void clickOnInsertCoinButton(Coin coin) {
        if (currentState instanceof IdleState || currentState instanceof HasMoneyState) {
            coinList.add(coin);
            advanceState();
        } else {
            System.out.println("Cannot insert coin in " + currentState.getStateName());
        }
    }

    public void clickOnStartProductSelectionButton(int codeNumber) {
        if (currentState instanceof HasMoneyState) {
            advanceState();
            selectProduct(codeNumber);
        } else {
            System.out.println("Product selection button can only be clicked in HasMoney state");
        }
    }
}
```

#### Step 4: Client Usage

```java
VendingMachineContext vendingMachine = new VendingMachineContext();

// Insert coin - transitions from Idle to HasMoney
vendingMachine.clickOnInsertCoinButton(Coin.TEN_RUPEES);

// Select product - transitions from HasMoney to Selection to Dispense
vendingMachine.clickOnStartProductSelectionButton(102);
```

> The State pattern ensures that the vending machine behaves correctly in each state and prevents invalid operations (e.g., selecting a product without inserting money). State transitions are managed cleanly without complex conditional logic.

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

## Example: Payment Strategy (From Vending Machine Project)

In the Vending Machine system, `PaymentStrategy` defines how payments are processed. Different strategies can handle different payment methods (Coin, Card).

#### Step 1: Define the Strategy Interface

```java
public interface PaymentStrategy {
    boolean processPayment(double amount);
}
```

#### Step 2: Implement Concrete Strategies

**Coin Payment Strategy**

```java
public class CoinPaymentStrategy implements PaymentStrategy {
    private List<Coin> coins;

    public CoinPaymentStrategy(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public boolean processPayment(double amount) {
        int total = 0;
        for (Coin coin : coins) {
            total += coin.value;
        }
        return total >= amount;
    }
}
```

**Card Payment Strategy**

```java
public class CardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CardPaymentStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing card payment of $" + amount);
        // Card validation logic here
        return true;
    }
}
```

#### Step 3: Context (VendingMachineContext Class)

```java
public class VendingMachineContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment strategy set.");
            return false;
        }
        return paymentStrategy.processPayment(amount);
    }

    public void clickOnCardPaymentButton(String cardNumber, String expiryDate, String cvv) {
        setPaymentStrategy(new CardPaymentStrategy(cardNumber, expiryDate, cvv));
        advanceState();
    }

    public void dispenseItem(int codeNumber, double amount) {
        setPaymentStrategy(new CoinPaymentStrategy(coinList));
        processPayment(amount);
        inventory.removeItem(codeNumber);
        advanceState();
    }
}
```

#### Step 4: Client Usage

```java
// Coin payment
vendingMachine.clickOnInsertCoinButton(Coin.TEN_RUPEES);
vendingMachine.clickOnStartProductSelectionButton(102);

// Card payment
vendingMachine.clickOnCardPaymentButton("1234-5678-9012-3456", "12/25", "123");
vendingMachine.clickOnStartProductSelectionButton(102);
```

> The `VendingMachineContext` class doesn't know or care how the payment is processed. It delegates the responsibility to the strategy object, making it easy to add new payment methods (Mobile Wallet, NFC) without modifying existing code.

---

## Design Patterns Summary

| Pattern      | Type       | Purpose                                 | Key Benefit                                          |
| ------------ | ---------- | --------------------------------------- | ---------------------------------------------------- |
| **State**    | Behavioral | Manages object behavior based on state  | Clean state transitions without complex conditionals |
| **Strategy** | Behavioral | Encapsulates interchangeable algorithms | Runtime algorithm selection without conditionals     |

---

## Design Patterns in This Project

| Pattern      | Implementation                                                                                            | Purpose                                   |
| ------------ | --------------------------------------------------------------------------------------------------------- | ----------------------------------------- |
| **State**    | `VendingMachineState`, `IdleState`, `HasMoneyState`, `SelectionState`, `DispenseState`, `OutOfStockState` | Manages vending machine state transitions |
| **Strategy** | `PaymentStrategy`, `CoinPaymentStrategy`, `CardPaymentStrategy`                                           | Flexible payment method processing        |

---

## Why These Patterns?

### State Pattern for Vending Machine

The vending machine has distinct states with different allowed operations:

-   **Idle State**: Machine is ready, waiting for user input
-   **HasMoney State**: Money has been inserted, user can select product
-   **Selection State**: Product is being selected
-   **Dispense State**: Product is being dispensed
-   **OutOfStock State**: Machine has no items available

**Benefits:**

-   **Clear state transitions**: Each state knows its next state
-   **Prevents invalid operations**: Operations are only allowed in appropriate states
-   **Easy to extend**: Add new states (Maintenance, Refilling) without modifying existing code
-   **No complex conditionals**: State-specific behavior is encapsulated in state classes

### Strategy Pattern for Payments

Different payment methods (Coin, Card) have different processing logic:

-   **Flexible payment methods**: Switch between payment methods easily
-   **Extensibility**: Add new payment methods (Mobile Wallet, NFC, QR Code) without changing existing code
-   **Testability**: Mock payment strategies for testing
-   **Separation of concerns**: Payment logic is isolated from vending machine logic

---

## Benefits of This Design

1. **Maintainability**: Each pattern isolates concerns, making code easier to understand and modify
2. **Extensibility**: New states or payment methods can be added without changing existing code
3. **Testability**: States and strategies can be easily mocked for unit testing
4. **Separation of Concerns**: State management and payment processing are cleanly separated
5. **Flexibility**: Runtime selection of payment methods allows for dynamic behavior
6. **Type Safety**: Using interfaces for states and strategies provides compile-time type checking
7. **Prevents Invalid Operations**: State pattern ensures operations are only performed in valid states

---

## State vs Strategy Pattern

While both patterns are behavioral, they serve different purposes:

-   **State Pattern**: Manages how an object behaves based on its internal state. The object's behavior changes as its state changes.
-   **Strategy Pattern**: Manages how an algorithm is executed. The algorithm can be swapped at runtime, but the object's state doesn't change based on the strategy.

In this vending machine:

-   **State Pattern** manages the machine's operational state (Idle → HasMoney → Selection → Dispense)
-   **Strategy Pattern** manages how payment is processed (Coin vs Card), which is independent of the machine's state
