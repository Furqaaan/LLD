package parking_lot.vehicle;

import parking_lot.enums.VehicleType;
import parking_lot.fee.ParkingFeeStrategy;

public class OtherVehicle extends Vehicle {
    public OtherVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.OTHER, feeStrategy);
    }
}
