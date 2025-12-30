package elevator_system.command;

import elevator_system.elevator.ElevatorController;
import elevator_system.enums.Direction;

public class ElevatorRequest implements ElevatorCommand {
    private int elevatorId;
    private int floor;
    private Direction requestDirection;
    private ElevatorController controller;
    private boolean isInternalRequest;

    public ElevatorRequest(int elevatorId, int floor, boolean isInternalRequest,
            Direction direction, ElevatorController controller) {
        this.elevatorId = elevatorId;
        this.floor = floor;
        this.isInternalRequest = isInternalRequest;
        this.requestDirection = direction;
        this.controller = controller;
    }

    @Override
    public void execute() {
        if (isInternalRequest)
            controller.requestFloor(elevatorId, floor);
        else
            controller.requestElevator(floor, requestDirection);
    }

    public Direction getDirection() {
        return requestDirection;
    }

    public int getFloor() {
        return floor;
    }

    public boolean checkIsInternalRequest() {
        return isInternalRequest;
    }

    public int getElevatorId() {
        return elevatorId;
    }
}
