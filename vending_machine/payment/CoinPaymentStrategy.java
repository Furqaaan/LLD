package vending_machine.payment;

import java.util.List;
import vending_machine.enums.Coin;

public class CoinPaymentStrategy implements PaymentStrategy {
    private List<Coin> coins;

    public CoinPaymentStrategy(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public boolean processPayment(double amount) {
        int total = 0;
        for (Coin coin : coins) {
            total += coin.value;
        }
        return total >= amount;
    }
}
