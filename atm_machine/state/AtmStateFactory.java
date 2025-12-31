package atm_machine.state;

public class AtmStateFactory {
    private static AtmStateFactory instance = null;

    private AtmStateFactory() {
    }

    public static AtmStateFactory getInstance() {
        if (instance == null) {
            instance = new AtmStateFactory();
        }
        return instance;
    }

    public AtmState createIdleState() {
        return new IdleState();
    }

    public AtmState createHasCardState() {
        return new HasCardState();
    }

    public AtmState createSelectOperationState() {
        return new SelectOperationState();
    }

    public AtmState createTransactionState() {
        return new TransactionState();
    }
}
