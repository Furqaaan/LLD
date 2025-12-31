package atm_machine.state;

public interface AtmState {
    String getStateName();
    AtmState next(AtmMachineContext context);
}