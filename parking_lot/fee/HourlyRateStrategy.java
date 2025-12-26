package parking_lot.fee;

import parking_lot.enums.DurationType;
import parking_lot.enums.VehicleType;

public class HourlyRateStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        double multiplier = 1;

        if (durationType == DurationType.DAY) {
            multiplier = 24;
        }

        double price = duration * multiplier;

        switch (vehicleType) {
            case CAR:
                return price * 10.0;
            case MOTORCYCLE:
                return price * 5.0;
            case TRUCK:
                return price * 20.0;
            case OTHER:
                return price * 5.0;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
    }
}
