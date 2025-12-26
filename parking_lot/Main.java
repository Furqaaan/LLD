package parking_lot;


import parking_lot.parking.spot.ParkingSpot;
import parking_lot.parking.ParkingLot;
import parking_lot.fee.ParkingFeeStrategy;
import parking_lot.fee.HourlyRateStrategy;
import parking_lot.fee.PremiumRateStrategy;
import parking_lot.vehicle.Vehicle;
import parking_lot.vehicle.VehicleFactory;
import parking_lot.enums.VehicleType;
import java.util.Scanner;
import parking_lot.enums.DurationType;
import parking_lot.payment.PaymentStrategy;
import parking_lot.payment.PaymentStrategyFactory;
import parking_lot.parking.ParkingLotBuilder;

public class Main {

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLotBuilder()
                .createFloor(1, 2, 2)
                .createFloor(2, 3, 1, 1)
                .build();

        ParkingFeeStrategy basicHourlyRateStrategy = new HourlyRateStrategy();
        ParkingFeeStrategy premiumRateStrategy = new PremiumRateStrategy();

        Vehicle car1 = VehicleFactory.createVehicle(VehicleType.CAR, "CAR123", basicHourlyRateStrategy);
        Vehicle car2 = VehicleFactory.createVehicle(VehicleType.CAR, "CAR345", basicHourlyRateStrategy);

        Vehicle bike1 = VehicleFactory.createVehicle(VehicleType.MOTORCYCLE, "BIKE456", premiumRateStrategy);
        Vehicle bike2 = VehicleFactory.createVehicle(VehicleType.MOTORCYCLE, "BIKE123", premiumRateStrategy);

        ParkingSpot carSpot = parkingLot.parkVehicle(car1);
        ParkingSpot bikeSpot = parkingLot.parkVehicle(bike1);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method for your vehicle:");
        System.out.println("1. Card");
        System.out.println("2. Cash");

        int paymentMethod = scanner.nextInt();
        if (carSpot != null) {
            double carFee = car1.calculateFee(2, DurationType.HOUR);
            PaymentStrategy carPaymentStrategy = PaymentStrategyFactory.createPaymentStrategy(paymentMethod);

            carPaymentStrategy.pay(carFee);
            parkingLot.vacateSpot(carSpot, car1);
        }

        if (bikeSpot != null) {
            double bikeFee = bike1.calculateFee(3, DurationType.HOUR);
            PaymentStrategy bikePaymentStrategy = PaymentStrategyFactory.createPaymentStrategy(paymentMethod);

            bikePaymentStrategy.pay(bikeFee);
            parkingLot.vacateSpot(bikeSpot, bike1);
        }

        scanner.close();
    }
}
