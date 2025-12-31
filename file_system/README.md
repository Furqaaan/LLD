# File System - Design Patterns

This document explains the design patterns used in the File System implementation.

---

## Command Design Pattern

### What is the Command Design Pattern?

The **Command Pattern** encapsulates a request as an object, allowing you to parameterize clients with different requests, queue or log requests, and support undoable operations.

---

### Key Concepts of the Command Pattern

-   **Command Interface**: Declares an interface for executing operations.
-   **Concrete Commands**: Implement the command interface and bind to receiver operations.
-   **Receiver**: The object that performs the actual work.
-   **Invoker**: Calls the command's execute method.

---

### When to Use the Command Design Pattern

-   When you want to parameterize objects with operations.
-   When you need to queue operations, schedule them, or execute them remotely.
-   When you need to support undo/redo functionality.
-   When you want to decouple the object that invokes the operation from the one that performs it.

---

## Example: File System Commands

In the File System, each operation (create, write, read, delete, display) is encapsulated as a command object.

#### Step 1: Define the Command Interface

```java
public interface FileSystemCommand {
    void execute();
}
```

#### Step 2: Implement Concrete Commands

**Create Command**

```java
public class CreateCommand implements FileSystemCommand {
    private FileSystem receiver;
    private String path;

    public CreateCommand(FileSystem receiver, String path) {
        this.receiver = receiver;
        this.path = path;
    }

    @Override
    public void execute() {
        receiver.create(path);
    }
}
```

Similar commands exist for: `WriteCommand`, `ReadCommand`, `DeleteCommand`, `DisplayCommand`, and `ExitCommand`.

#### Step 3: Invoker (CommandInvoker)

```java
public class CommandInvoker {
    private List<FileSystemCommand> commandHistory;

    public void executeCommand(FileSystemCommand command) {
        if (command != null) {
            command.execute();
            commandHistory.add(command);
        }
    }
}
```

> The Command Pattern decouples the invoker from the receiver, making it easy to add new commands, queue operations, or implement undo/redo functionality.

---

## Factory Design Pattern

### What is the Factory Design Pattern?

The **Factory Pattern** provides an interface for creating objects without specifying their exact classes. It centralizes object creation logic.

---

### Key Concepts of the Factory Pattern

-   **Factory Class**: Contains the logic to create appropriate objects.
-   **Product**: The objects being created (commands in this case).

---

### When to Use the Factory Design Pattern

-   When you want to centralize object creation logic.
-   When the creation process is complex or involves conditionals.
-   When you want to decouple object creation from usage.

---

## Example: Command Factory

The `CommandFactory` creates the appropriate command object based on user input.

#### Implementation

```java
public class CommandFactory {
    private FileSystem receiver;

    public FileSystemCommand createCommand(String commandName, String[] parts) {
        switch (commandName.toLowerCase()) {
            case "create":
                return new CreateCommand(receiver, parts[1]);
            case "write":
                return new WriteCommand(receiver, parts[1], parts[2]);
            case "read":
                return new ReadCommand(receiver, parts[1]);
            // ... other commands
        }
    }
}
```

> The Factory Pattern centralizes command creation logic, making it easy to add new commands or modify existing ones without changing client code.

---

## Composite Design Pattern

### What is the Composite Design Pattern?

The **Composite Pattern** composes objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions uniformly.

---

### Key Concepts of the Composite Pattern

-   **Component**: Abstract class/interface that defines common operations for both leaf and composite objects.
-   **Leaf**: Represents individual objects (cannot have children).
-   **Composite**: Represents objects that can have children.

---

### When to Use the Composite Design Pattern

-   When you want to represent part-whole hierarchies.
-   When you want clients to treat individual objects and compositions uniformly.
-   When you need to build tree structures of objects.

---

## Example: File System Tree Structure

In the File System, both files and directories are treated uniformly through the `Node` abstract class, allowing recursive operations on the file system tree.

#### Step 1: Define the Component (Abstract Class)

```java
public abstract class Node {
    private String name;
    private Map<String, Node> children;

    public abstract boolean isFile();
    public abstract void display(int depth);

    public void addChild(String name, Node child) {
        children.put(name, child);
    }
}
```

#### Step 2: Implement Leaf (File)

```java
public class File extends Node {
    private String content;

    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth * 2);
        System.out.println(indent + "📄 " + getName());
    }
}
```

#### Step 3: Implement Composite (Directory)

```java
public class Directory extends Node {
    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth * 2);
        System.out.println(indent + "📁 " + getName());
        for (Node child : getChildren()) {
            child.display(depth + 1);  // Recursive call
        }
    }
}
```

> The Composite Pattern allows the file system to treat files and directories uniformly, enabling recursive operations like displaying the entire tree structure without distinguishing between individual files and directory containers.

---

## Design Patterns Summary

| Pattern       | Type       | Purpose                               | Key Benefit                                           |
| ------------- | ---------- | ------------------------------------- | ----------------------------------------------------- |
| **Command**   | Behavioral | Encapsulates requests as objects      | Decouples invoker from receiver                       |
| **Factory**   | Creational | Abstracts object creation             | Centralizes command creation logic                    |
| **Composite** | Structural | Composes objects into tree structures | Uniform treatment of individual and composite objects |

---

## Design Patterns in This Project

| Pattern       | Implementation                        | Purpose                             |
| ------------- | ------------------------------------- | ----------------------------------- |
| **Command**   | `FileSystemCommand`, `CommandInvoker` | Encapsulates file system operations |
| **Factory**   | `CommandFactory`                      | Centralized command creation        |
| **Composite** | `Node`, `Directory`, `File`           | Tree structure for file system      |

---

## Why These Patterns?

### Command Pattern for File Operations

File system operations need to be:

-   **Queueable**: Commands can be stored and executed later
-   **Loggable**: Command history enables audit trails
-   **Extensible**: New operations can be added easily

### Factory Pattern for Command Creation

Command creation involves:

-   **Centralized logic**: All command creation in one place
-   **Simplified client code**: Main class doesn't need to know command constructors
-   **Easy maintenance**: Add new commands by updating the factory

### Composite Pattern for File System Structure

The file system tree structure benefits from:

-   **Uniform interface**: Files and directories treated the same way
-   **Recursive operations**: Display, search, and traversal work on any node
-   **Natural hierarchy**: Mirrors real-world file system structure

---

## Benefits of This Design

1. **Maintainability**: Patterns isolate concerns, making code easier to understand
2. **Extensibility**: New commands and file types can be added without modifying existing code
3. **Testability**: Commands and nodes can be easily mocked for unit testing
4. **Separation of Concerns**: Command logic is separated from invocation logic
5. **History Support**: Command history enables undo/redo functionality
6. **Uniform Interface**: Composite pattern allows treating files and directories uniformly
