## State Design Pattern

### What is the State Design Pattern?

The **State Pattern** allows an object to change its behavior based on its current state. Instead of using large `if-else` or `switch-case` blocks to handle different states, the behavior is encapsulated into state classes. The context delegates behavior to the current state object.

---

### Key Concepts of the State Pattern

-   **State Interface**: Defines the common behavior for all states.
-   **Concrete States**: Implement the behavior for each state.
-   **Context**: Maintains a reference to the current state and delegates requests to the current state object.

---

### When to Use the State Design Pattern

-   When an object’s behavior depends on its state.
-   When you have a lot of `if-else` or `switch` statements that change behavior based on state.
-   When the state-specific behavior changes frequently or needs to be easily extendable.

---

## Example 1: Traffic Light System

Let’s take a traffic light system as an example. A traffic light can be in one of three states:

-   **Red Light**: Cars must stop.
-   **Green Light**: Cars can go.
-   **Yellow Light**: Cars should prepare to stop.

#### Step 1: Define the State Interface

```java
interface TrafficLightState {
    void handleRequest(TrafficLightContext context);
}
```

#### Step 2: Implement Concrete States

**Red Light State**

```java
class RedLightState implements TrafficLightState {
    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Red Light: Cars must stop.");
        context.setState(new GreenLightState());  // Change to Green after Red
    }
}
```

**Green Light State**

```java
class GreenLightState implements TrafficLightState {
    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Green Light: Cars can go.");
        context.setState(new YellowLightState());  // Change to Yellow after Green
    }
}
```

_Add other states as needed, e.g._:

```java
class YellowLightState implements TrafficLightState {
    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Yellow Light: Cars should prepare to stop.");
        context.setState(new RedLightState()); // Change to Red after Yellow
    }
}
```

#### Step 3: Create the Context Class

```java
class TrafficLightContext {
    private TrafficLightState currentState;

    public TrafficLightContext() {
        currentState = new RedLightState();  // Default initial state
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
    }

    public void changeLight() {
        currentState.handleRequest(this);
    }
}
```

#### Step 4: Client Code

```java
public class StatePatternDemo {
    public static void main(String[] args) {
        TrafficLightContext trafficLight = new TrafficLightContext();

        for (int i = 0; i < 6; i++) {  // Change the light multiple times
            trafficLight.changeLight();
            System.out.println();
        }
    }
}
```

**Output:**

```
Red Light: Cars must stop.

Green Light: Cars can go.

Yellow Light: Cars should prepare to stop.

Red Light: Cars must stop.

Green Light: Cars can go.

Yellow Light: Cars should prepare to stop.
```

> In this example, the `TrafficLightContext` transitions through different states (Red, Green, Yellow). The behavior in each state is encapsulated, ensuring state transitions and behaviors are easy to maintain and extend.

---

## Example 2: Order State in an E-Commerce System

In an e-commerce system, an order can go through several states: Placed, Shipped, Delivered, Cancelled. Each state has specific actions that can be performed.

#### Step 1: Define the State Interface

```java
interface OrderState {
    void next(OrderContext context);
    void cancel(OrderContext context);
}
```

#### Step 2: Implement Concrete States

**Order Placed State**

```java
class OrderPlacedState implements OrderState {
    @Override
    public void next(OrderContext context) {
        System.out.println("Order has been placed. Moving to Shipped state.");
        context.setState(new OrderShippedState());
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Order has been cancelled.");
        context.setState(new OrderCancelledState());
    }
}
```

**Order Shipped State**

```java
class OrderShippedState implements OrderState {
    @Override
    public void next(OrderContext context) {
        System.out.println("Order has been shipped. Moving to Delivered state.");
        context.setState(new OrderDeliveredState());
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel. Order has already been shipped.");
    }
}
```

**Order Delivered State**

```java
class OrderDeliveredState implements OrderState {
    @Override
    public void next(OrderContext context) {
        System.out.println("Order is already delivered.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Cannot cancel. Order is already delivered.");
    }
}
```

**Order Cancelled State**

