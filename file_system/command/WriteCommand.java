package file_system.command;

import file_system.manager.FileSystem;

public class WriteCommand implements FileSystemCommand {
    private FileSystem receiver;
    private String path;
    private String content;

    public WriteCommand(FileSystem receiver, String path, String content) {
        this.receiver = receiver;
        this.path = path;
        this.content = content;
    }

    @Override
    public void execute() {
        boolean isWritten = receiver.setFileContent(path, content);
        System.out.println(isWritten ? "Content written successfully" : "Failed to write content");
    }
}

