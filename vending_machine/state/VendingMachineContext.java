package vending_machine.state;

import java.util.List;
import java.util.ArrayList;
import vending_machine.inventory.Inventory;
import vending_machine.enums.Coin;
import vending_machine.item.Item;
import vending_machine.payment.PaymentStrategy;
import vending_machine.payment.CardPaymentStrategy;
import vending_machine.payment.CoinPaymentStrategy;

public class VendingMachineContext {
    private VendingMachineState currentState;
    private Inventory inventory;
    private List<Coin> coinList;
    private int selectedItemCode;
    private PaymentStrategy paymentStrategy;

    public VendingMachineContext() {
        inventory = new Inventory(10);
        coinList = new ArrayList<>();
        currentState = new IdleState();
        System.out.println("Initialized: " + currentState.getStateName());
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment strategy set.");
            return false;
        }
        return paymentStrategy.processPayment(amount);
    }

    public VendingMachineState getCurrentState() {
        return currentState;
    }

    public void advanceState() {
        VendingMachineState nextState = currentState.next(this);
        currentState = nextState;
        System.out.println("Current state: " + currentState.getStateName());
    }

    public void clickOnInsertCoinButton(Coin coin) {
        if (currentState instanceof IdleState || currentState instanceof HasMoneyState) {
            System.out.println("Inserted " + coin.name() + " worth " + coin.value);
            coinList.add(coin); // Add the coin to the list
            advanceState(); // Move to the next state
        } else {
            System.out.println("Cannot insert coin in " + currentState.getStateName());
        }
    }

    public void clickOnStartProductSelectionButton(int codeNumber) {
        if (currentState instanceof HasMoneyState) {
            advanceState();
            selectProduct(codeNumber);
        } else {
            System.out.println("Product selection button can only be clicked in HasMoney state");
        }
    }

    public void selectProduct(int codeNumber) {
        if (currentState instanceof SelectionState) {
            try {
                Item item = inventory.getItem(codeNumber);

                int balance = getBalance();
                if (balance < item.getPrice()) {
                    System.out.println(
                            "Insufficient amount. Product price: " + item.getPrice() + ", paid: " + balance);
                    return;
                }
                setSelectedItemCode(codeNumber);
                advanceState();
                dispenseItem(codeNumber, balance);

                if (balance >= item.getPrice()) {
                    int change = balance - item.getPrice();
                    System.out.println("Returning change: " + change);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Products can only be selected in Selection state");
        }
    }

    public void clickOnCardPaymentButton(String cardNumber, String expiryDate, String cvv) {
        if (currentState instanceof IdleState || currentState instanceof HasMoneyState) {
            System.out.println("Starting card payment process.");
            setPaymentStrategy(
                    new CardPaymentStrategy(cardNumber, expiryDate, cvv)); // Set the card payment strategy
            advanceState();
        } else {
            System.out.println("Cannot start card payment in " + currentState.getStateName());
        }
    }

    public void dispenseItem(int codeNumber, double amount) {
        try {
          setPaymentStrategy(new CoinPaymentStrategy(coinList));
          processPayment(amount);
          inventory.removeItem(codeNumber);
          advanceState();
        } catch (Exception e) {
          System.out.println("Failed to Dispense the Product with code : " + codeNumber);
        }
      }

    public int getBalance() {
        if (paymentStrategy instanceof CoinPaymentStrategy) {
            int balance = 0;
            for (Coin coin : coinList) {
                balance += coin.value;
            }
            return balance;
        }
        return 0;
    }

    public void resetBalance() {
        coinList.clear();
        paymentStrategy = null;
    }

    public void updateInventory(Item item, int codeNumber) {
        if (currentState instanceof IdleState) {
            try {
                inventory.addItem(item, codeNumber);
                System.out.println("Added " + item.getType() + " to slot " + codeNumber);
            } catch (Exception e) {
                System.out.println("Error updating inventory: " + e.getMessage());
            }
        } else {
            System.out.println("Inventory can only be updated in Idle state");
        }
    }

    // Getters and setters for context properties
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<Coin> coinList) {
        this.coinList = coinList;
    }

    public int getSelectedItemCode() {
        return selectedItemCode;
    }

    public void setSelectedItemCode(int codeNumber) {
        this.selectedItemCode = codeNumber;
    }

    public void resetSelection() {
        this.selectedItemCode = 0;
    }
}
