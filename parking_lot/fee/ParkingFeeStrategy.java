package parking_lot.fee;

import parking_lot.enums.DurationType;
import parking_lot.enums.VehicleType;

public interface ParkingFeeStrategy {
    double calculateFee(VehicleType vehicleType, int duration, DurationType durationType);
}
