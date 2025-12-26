package parking_lot.fee;

import parking_lot.enums.DurationType;
import parking_lot.enums.VehicleType;

public class PremiumRateStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        double premiumRate = 10.0;
        double multiplier = 1;

        if (durationType == DurationType.DAY) {
            multiplier = 24;
        }

        double price = duration * multiplier * premiumRate;

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