```java
class OrderCancelledState implements OrderState {
    @Override
    public void next(OrderContext context) {
        System.out.println("Cannot proceed. Order is cancelled.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Order is already cancelled.");
    }
}
```

#### Step 3: Create the Context Class

```java
class OrderContext {
    private OrderState currentState;

    public OrderContext() {
        currentState = new OrderPlacedState();  // Default state
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public void proceedToNext() {
        currentState.next(this);
    }

    public void cancelOrder() {
        currentState.cancel(this);
    }
}
```

#### Step 4: Client Code

```java
public class ECommerceStatePatternDemo {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();

        System.out.println("Order Workflow:");
        order.proceedToNext();  // Move to Shipped
        order.proceedToNext();  // Move to Delivered
        order.cancelOrder();    // Try to cancel after delivery

        System.out.println("\nNew Order Workflow:");
        OrderContext newOrder = new OrderContext();
        newOrder.cancelOrder();  // Cancel immediately after placement
    }
}
```

**Output:**

```
Order Workflow:
Order has been placed. Moving to Shipped state.
Order has been shipped. Moving to Delivered state.
Cannot cancel. Order is already delivered.

New Order Workflow:
Order has been cancelled.
```

> In this example, the `OrderContext` transitions through different states (Placed, Shipped, Delivered, Cancelled). The behavior in each state is encapsulated, ensuring that state transitions and behaviors are easy to maintain and extend.

Note:
The context class initializes with a state and provides methods to transition between states. Always interact with state changes and transitions through the context's methods.

---

## Factory Design Pattern

### What is the Factory Design Pattern?

The **Factory Pattern** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. Instead of calling a constructor directly, you call a factory method which returns an instance of the object.

---

### Key Concepts of the Factory Pattern

-   **Factory Interface**: Defines the method(s) for creating objects.
-   **Concrete Factory**: Implements the factory interface to create specific objects.
-   **Product**: The object being created by the factory.

---

### When to Use the Factory Design Pattern

-   When a class doesn't know ahead of time what class of objects it needs to create.
-   When you want to delegate object creation to subclasses.
-   When you need to centralize complex object creation logic.
-   When you want to decouple the code that uses objects from the code that creates them.

---

## Example 1: Vehicle Factory

Let's create a vehicle factory that can produce different types of vehicles.

#### Step 1: Define the Product Interface

```java
interface Vehicle {
    void drive();
}
```

#### Step 2: Implement Concrete Products

**Car**

```java
class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a car on the road.");
    }
}
```

**Motorcycle**

```java
class Motorcycle implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Riding a motorcycle on the road.");
    }
}
```

#### Step 3: Define the Factory Interface

```java
interface VehicleFactory {
    Vehicle createVehicle();
}
```

#### Step 4: Implement Concrete Factories

**Car Factory**

```java
class CarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}
```

**Motorcycle Factory**

```java
class MotorcycleFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Motorcycle();
    }
}
```

#### Step 5: Client Code

```java
public class FactoryPatternDemo {
    public static void main(String[] args) {
        VehicleFactory carFactory = new CarFactory();
        Vehicle car = carFactory.createVehicle();
        car.drive();

        VehicleFactory motorcycleFactory = new MotorcycleFactory();
        Vehicle motorcycle = motorcycleFactory.createVehicle();
        motorcycle.drive();
    }
}
```

**Output:**

```
Driving a car on the road.
Riding a motorcycle on the road.
```

> In this example, the `VehicleFactory` interface abstracts the creation of vehicles. The client code doesn't need to know which specific class is instantiated—it just works with the factory.

---

## Example 2: Player Factory (From Tic-Tac-Toe Project)

In the Tic-Tac-Toe game, a `PlayerFactory` is used to create players with different strategies.

#### Step 1: Define the Factory Interface

```java
public interface PlayerFactory {
    Player createPlayer(Symbol symbol, PlayerStrategy strategy);
}
```

#### Step 2: Implement the Concrete Factory

```java
public class SimplePlayerFactory implements PlayerFactory {
    @Override
    public Player createPlayer(Symbol symbol, PlayerStrategy strategy) {
        return new Player(symbol, strategy);
    }
}
```

