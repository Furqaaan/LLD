package parking_lot.payment;

public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Cash");
    }

}
