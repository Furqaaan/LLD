package file_system.command;

public class ExitCommand implements FileSystemCommand {
    @Override
    public void execute() {
        System.out.println("Exiting...");
    }
}

