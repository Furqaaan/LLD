package elevator_system.observer;

import elevator_system.elevator.Elevator;
import elevator_system.enums.State;

public class ElevatorDisplay implements ElevatorObserver {
    @Override
    public void onElevatorStateChange(Elevator elevator, State state) {
        System.out.println("Elevator " + elevator.getId() + " state changed to " + state);
    }

    @Override
    public void onElevatorFloorChange(Elevator elevator, int floor) {
        System.out.println("Elevator " + elevator.getId() + " moved to floor " + floor);
    }
}