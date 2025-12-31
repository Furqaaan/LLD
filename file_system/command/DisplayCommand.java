package file_system.command;

import file_system.manager.FileSystem;

public class DisplayCommand implements FileSystemCommand {
    private FileSystem receiver;

    public DisplayCommand(FileSystem receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("File System Structure:");
        receiver.display();
    }
}

