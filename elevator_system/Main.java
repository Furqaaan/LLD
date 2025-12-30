package elevator_system;

import elevator_system.building.Building;
import elevator_system.command.CommandInvoker;
import elevator_system.command.ElevatorCommand;
import elevator_system.command.ElevatorRequest;
import elevator_system.enums.Direction;
import elevator_system.observer.ElevatorDisplay;
import elevator_system.scheduling.FCFSSchedulingStrategy;
import elevator_system.scheduling.LookSchedulingStrategy;
import elevator_system.scheduling.ScanSchedulingStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Elevator System Demo ===\n");

        // Create a building with 10 floors and 2 elevators using FCFS scheduling
        Building building = new Building("Tech Tower", 10, 2, new FCFSSchedulingStrategy());

        // Add observers to elevators
        ElevatorDisplay display = new ElevatorDisplay();
        building.getElevatorController().getElevators().forEach(elevator -> elevator.addObserver(display));

        // Create command invoker for command pattern
        CommandInvoker invoker = new CommandInvoker();

        System.out.println("Building: " + building.getName());
        System.out.println("Floors: " + building.getNumberOfFloors());
        System.out.println("Elevators: " + building.getElevatorController().getElevators().size());
        System.out.println("\n--- Starting Elevator Requests ---\n");

        // External request: Person on floor 3 wants to go UP
        System.out.println("Request 1: External request from floor 3 going UP");
        ElevatorCommand request1 = new ElevatorRequest(
                0, // elevatorId not needed for external requests
                3,
                false,
                Direction.UP,
                building.getElevatorController());
        invoker.executeCommand(request1);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        // Internal request: Person inside elevator 1 wants to go to floor 7
        System.out.println("\nRequest 2: Internal request - Elevator 1 to floor 7");
        ElevatorCommand request2 = new ElevatorRequest(
                1,
                7,
                true,
                Direction.UP,
                building.getElevatorController());
        invoker.executeCommand(request2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        // External request: Person on floor 5 wants to go DOWN
        System.out.println("\nRequest 3: External request from floor 5 going DOWN");
        ElevatorCommand request3 = new ElevatorRequest(
                0,
                5,
                false,
                Direction.DOWN,
                building.getElevatorController());
        invoker.executeCommand(request3);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        // Internal request: Person inside elevator wants to go to floor 1
        System.out.println("\nRequest 4: Internal request - Elevator to floor 1");
        ElevatorCommand request4 = new ElevatorRequest(
                1,
                1,
                true,
                Direction.DOWN,
                building.getElevatorController());
        invoker.executeCommand(request4);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        // Demonstrate different scheduling strategies
        System.out.println("\n--- Switching to LOOK Scheduling Strategy ---\n");
        building.getElevatorController().setSchedulingStrategy(new LookSchedulingStrategy());

        System.out.println("Request 5: External request from floor 8 going UP");
        ElevatorCommand request5 = new ElevatorRequest(
                0,
                8,
                false,
                Direction.UP,
                building.getElevatorController());
        invoker.executeCommand(request5);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- Switching to SCAN Scheduling Strategy ---\n");
        building.getElevatorController().setSchedulingStrategy(new ScanSchedulingStrategy());

        System.out.println("Request 6: External request from floor 2 going UP");
        ElevatorCommand request6 = new ElevatorRequest(
                0,
                2,
                false,
                Direction.UP,
                building.getElevatorController());
        invoker.executeCommand(request6);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }

        // Display command history
        System.out.println("\n--- Command History ---");
        System.out.println("Total commands executed: " + invoker.getCommandHistory().size());
        for (int i = 0; i < invoker.getCommandHistory().size(); i++) {
            ElevatorRequest req = (ElevatorRequest) invoker.getCommandHistory().get(i);
            System.out.println("Command " + (i + 1) + ": " +
                    (req.checkIsInternalRequest() ? "Internal" : "External") +
                    " request to floor " + req.getFloor());
        }

        System.out.println("\n=== Demo Complete ===");
    }
}
