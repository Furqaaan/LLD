package logging_system.appender;

import logging_system.message.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);
}
