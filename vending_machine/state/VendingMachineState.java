package vending_machine.state;

public interface VendingMachineState {
    String getStateName();
    VendingMachineState next(VendingMachineContext context);
}