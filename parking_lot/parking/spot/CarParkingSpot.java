package parking_lot.parking.spot;

import parking_lot.enums.VehicleType;

public class CarParkingSpot extends ParkingSpot {
    public CarParkingSpot(int spotNumber) {
        super(spotNumber, VehicleType.CAR);
    }
}
