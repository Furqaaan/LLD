package atm_machine.state;

public class HasCardState implements AtmState {
    public HasCardState() {
        System.out.println("ATM is in Has Card State - Please enter your PIN");
    }

    @Override
    public String getStateName() {
        return "HasCardState";
    }

    @Override
    public AtmState next(AtmMachineContext context) {
        if (context.getCurrentCard() == null) {
            return context.getStateFactory().createIdleState();
        }
        if (context.getCurrentAccount() != null) {
            return context.getStateFactory().createSelectOperationState();
        }
        return this;
    }
}