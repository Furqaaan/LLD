package elevator_system.elevator;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import elevator_system.building.Floor;
import elevator_system.scheduling.SchedulingStrategy;
import elevator_system.scheduling.FCFSSchedulingStrategy;
import elevator_system.command.ElevatorRequest;
import elevator_system.enums.Direction;

public class ElevatorController {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private SchedulingStrategy schedulingStrategy;

    public ElevatorController(int numberOfElevators, int numberOfFloors, SchedulingStrategy strategy) {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.schedulingStrategy = strategy != null ? strategy : new FCFSSchedulingStrategy();
        
        // Initialize elevators (all start at floor 0)
        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new Elevator(i + 1, 0));
        }
        
        // Initialize floors
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i));
        }
    }

    public void setSchedulingStrategy(SchedulingStrategy strategy) {
        this.schedulingStrategy = strategy;
    }

    public void requestElevator(int floor, Direction direction) {
        // Find the best elevator for this request
        Elevator bestElevator = findBestElevator(floor, direction);
        
        if (bestElevator != null) {
            ElevatorRequest request = new ElevatorRequest(
                bestElevator.getId(), 
                floor, 
                false, 
                direction,
                this
            );
            bestElevator.addRequest(request);
            processElevatorRequests(bestElevator);
        }
    }

    public void requestFloor(int elevatorId, int floor) {
        Elevator elevator = findElevatorById(elevatorId);
        if (elevator != null) {
            ElevatorRequest request = new ElevatorRequest(
                elevatorId, 
                floor, 
                true, 
                elevator.getDirection(),
                this
            );
            elevator.addRequest(request);
            processElevatorRequests(elevator);
        }
    }

    private Elevator findBestElevator(int floor, Direction direction) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            // Skip elevators in maintenance
            if (elevator.getState() == elevator_system.enums.State.MAINTENANCE) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            
            // Prefer elevators that are idle or moving in the same direction
            if (elevator.getDirection() == Direction.IDLE || 
                (elevator.getDirection() == direction && 
                 ((direction == Direction.UP && elevator.getCurrentFloor() < floor) ||
                  (direction == Direction.DOWN && elevator.getCurrentFloor() > floor)))) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        // If no ideal elevator found, use the closest one
        if (bestElevator == null) {
            for (Elevator elevator : elevators) {
                if (elevator.getState() != elevator_system.enums.State.MAINTENANCE) {
                    int distance = Math.abs(elevator.getCurrentFloor() - floor);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestElevator = elevator;
                    }
                }
            }
        }

        return bestElevator;
    }

    private Elevator findElevatorById(int id) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == id) {
                return elevator;
            }
        }
        return null;
    }

    public void processElevatorRequests(Elevator elevator) {
        if (!elevator.hasRequests()) {
            return;
        }

        int nextStop = schedulingStrategy.getNextStop(elevator);
        elevator.moveToFloor(nextStop);
        
        // Remove requests for the floor we just stopped at
        Queue<ElevatorRequest> requests = elevator.getRequestsQueueForScheduling();
        requests.removeIf(req -> req.getFloor() == nextStop);

        // Process next request if any
        if (elevator.hasRequests()) {
            processElevatorRequests(elevator);
        }
    }

    public List<Elevator> getElevators() {
        return new ArrayList<>(elevators);
    }

    public List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }
}
