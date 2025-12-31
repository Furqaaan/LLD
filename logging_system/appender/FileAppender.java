package logging_system.appender;

import logging_system.message.LogMessage;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender {
    private final String filePath;

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(LogMessage logMessage) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(logMessage.toString() + "n"); // Write log to file
        } catch (IOException e) {
            e.printStackTrace(); // Print error if file writing fails
        }
    }
}