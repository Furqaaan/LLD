package parking_lot.vehicle;

import parking_lot.enums.VehicleType;
import parking_lot.fee.ParkingFeeStrategy;

public class TruckVehicle extends Vehicle {
    public TruckVehicle(String licensePlate, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, VehicleType.TRUCK, feeStrategy);
    }
    
}
