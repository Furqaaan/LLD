package car_rental.payment;

import car_rental.enums.PaymentType;

public class PaymentFactory {
    public static PaymentStrategy createPaymentStrategy(PaymentType paymentType) {
        switch (paymentType) {
            case CREDIT_CARD:
                return new CreditCardPayment();
            case CASH:
                return new CashPayment();
            case PAYPAL:
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}

