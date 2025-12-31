package car_rental.vehicle;

import car_rental.enums.VehicleType;
import car_rental.enums.VehicleStatus;

public abstract class Vehicle {
    private String registrationNumber;
    private String model;
    private VehicleType type;
    private VehicleStatus status;
    private double baseRentalPrice;

    public Vehicle(String registrationNumber, String model, VehicleType type,
            double baseRentalPrice) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.baseRentalPrice = baseRentalPrice;
    }

    public abstract double calculateRentalFee(int days);

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public double getBaseRentalPrice() {
        return baseRentalPrice;
    }
}