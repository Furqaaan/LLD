package car_rental.pricing;

import car_rental.vehicle.Vehicle;

public interface PricingStrategy {
    double calculateRentalPrice(Vehicle vehicle, int rentalPeriod);
}
