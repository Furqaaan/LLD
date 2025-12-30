package elevator_system.command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private List<ElevatorCommand> commandHistory;

    public CommandInvoker() {
        this.commandHistory = new ArrayList<>();
    }

    public void executeCommand(ElevatorCommand command) {
        command.execute();
        commandHistory.add(command);
    }

    public List<ElevatorCommand> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    public void clearHistory() {
        commandHistory.clear();
    }
}
