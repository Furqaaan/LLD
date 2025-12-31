package car_rental.pricing;

import car_rental.vehicle.Vehicle;

public class HourlyPricingStrategy implements PricingStrategy {
    private static final double HOURLY_RATE_MULTIPLIER = 0.2; // 20% of daily rate per hour

    @Override
    public double calculateRentalPrice(Vehicle vehicle, int rentalPeriod) {
        double dailyRate = vehicle.getBaseRentalPrice();
        return dailyRate * HOURLY_RATE_MULTIPLIER * rentalPeriod;
    }
}
