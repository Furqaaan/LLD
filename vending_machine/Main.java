package vending_machine;

import vending_machine.state.VendingMachineContext;
import vending_machine.enums.Coin;
import vending_machine.item.Item;
import vending_machine.enums.ItemType;
import vending_machine.item.ItemShelf;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        VendingMachineContext vendingMachine = new VendingMachineContext();

        try {
            System.out.println("|");
            System.out.println("Filling up the inventory");
            System.out.println("|");
            fillUpInventory(vendingMachine);
            displayInventory(vendingMachine);

            System.out.println("Select Payment Method:");
            System.out.println("1. Coin Payment");
            System.out.println("2. Card Payment");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int paymentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (paymentChoice) {
                case 1:
                    System.out.println("Inserting coins");
                    vendingMachine.clickOnInsertCoinButton(Coin.TEN_RUPEES);
                    vendingMachine.clickOnInsertCoinButton(Coin.FIVE_RUPEES);
                    break;
                case 2:
                    System.out.println("Making card payment");
                    System.out.print("Enter card number: ");
                    String cardNumber = scanner.nextLine();
                    System.out.print("Enter expiry date (MM/YY): ");
                    String expiryDate = scanner.nextLine();
                    System.out.print("Enter CVV: ");
                    String cvv = scanner.nextLine();
                    vendingMachine.clickOnCardPaymentButton(cardNumber, expiryDate, cvv);
                    break;
                default:
                    System.out.println("Invalid payment choice.");
                    return;
            }
            System.out.println("|");
            System.out.println("Clicking on ProductSelectionButton");
            System.out.println("|");
            vendingMachine.clickOnStartProductSelectionButton(102);
            displayInventory(vendingMachine);
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            displayInventory(vendingMachine);
        }
    }

    private static void fillUpInventory(VendingMachineContext vendingMachine) {
        for (int i = 0; i < 10; i++) {
            Item newItem = new Item();
            int codeNumber = 101 + i; // Shelf code
            // Set item type and price based on the index range
            if (i >= 0 && i < 3) {
                newItem.setType(ItemType.COKE);
                newItem.setPrice(12);
            } else if (i >= 3 && i < 5) {
                newItem.setType(ItemType.PEPSI);
                newItem.setPrice(9);
            } else if (i >= 5 && i < 7) {
                newItem.setType(ItemType.JUICE);
                newItem.setPrice(13);
            } else if (i >= 7 && i < 10) {
                newItem.setType(ItemType.SODA);
                newItem.setPrice(7);
            }
            // Update the inventory with multiple same items per shelf
            for (int j = 0; j < 5; j++) {
                // Add 5 items to each shelf
                vendingMachine.updateInventory(newItem, codeNumber);
            }
        }
    }

    private static void displayInventory(VendingMachineContext vendingMachine) {
        ItemShelf[] slots = vendingMachine.getInventory().getInventory();
        for (ItemShelf slot : slots) {
            List<Item> items = slot.getItems(); // Get the list of items in the shelf
            if (!items.isEmpty()) {
                System.out.println("CodeNumber: " + slot.getCode() + " Items: ");
                for (Item item : items) { // Display all items in the shelf
                    System.out.println(
                            "    - Item: " + item.getType().name() + ", Price: " + item.getPrice());
                }
                System.out.println("SoldOut: " + slot.checkIsSoldOut());
            } else {
                // Display empty shelf information
                System.out.println("CodeNumber: " + slot.getCode() + " Items: EMPTY"
                        + " SoldOut: " + slot.checkIsSoldOut());
            }
        }
    }
}
