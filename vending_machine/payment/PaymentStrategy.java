package vending_machine.payment;

public interface PaymentStrategy {
    boolean processPayment(double amount);
}