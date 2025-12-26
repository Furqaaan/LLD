package parking_lot.parking;

import java.util.List;

import parking_lot.parking.spot.ParkingSpot;
import parking_lot.vehicle.Vehicle;

public class ParkingLot {
    private List<ParkingFloor> floors;

    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if (spot.canPark(vehicle)) {
                return spot;
            }
        }

        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);
            
            if (spot.canPark(vehicle)) {
                spot.park(vehicle);
                return spot;
            }
        }
        return null;
    }

    public void vacateSpot(ParkingSpot spot, Vehicle vehicle) {
        if (spot != null && spot.isOccupied()
                && spot.getVehicle().equals(vehicle)) {
            spot.vacate();

            System.out.println(vehicle.getType() + " vacated the spot: " + spot.getSpotNumber());
        } else {
            System.out.println("Invalid operation! Either the spot is already vacant "
                    + "or the vehicle does not match.");
        }
    }

    public ParkingSpot getSpotByNumber(int spotNumber) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findSpotByNumber(spotNumber);
            
            if (spot.getSpotNumber() == spotNumber) {
                return spot;
            }
        }
        return null;
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }
}
