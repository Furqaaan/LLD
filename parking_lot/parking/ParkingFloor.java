package parking_lot.parking;

import java.util.List;
import java.util.ArrayList;
import parking_lot.parking.spot.ParkingSpot;
import parking_lot.vehicle.Vehicle;

public class ParkingFloor {
    private List<ParkingSpot> spots;
    private int floorNumber;

    public ParkingFloor(int floorNumber) {
      this.floorNumber = floorNumber;
      this.spots = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot) {
      this.spots.add(spot);
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
      for (ParkingSpot spot : spots) {
        if (spot.canPark(vehicle)) {
          return spot;
        }
      }

      return null;
    }

    public List<ParkingSpot> getParkingSpots() {
      return spots;
    }

    public int getFloorNumber() {
      return floorNumber;
    }

    public ParkingSpot findSpotByNumber(int spotNumber) {
      for (ParkingSpot spot : spots) {
        if (spot.getSpotNumber() == spotNumber) {
          return spot;
        }
      }
      return null;
    }
}
