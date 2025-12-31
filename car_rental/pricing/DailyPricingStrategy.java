package car_rental.pricing;

import car_rental.vehicle.Vehicle;

public class DailyPricingStrategy implements PricingStrategy {
    @Override
    public double calculateRentalPrice(Vehicle vehicle, int rentalPeriod) {
        return vehicle.getBaseRentalPrice() * rentalPeriod;
    }
}
