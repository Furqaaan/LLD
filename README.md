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
