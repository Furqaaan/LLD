package file_system.command;

import file_system.manager.FileSystem;

public class DeleteCommand implements FileSystemCommand {
    private FileSystem receiver;
    private String path;

    public DeleteCommand(FileSystem receiver, String path) {
        this.receiver = receiver;
        this.path = path;
    }

    @Override
    public void execute() {
        boolean isDeleted = receiver.deletePath(path);
        System.out.println(isDeleted ? "Path deleted successfully" : "Failed to delete path");
    }
}

