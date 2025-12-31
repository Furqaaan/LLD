package atm_machine.state;

import atm_machine.state.AtmState;
import atm_machine.state.AtmMachineContext;

public class TransactionState implements AtmState {
    public TransactionState() {
        System.out.println("ATM is in Transaction State");
    }
    
    @Override
    public String getStateName() {
        return "TransactionState";
    }
    
    @Override
    public AtmState next(AtmMachineContext context) {
        if (context.getCurrentCard() == null) {
            return context.getStateFactory().createIdleState();
        }
        
        // After transaction completion, go back to select operation
        return context.getStateFactory().createSelectOperationState();
    }
}

