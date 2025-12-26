package parking_lot.vehicle;

import parking_lot.enums.VehicleType;
import parking_lot.fee.ParkingFeeStrategy;

public class CarVehicle extends Vehicle {
    public CarVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.CAR, feeStrategy);
    }

}
