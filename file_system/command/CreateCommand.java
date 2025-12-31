package file_system.command;

import file_system.manager.FileSystem;

public class CreateCommand implements FileSystemCommand {
    private FileSystem receiver;
    private String path;

    public CreateCommand(FileSystem receiver, String path) {
        this.receiver = receiver;
        this.path = path;
    }

    @Override
    public void execute() {
        boolean isCreated = receiver.createPath(path);
        System.out.println(isCreated ? "Path created successfully" : "Failed to create path");
    }
}

