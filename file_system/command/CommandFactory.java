package file_system.command;

import file_system.manager.FileSystem;

public class CommandFactory {
    private FileSystem receiver;

    public CommandFactory(FileSystem receiver) {
        this.receiver = receiver;
    }

    public FileSystemCommand createCommand(String commandName, String[] parts) {
        if (commandName == null || parts == null) {
            return null;
        }

        switch (commandName.toLowerCase()) {
            case "create":
                if (parts.length >= 2) {
                    return new CreateCommand(receiver, parts[1]);
                }
                System.out.println("Usage: create <path>");
                return null;

            case "write":
                if (parts.length >= 3) {
                    return new WriteCommand(receiver, parts[1], parts[2]);
                }
                System.out.println("Usage: write <path> <content>");
                return null;

            case "read":
                if (parts.length >= 2) {
                    return new ReadCommand(receiver, parts[1]);
                }
                System.out.println("Usage: read <path>");
                return null;

            case "delete":
                if (parts.length >= 2) {
                    return new DeleteCommand(receiver, parts[1]);
                }
                System.out.println("Usage: delete <path>");
                return null;

            case "display":
                return new DisplayCommand(receiver);

            case "exit":
                return new ExitCommand();

            default:
                return null;
        }
    }
}

