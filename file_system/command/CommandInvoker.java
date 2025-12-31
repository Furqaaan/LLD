package file_system.command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private List<FileSystemCommand> commandHistory;

    public CommandInvoker() {
        this.commandHistory = new ArrayList<>();
    }

    public void executeCommand(FileSystemCommand command) {
        if (command != null) {
            command.execute();
            // Don't add exit command to history
            if (!(command instanceof ExitCommand)) {
                commandHistory.add(command);
            }
        }
    }

    public List<FileSystemCommand> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    public void clearHistory() {
        commandHistory.clear();
    }
}

