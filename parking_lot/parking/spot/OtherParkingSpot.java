package parking_lot.parking.spot;

import parking_lot.enums.VehicleType;

public class OtherParkingSpot extends ParkingSpot {
    public OtherParkingSpot(int spotNumber) {
        super(spotNumber, VehicleType.OTHER);
    }
}
