package logging_system.handler;

import logging_system.enums.LogLevel;
import logging_system.message.LogMessage;
import logging_system.appender.LogAppender;

public abstract class LogHandler {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;

    protected int level;
    protected LogHandler nextLogger;
    protected LogAppender appender;

    public LogHandler(int level, LogAppender appender) {
        this.level = level;
        this.appender = appender;
    }

    public void setNextLogger(LogHandler nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level >= level) {
            LogLevel logLevel = intToLogLevel(level);
            LogMessage logMsg = new LogMessage(logLevel, message);
            // Use the appender to log
            if (appender != null)
                appender.append(logMsg);
            write(message);
        } else if (nextLogger != null)
            nextLogger.logMessage(level, message);
    }

    private LogLevel intToLogLevel(int level) {
        switch (level) {
            case INFO:
                return LogLevel.INFO;
            case DEBUG:
                return LogLevel.DEBUG;
            case ERROR:
                return LogLevel.ERROR;
            default:
                return LogLevel.INFO;
        }
    }

    abstract protected void write(String message);
}
