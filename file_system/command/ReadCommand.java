package file_system.command;

import file_system.manager.FileSystem;

public class ReadCommand implements FileSystemCommand {
    private FileSystem receiver;
    private String path;

    public ReadCommand(FileSystem receiver, String path) {
        this.receiver = receiver;
        this.path = path;
    }

    @Override
    public void execute() {
        String content = receiver.getFileContent(path);
        if (content != null) {
            System.out.println("Content: " + content);
        } else {
            System.out.println("Failed to read content");
        }
    }
}

