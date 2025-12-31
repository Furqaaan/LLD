package car_rental.payment;

public class PaymentProcessor {
    public boolean processPayment(double amount, PaymentStrategy paymentStrategy) {
        paymentStrategy.processPayment(amount);
        return true;
    }
}
