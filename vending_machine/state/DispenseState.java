package vending_machine.state;

public class DispenseState implements VendingMachineState {
    public DispenseState() {
        System.out.println("Vending machine is now in Dispense State");
    }

    @Override
    public String getStateName() {
        return "DispenseState";
    }

    @Override
    public VendingMachineState next(VendingMachineContext context) {
        try {
            context.resetBalance();
            context.resetSelection();
            context.setPaymentStrategy(null);
            return new IdleState();
        } catch (Exception e) {
            context.setPaymentStrategy(null);
            return new IdleState();
        }
    }
}