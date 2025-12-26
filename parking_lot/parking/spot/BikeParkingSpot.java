package parking_lot.parking.spot;

import parking_lot.enums.VehicleType;

public class BikeParkingSpot extends ParkingSpot {
    public BikeParkingSpot(int spotNumber) {
        super(spotNumber, VehicleType.MOTORCYCLE);
    }
}