#### Step 3: Client Code (TicTacToeGame)

```java
public class TicTacToeGame {
    public TicTacToeGame(int boardSize, PlayerStrategy strategyX,
            PlayerStrategy strategyO, PlayerFactory playerFactory) {
        this.playerX = playerFactory.createPlayer(Symbol.X, strategyX);
        this.playerO = playerFactory.createPlayer(Symbol.O, strategyO);
        // ...
    }
}
```

> The factory pattern allows easy substitution of player creation logic. You could create an `AIPlayerFactory` or `NetworkPlayerFactory` without modifying the game controller.

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

## Example 1: Payment Strategy

Let's create a payment system that supports multiple payment methods.

#### Step 1: Define the Strategy Interface

```java
interface PaymentStrategy {
    void pay(double amount);
}
```

#### Step 2: Implement Concrete Strategies

**Credit Card Payment**

```java
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card: " + cardNumber);
    }
}
```

**PayPal Payment**

```java
class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal: " + email);
    }
}
```

**Crypto Payment**

```java
class CryptoPayment implements PaymentStrategy {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Crypto Wallet: " + walletAddress);
    }
}
```

#### Step 3: Create the Context Class

```java
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}
```

#### Step 4: Client Code

```java
public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(100.00);

        cart.setPaymentStrategy(new PayPalPayment("user@email.com"));
        cart.checkout(50.00);

        cart.setPaymentStrategy(new CryptoPayment("0xABC123..."));
        cart.checkout(200.00);
    }
}
```

**Output:**

```
Paid $100.0 using Credit Card: 1234-5678-9012-3456
Paid $50.0 using PayPal: user@email.com
Paid $200.0 using Crypto Wallet: 0xABC123...
```

> The `ShoppingCart` doesn't know or care how the payment is processed. It delegates the responsibility to the strategy object, making it easy to add new payment methods.

---

## Example 2: Player Strategy (From Tic-Tac-Toe Project)

In the Tic-Tac-Toe game, `PlayerStrategy` defines how a player makes moves.

#### Step 1: Define the Strategy Interface

```java
public interface PlayerStrategy {
    Position makeMove(Board board);
}
```

#### Step 2: Implement Concrete Strategies

**Human Player Strategy**

```java
public class HumanPlayerStrategy implements PlayerStrategy {
    String playerName;
    Scanner scanner;

    public HumanPlayerStrategy(String playerName) {
        this.playerName = playerName;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Position makeMove(Board board) {
        while (true) {
            System.out.printf("%s, enter your move (row [0-2] and column [0-2]): ", playerName);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            Position move = new Position(row, col);

            if (board.isValidMove(move)) {
                return move;
            }
            System.out.println("Invalid move. Try again.");
        }
    }
}
```

**AI Player Strategy (Example Extension)**

```java
public class AIPlayerStrategy implements PlayerStrategy {
    @Override
    public Position makeMove(Board board) {
        // AI logic: find the best move using minimax or random selection
        return findBestMove(board);
    }

    private Position findBestMove(Board board) {
        // Implementation of AI move selection
        return new Position(0, 0); // Simplified example
    }
}
```

#### Step 3: Context (Player Class)

```java
public class Player {
    Symbol symbol;
    PlayerStrategy playerStrategy;

    public Player(Symbol symbol, PlayerStrategy playerStrategy) {
        this.symbol = symbol;
        this.playerStrategy = playerStrategy;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }
}
```

> This design allows you to create human players, AI players, or even network players by simply providing different strategy implementations.

---

## Observer Design Pattern

### What is the Observer Design Pattern?

The **Observer Pattern** is a behavioral design pattern that defines a one-to-many dependency between objects. When one object (the subject) changes state, all its dependents (observers) are notified and updated automatically.

---

### Key Concepts of the Observer Pattern

-   **Subject**: The object being observed. It maintains a list of observers and notifies them of state changes.
-   **Observer Interface**: Defines the update method that observers must implement.
-   **Concrete Observers**: Implement the observer interface to react to state changes.

---

### When to Use the Observer Design Pattern

