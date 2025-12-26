package parking_lot.payment;

public class PaymentStrategyFactory {
    public static PaymentStrategy createPaymentStrategy(int paymentMethod) {
        switch (paymentMethod) {
            case 1:
                return new CardPayment();
            case 2:
                return new CashPayment();
            default:
                throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
    }
}
