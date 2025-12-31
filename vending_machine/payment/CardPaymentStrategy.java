package vending_machine.payment;

public class CardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CardPaymentStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate card payment processing (replace with actual logic)
        System.out.println("Processing card payment of " + amount + " using card " + cardNumber);
        // In real-world, integrate with a payment gateway here.
        // For simulation, always return true for simplicity.
        return true;
    }
}
