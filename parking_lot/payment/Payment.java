package parking_lot.payment;

public class Payment {
    private PaymentStrategy paymentStrategy;
    private double amount;

    public Payment(PaymentStrategy paymentStrategy, double amount) {
        this.paymentStrategy = paymentStrategy;
        this.amount = amount;
    }

    public void pay() {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        
        paymentStrategy.pay(amount);
    }
}
