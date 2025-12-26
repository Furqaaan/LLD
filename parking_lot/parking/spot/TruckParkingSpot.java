package parking_lot.parking.spot;

import parking_lot.enums.VehicleType;

public class TruckParkingSpot extends ParkingSpot {
    public TruckParkingSpot(int spotNumber) {
        super(spotNumber, VehicleType.TRUCK);
    }
}