-   When changes to one object require changing others, and you don't know how many objects need to change.
-   When an object should notify other objects without knowing who those objects are.
-   When you want to implement distributed event handling systems.
-   When you need a publish-subscribe relationship between objects.

---

## Example 1: Weather Station

Let's create a weather station that notifies multiple displays when weather data changes.

#### Step 1: Define the Observer Interface

```java
interface WeatherObserver {
    void update(float temperature, float humidity, float pressure);
}
```

#### Step 2: Define the Subject Interface

```java
interface WeatherSubject {
    void registerObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers();
}
```

#### Step 3: Implement the Subject

```java
class WeatherStation implements WeatherSubject {
    private List<WeatherObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherStation() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
```

#### Step 4: Implement Concrete Observers

**Current Conditions Display**

```java
class CurrentConditionsDisplay implements WeatherObserver {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("Current Conditions: " + temperature + "°C and " + humidity + "% humidity");
    }
}
```

**Statistics Display**

```java
class StatisticsDisplay implements WeatherObserver {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0;
    private int numReadings = 0;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;
        maxTemp = Math.max(maxTemp, temperature);
        minTemp = Math.min(minTemp, temperature);

        System.out.println("Avg/Max/Min Temperature: " + (tempSum / numReadings)
            + "/" + maxTemp + "/" + minTemp);
    }
}
```

#### Step 5: Client Code

```java
public class ObserverPatternDemo {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statsDisplay = new StatisticsDisplay();

        weatherStation.registerObserver(currentDisplay);
        weatherStation.registerObserver(statsDisplay);

        weatherStation.setMeasurements(25.0f, 65.0f, 1013.0f);
        System.out.println();
        weatherStation.setMeasurements(28.0f, 70.0f, 1012.0f);
    }
}
```

**Output:**

```
Current Conditions: 25.0°C and 65.0% humidity
Avg/Max/Min Temperature: 25.0/25.0/25.0

Current Conditions: 28.0°C and 70.0% humidity
Avg/Max/Min Temperature: 26.5/28.0/25.0
```

> The weather station doesn't need to know the details of each display. It simply notifies all registered observers, and each observer handles the update in its own way.

---

## Example 2: Game Event Listener (From Tic-Tac-Toe Project)

In the Tic-Tac-Toe game, a `GameEventListener` observes game events like moves and state changes.

#### Step 1: Define the Observer Interface

```java
public interface GameEventListener {
    void onMoveMade(Position position, Symbol symbol);
    void onGameStateChanged(GameState state);
}
```

#### Step 2: Implement Concrete Observer

```java
public class ConsoleGameEventListener implements GameEventListener {
    @Override
    public void onMoveMade(Position position, Symbol symbol) {
        System.out.println("Move made at position: " + position + " by " + symbol);
    }

    @Override
    public void onGameStateChanged(GameState state) {
        System.out.println("Game state changed to: " + state);
    }
}
```

#### Usage (Example Integration)

```java
public class TicTacToeGame {
    private List<GameEventListener> listeners = new ArrayList<>();

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    private void notifyMoveMade(Position position, Symbol symbol) {
        for (GameEventListener listener : listeners) {
            listener.onMoveMade(position, symbol);
        }
    }

    private void notifyGameStateChanged(GameState state) {
        for (GameEventListener listener : listeners) {
            listener.onGameStateChanged(state);
        }
    }
}
```

> This pattern enables features like logging, UI updates, network synchronization, or analytics without modifying the core game logic. Just add new listeners!

---

## Design Patterns Summary

| Pattern      | Type       | Purpose                                         | Key Benefit                                                  |
| ------------ | ---------- | ----------------------------------------------- | ------------------------------------------------------------ |
| **State**    | Behavioral | Object changes behavior based on internal state | Eliminates complex conditionals for state-dependent behavior |
| **Factory**  | Creational | Abstracts object creation                       | Decouples client from concrete classes                       |
| **Strategy** | Behavioral | Encapsulates interchangeable algorithms         | Runtime algorithm selection without conditionals             |
| **Observer** | Behavioral | One-to-many dependency notification             | Loose coupling between subject and observers                 |
