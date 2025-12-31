package logging_system.enums;

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    ERROR(3);

    private final int value;
    LogLevel(int value) { 
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isGreaterOrEqual(LogLevel other) {
        return this.value >= other.value;
    }
}
