package atm_machine.state;

public class IdleState implements AtmState {
    public IdleState() {
        System.out.println("ATM is in Idle State - Please insert your card");
    }

    @Override
    public String getStateName() {
        return "IdleState";
    }

    @Override
    public AtmState next(AtmMachineContext context) {
        if (context.getCurrentCard() != null) {
            return context.getStateFactory().createHasCardState();
        }
        return this;
    }
}
