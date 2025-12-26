package parking_lot.vehicle;

import parking_lot.enums.VehicleType;
import parking_lot.fee.ParkingFeeStrategy;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type, String licensePlate, ParkingFeeStrategy feeStrategy) {
        switch (type) {
            case CAR:
                return new CarVehicle(licensePlate, feeStrategy);
            case MOTORCYCLE:
                return new BikeVehicle(licensePlate, feeStrategy);
            case TRUCK:
                return new TruckVehicle(licensePlate, feeStrategy);
            case OTHER:
                return new OtherVehicle(licensePlate, feeStrategy);
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }

    }
}
