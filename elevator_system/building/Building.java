package elevator_system.building;

import elevator_system.elevator.ElevatorController;
import elevator_system.scheduling.SchedulingStrategy;

public class Building {
    private String name;
    private int numberOfFloors;
    private ElevatorController elevatorController;

    public Building(String name, int numberOfFloors, int numberOfElevators, SchedulingStrategy strategy) {
        this.name = name;
        this.numberOfFloors = numberOfFloors;
        this.elevatorController = new ElevatorController(numberOfElevators, numberOfFloors, strategy);
    }

    public ElevatorController getElevatorController() {
        return elevatorController;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }
}