# Command Design Pattern — Explained

---

## 1. What is the Command Pattern?

- **Definition:** Encapsulate a request as an object. This lets you parameterize clients with different requests, queue or log requests, and support undoable operations.
- **In short:**  
  > "Make method calls into objects."

---

## 2. Problems Addressed

- **Without Command Pattern:**
  - Caller directly calls business logic
  - Large `if-else` or `switch` blocks
  - Strong coupling between caller and receiver
  - Difficult to add features like undo, redo, queueing, or scheduling

---

## 3. Core Elements

### 3.1 Command Interface

```java
interface Command {
    void execute();
}
```
Defines a method for executing a command.

---

### 3.2 Concrete Command

```java
class LightOnCommand implements Command {
    private Light light;
    LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
}
```
- Implements the Command interface.
- Holds reference to the *Receiver* (e.g., `Light`).
- Calls receiver logic inside `execute()`.

---

### 3.3 Receiver

```java
class Light {
    void turnOn() { System.out.println("Light is ON"); }
}
```
Contains the real business logic.

---

### 3.4 Invoker

```java
class RemoteControl {
    private Command command;
    void setCommand(Command command) { this.command = command; }
    void pressButton() { command.execute(); }
}
```
- Initiates the command, but doesn't know details of receiver/business logic.

---

### 3.5 Client

```java
Light light = new Light();
Command command = new LightOnCommand(light);
remote.setCommand(command);
remote.pressButton();
```
- Creates commands and links everything.

---

## 4. Flow Diagram

**Client** → **Invoker** → **Command** → **Receiver**  
WHO → WHAT → HOW

---

## 5. Without Command Pattern Example (Problems)

```java
class RemoteControl {
    void pressButton(String action) {
        if ("LIGHT_ON".equals(action)) new Light().turnOn();
        else if ("FAN_ON".equals(action)) new Fan().turnOn();
    }
}
```
- Tight coupling  
- Not extendable  
- No undo/redo or scheduling  
- Violates SOLID principles

---

## 6. With Command Pattern Example (Solution)

```java
remote.setCommand(new LightOnCommand(new Light()));
remote.pressButton();
```
- No conditionals
- Clean, extensible, decoupled

---

## 7. Command Pattern + Queues

**Without Command Pattern:**
```java
Queue<String> tasks;
```
Must use conditionals to run logic.

**With Command Pattern:**
```java
Queue<Command> tasks;
tasks.poll().execute();
```
- Easy to queue, log or retry actions

---

## 8. Command Pattern + Scheduling

- Commands can be stored, scheduled, or executed later:
```java
scheduler.schedule(command, delay);
```
- Scheduler's only job is to call `execute()` on command objects.

---

## 9. Who Is the Invoker?

The "invoker" is whoever calls `execute()` — can be a button, remote, scheduler, worker thread, history/undo manager, etc.

---

## 10. Undo/Redo with Command Pattern

```java
interface Command {
    void execute();
    void undo();
}

command.execute();
command.undo();
```
Commands can reverse their effect by implementing `undo()`.

---

## 11. When to Use Command Pattern

### ✅ Use for:
- Actions triggered via UI (buttons, menus)
- Undo/redo support
- Queuing, scheduling
- Clean, decoupled code

### ❌ Not needed if:
- Logic is trivial
- There are only 1–2 simple actions
- No plan for growth/extensibility

---

## 12. Real Example Usages

- Remote controls
- UI toolbars (Save, Undo, Redo)
- Message/Job queues (background jobs)
- Cron/scheduler jobs
- Elevator requests
- Parking gates

---

## 13. One-Line Summary

> "Command Pattern wraps a request as an object, decoupling sender and receiver, enabling undo/redo, queuing, and scheduling without conditionals."

---

## 14. Mental Model

**Invoker → Command → Receiver**  
WHO       →   WHAT   →  HOW

## Strategy Pattern vs. Command Pattern: Removing if-else and Key Differences

You might have heard: *“Use Strategy Pattern to eliminate if-else statements.”*  
✅ Yes, you can!  
But **Strategy** and **Command** address different problems, even though both help remove big conditional code blocks.

Let’s clarify the distinction.

---

### Can the Strategy Pattern Replace if-else?
**Yes.**  
A classic use-case of Strategy Pattern is to replace conditional logic that chooses between different behaviors or algorithms at runtime.

#### Example

**Without Strategy (with if-else):**
```java
class PaymentService {
    void pay(String method, int amount) {
        if (method.equals("CARD")) {
            payByCard(amount);
        } else if (method.equals("UPI")) {
            payByUpi(amount);
        }
    }
}
```

**With Strategy Pattern (no if-else):**
```java
// Strategy Interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete Strategies
class CardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid by Card");
    }
}

class UpiPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid by UPI");
    }
}

// Context
class PaymentService {
    private PaymentStrategy strategy;

    void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    void pay(int amount) {
        strategy.pay(amount);
    }
}
```
*Result: No more `if-else`; behavior swapped via composition!*

---

### Why Not Always Use Strategy Instead of Command?

While both patterns encapsulate behavior, their **intent and use-cases differ**.

| Aspect          | Strategy Pattern                   | Command Pattern                    |
|-----------------|-----------------------------------|-------------------------------------|
| **Purpose**     | Select *how* to do something      | Represent *what* to do             |
| **Focus**       | Algorithm/behavior                | Action/request                     |
| **Execution**   | Immediate                         | Can be immediate *or* delayed      |
| **Undo/Redo**   | Difficult                         | Natural (by design)                |
| **Queue/Schedule** | Not designed for this          | Natural, built-in                  |
| **Receiver**    | Usually none                      | Always present                     |
| **Caller**      | Context                           | Invoker                            |

#### When is Strategy the Right Choice?

Use Strategy Pattern when you:
- Need multiple interchangeable ways to perform the same operation
- Want to select/switch behaviors dynamically
- Don’t need to support undo, logging, queuing, or scheduling

**Examples:**  
- Sorting algorithms  
- Calculation rules (discounts, taxes)  
- Compression algorithms  
- Payment method selection

---

#### Why Command Pattern Excels for Actions/Requests

Use Command Pattern when you:
- Want to represent distinct actions/requests as objects
- Need undo/redo (history of operations)
- Need to queue, schedule, or log requests
- Want macro commands (composite actions)

**Examples:**  
- UI buttons or menu operations (Undo, Redo, Save, etc.)  
- Background jobs / message queues  
- Remote controls  
- Workflow steps (pipelines)

---

### Conceptual Difference

- **Strategy answers:**  
  > *“Which way should I do this?”*  
  (e.g., “Pay via UPI or Card?”)

- **Command answers:**  
  > *“Which action should I perform?”*  
  (e.g., “Execute payment order #123”)

---

### Takeaway

> **“Strategy Pattern removes if-else by encapsulating interchangeable algorithms (how),  
while Command Pattern removes if-else by encapsulating requests as objects (what)—enabling undo, logging, queuing, and scheduling.”**

**Quick Mnemonic:**  
Strategy → **HOW** to do it  
Command  → **WHAT** to do
