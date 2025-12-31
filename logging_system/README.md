# Logging System - Design Patterns

This document explains the design patterns used in the Logging System implementation.

---

## Chain of Responsibility Design Pattern

The **Chain of Responsibility Pattern** passes requests along a chain of handlers. Each handler processes the request or passes it to the next handler.

### Example: Log Handler Chain

Log messages are processed through a chain (InfoLogger → DebugLogger → ErrorLogger).

```java
LogHandler errorLogger = new ErrorLogger(LogHandler.ERROR, appender);
LogHandler debugLogger = new DebugLogger(LogHandler.DEBUG, appender);
LogHandler infoLogger = new InfoLogger(LogHandler.INFO, appender);

infoLogger.setNextLogger(debugLogger);
debugLogger.setNextLogger(errorLogger);
```

> The Chain of Responsibility pattern allows log messages to be processed by multiple handlers in sequence.

---

## Singleton Design Pattern

The **Singleton Pattern** ensures a class has only one instance and provides a global point of access.

### Example: Logger Singleton

The `Logger` class provides a single logging instance per configuration.

```java
Logger logger = Logger.getInstance(LogLevel.INFO, consoleAppender);
logger.info("Message");
```

> The Singleton pattern ensures consistent logging configuration.

---

## Design Patterns Summary

| Pattern                     | Type       | Purpose                       | Key Benefit                                      |
| --------------------------- | ---------- | ----------------------------- | ------------------------------------------------ |
| **Chain of Responsibility** | Behavioral | Passes requests along a chain | Flexible request processing without conditionals |
| **Singleton**               | Creational | Ensures single instance       | Controlled access to shared resource             |

---

## Design Patterns in This Project

| Pattern                     | Implementation                                           | Purpose                   |
| --------------------------- | -------------------------------------------------------- | ------------------------- |
| **Chain of Responsibility** | `LogHandler`, `InfoLogger`, `DebugLogger`, `ErrorLogger` | Sequential log processing |
| **Singleton**               | `Logger`                                                 | Single logger instance    |
