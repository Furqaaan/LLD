package elevator_system.scheduling;

import elevator_system.elevator.Elevator;
import elevator_system.enums.Direction;
import java.util.Queue;
import elevator_system.command.ElevatorRequest;

public class FCFSSchedulingStrategy implements SchedulingStrategy {
    @Override
    public int getNextStop(Elevator elevator) {
        Direction elevatorDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        Queue<ElevatorRequest> requestQueue = elevator.getRequestsQueueForScheduling();

        if (requestQueue.isEmpty())
            return currentFloor;

        int nextRequestedFloor = requestQueue.poll().getFloor();

        if (nextRequestedFloor == currentFloor)
            return currentFloor;

        if (elevatorDirection == Direction.IDLE) {
            elevator.setDirection(
                    nextRequestedFloor > currentFloor ? Direction.UP : Direction.DOWN);
        } else if (elevatorDirection == Direction.UP
                && nextRequestedFloor < currentFloor) {
            elevator.setDirection(Direction.DOWN);
        } else if (nextRequestedFloor > currentFloor) {
            elevator.setDirection(Direction.UP);
        }

        return nextRequestedFloor;
    }
}
