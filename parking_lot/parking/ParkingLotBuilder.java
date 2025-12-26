package parking_lot.parking;

import java.util.List;
import java.util.ArrayList;

import parking_lot.parking.spot.CarParkingSpot;
import parking_lot.parking.spot.BikeParkingSpot;
import parking_lot.parking.spot.OtherParkingSpot;

public class ParkingLotBuilder {
    private List<ParkingFloor> floors;

    public ParkingLotBuilder() {
        this.floors = new ArrayList<>();
    }

    public ParkingLotBuilder addFloor(ParkingFloor floor) {
        floors.add(floor);
        return this;
    }

    public ParkingLotBuilder createFloor(int floorNumber, int numOfCarSpots,
            int numOfBikeSpots, int... otherSpotCounts) {
        ParkingFloor floor = new ParkingFloor(floorNumber);

        for (int i = 0; i < numOfCarSpots; i++) {
            floor.addParkingSpot(new CarParkingSpot(i + 1));
        }

        for (int i = 0; i < numOfBikeSpots; i++) {
            floor.addParkingSpot(new BikeParkingSpot(numOfCarSpots + i + 1));
        }

        int spotOffset = numOfCarSpots + numOfBikeSpots;
        for (int k= 0; k < otherSpotCounts.length; k++) {
            floor.addParkingSpot(new OtherParkingSpot(spotOffset + k + 1));
        }

        floors.add(floor);

        return this;
    }

    public ParkingLot build() {
        return new ParkingLot(floors);
    }
}