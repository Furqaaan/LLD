package car_rental;

import car_rental.store.RentalSystem;
import car_rental.store.RentalStore;
import car_rental.location.Location;
import car_rental.vehicle.VehicleFactory;
import car_rental.vehicle.Vehicle;
import car_rental.enums.VehicleType;
import car_rental.user.User;
import car_rental.reservation.Reservation;
import car_rental.payment.PaymentStrategy;
import car_rental.payment.PaymentFactory;
import car_rental.enums.PaymentType;
import java.util.Scanner;
import java.util.Date;
import car_rental.pricing.DailyPricingStrategy;

public class Main {
    public static void main(String[] args) {
        // Get the Rental System instance (Singleton)
        RentalSystem rentalSystem = RentalSystem.getInstance();

        // Create rental stores
        RentalStore store1 = new RentalStore(
                1, "Downtown Rentals", new Location("123 Main St", "New York", "NY", "10001"));
        RentalStore store2 = new RentalStore(
                2, "Airport Rentals", new Location("456 Airport Rd", "Los Angeles", "CA", "90045"));
        rentalSystem.addStore(store1);
        rentalSystem.addStore(store2);

        // Create vehicles using Factory Pattern
        Vehicle economyCar = VehicleFactory.createVehicle(
                VehicleType.ECONOMY, "EC001", "Toyota", 50.0);
        Vehicle luxuryCar = VehicleFactory.createVehicle(
                VehicleType.LUXURY, "LX001", "Mercedes", 200.0);
        Vehicle suvCar = VehicleFactory.createVehicle(VehicleType.SUV, "SV001", "Honda", 75.0);

        // Add vehicles to stores
        store1.addVehicle(economyCar);
        store1.addVehicle(luxuryCar);
        store2.addVehicle(suvCar);

        // Register user
        User user1 = new User(123, "ABC", "abc@gmail.com");
        User user2 = new User(345, "BCD", "bcd@yahoo.com");

        rentalSystem.registerUser(user1);
        rentalSystem.registerUser(user2);

        // Create reservations
        Reservation reservation1 = rentalSystem.createReservation(user1.getId(), economyCar.getRegistrationNumber(),
                store1.getId(), store1.getId(), new Date(2025 - 1900, 4, 1),
                new Date(2025 - 1900, 5, 15), new DailyPricingStrategy());

        // Process payment using different strategies (Strategy Pattern)
        Scanner scanner = new Scanner(System.in);
        System.out.println("nProcessing payment for reservation #" + reservation1.getId());
        System.out.println("Total amount: $" + reservation1.getTotalAmount());
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. PayPal");

        int choice = 1; // Default to credit card for this example
        // In a real application, you would get user input:
        // int choice = scanner.nextInt();
        PaymentType paymentType;
        switch (choice) {
            case 1:
                paymentType = PaymentType.CREDIT_CARD;
                break;
            case 2:
                paymentType = PaymentType.CASH;
                break;
            case 3:
                paymentType = PaymentType.PAYPAL;
                break;
            default:
                System.out.println("Invalid choice! Defaulting to credit card payment.");
                paymentType = PaymentType.CREDIT_CARD;
                break;
        }
        // Create payment strategy using Factory Pattern
        PaymentStrategy paymentStrategy = PaymentFactory.createPaymentStrategy(paymentType);

        boolean paymentSuccess = rentalSystem.processPayment(reservation1.getId(), paymentStrategy);

        if (paymentSuccess) {
            System.out.println("Payment successful!");

            // Start the rental
            rentalSystem.startRental(reservation1.getId());

            // Simulate rental period
            System.out.println("nSimulating rental period...");

            // Complete the rental
            rentalSystem.completeRental(reservation1.getId());
        } else {
            System.out.println("Payment failed!");
        }

    }
}
