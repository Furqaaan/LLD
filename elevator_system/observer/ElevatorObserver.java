package elevator_system.observer;

import elevator_system.elevator.Elevator;
import elevator_system.enums.State;

public interface ElevatorObserver {
    void onElevatorStateChange(Elevator elevator, State state);
    void onElevatorFloorChange(Elevator elevator, int floor);
}