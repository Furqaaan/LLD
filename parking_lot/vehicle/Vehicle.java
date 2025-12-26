package parking_lot.vehicle;

import parking_lot.enums.VehicleType;
import parking_lot.fee.ParkingFeeStrategy;
import parking_lot.enums.DurationType;

public abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private ParkingFeeStrategy feeStrategy;

    public Vehicle(String licensePlate, VehicleType type, ParkingFeeStrategy feeStrategy) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.feeStrategy = feeStrategy;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public ParkingFeeStrategy getFeeStrategy() {
        return feeStrategy;
    }

    public double calculateFee(int duration, DurationType durationType) {
        return feeStrategy.calculateFee(type, duration, durationType);
    }
}
