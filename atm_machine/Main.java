package atm_machine;
import atm_machine.state.AtmMachineContext;
import atm_machine.model.Account;
import atm_machine.model.Card;
import atm_machine.enums.TransactionType;
    
public class Main {
    public static void main(String[] args) {
        // Create and initialize ATM
        AtmMachineContext atm = new AtmMachineContext();

        // Add sample accounts
        atm.addAccount(new Account("123456", 1000.0));
        atm.addAccount(new Account("654321", 500.0));

        try {
            // Sample workflow
            System.out.println("=== Starting ATM Demo ===");

            // Insert card
            atm.insertCard(new Card("123456", 1234, "654321"));

            // Enter PIN
            atm.enterPin(1234);

            // Select operation
            atm.selectOperation(TransactionType.WITHDRAW_CASH);

            // Perform transaction
            atm.performTransaction(100.0);

            // Select another operation
            atm.selectOperation(TransactionType.CHECK_BALANCE);

            // Perform balance check
            atm.performTransaction(0.0);

            // Return card
            atm.returnCard();

            System.out.println("=== ATM Demo Completed ===");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
