package elevator_system.elevator;

import elevator_system.enums.Direction;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import elevator_system.enums.State;
import elevator_system.observer.ElevatorObserver;
import elevator_system.command.ElevatorRequest;
import java.util.ArrayList;

public class Elevator {
  private int id;
  private int currentFloor;
  private Direction direction;
  private State state;
  private List<ElevatorObserver> observers;
  private Queue<ElevatorRequest> requests;

  public Elevator(int id, int initialFloor) {
    this.id = id;
    this.currentFloor = initialFloor;
    this.direction = Direction.IDLE;
    this.state = State.IDLE;
    this.observers = new ArrayList<>();
    this.requests = new LinkedList<>();
  }

  public int getId() {
    return id;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
    notifyObserversStateChange();
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    if (this.state != state) {
      this.state = state;
      notifyObserversStateChange();
    }
  }

  public Queue<ElevatorRequest> getRequestsQueue() {
    return new LinkedList<>(requests);
  }

  // Method for scheduling strategies that need to modify the queue
  // Note: This returns the actual queue, not a copy
  public Queue<ElevatorRequest> getRequestsQueueForScheduling() {
    return requests;
  }

  public List<ElevatorRequest> getDestinationFloors() {
    return new ArrayList<>(requests);
  }

  public void addRequest(ElevatorRequest request) {
    requests.offer(request);
    if (state == State.IDLE) {
      setState(State.MOVING);
    }
  }

  public void removeRequest(ElevatorRequest request) {
    requests.remove(request);
    if (requests.isEmpty()) {
      setDirection(Direction.IDLE);
      setState(State.IDLE);
    }
  }

  public void moveToFloor(int targetFloor) {
    if (targetFloor == currentFloor) {
      setState(State.STOPPED);
      return;
    }

    setState(State.MOVING);

    if (targetFloor > currentFloor) {
      setDirection(Direction.UP);
      while (currentFloor < targetFloor) {
        currentFloor++;
        notifyObserversFloorChange();
        try {
          Thread.sleep(500); // Simulate movement
        } catch (InterruptedException _) {
          Thread.currentThread().interrupt();
        }
      }
    } else {
      setDirection(Direction.DOWN);
      while (currentFloor > targetFloor) {
        currentFloor--;
        notifyObserversFloorChange();
        try {
          Thread.sleep(500); // Simulate movement
        } catch (InterruptedException _) {
          Thread.currentThread().interrupt();
        }
      }
    }

    setState(State.STOPPED);
    System.out.println("Elevator " + id + " arrived at floor " + currentFloor);
  }

  public void addObserver(ElevatorObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(ElevatorObserver observer) {
    observers.remove(observer);
  }

  private void notifyObserversStateChange() {
    for (ElevatorObserver observer : observers) {
      observer.onElevatorStateChange(this, state);
    }
  }

  private void notifyObserversFloorChange() {
    for (ElevatorObserver observer : observers) {
      observer.onElevatorFloorChange(this, currentFloor);
    }
  }

  public boolean hasRequests() {
    return !requests.isEmpty();
  }
}