package file_system;

import file_system.command.CommandFactory;
import file_system.command.CommandInvoker;
import file_system.command.ExitCommand;
import file_system.command.FileSystemCommand;
import file_system.manager.FileSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        CommandFactory factory = new CommandFactory(fs);
        CommandInvoker invoker = new CommandInvoker();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("File System Manager - Commands:");
        System.out.println("1. create <path> - Create a new path");
        System.out.println("2. write <path> <content> - Write content to a file");
        System.out.println("3. read <path> - Read content from a file");
        System.out.println("4. delete <path> - Delete a path");
        System.out.println("5. display - Show the entire file system structure");
        System.out.println("6. exit - Exit the program");

        while (isRunning) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+", 3);

            if (parts.length == 0)
                continue;

            String commandName = parts[0].toLowerCase();

            try {
                FileSystemCommand command = factory.createCommand(commandName, parts);

                if (command == null) {
                    if (!commandName.equals("create") && !commandName.equals("write") &&
                            !commandName.equals("read") && !commandName.equals("delete")) {
                        System.out.println(
                                "Unknown command. Available commands: create, write, read, delete, display, exit");
                    }
                    continue;
                }

                if (command instanceof ExitCommand) {
                    invoker.executeCommand(command);
                    isRunning = false;
                } else {
                    invoker.executeCommand(command);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
