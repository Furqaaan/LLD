package elevator_system.scheduling;

import elevator_system.elevator.Elevator;

public interface SchedulingStrategy {
    int getNextStop(Elevator elevator);
}
