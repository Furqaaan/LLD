package vending_machine.enums;

public enum Coin {
    ONE_RUPEE(1),
    TWO_RUPEES(2),
    FIVE_RUPEES(5),
    TEN_RUPEES(10);

    public int value;

    Coin(int value) {
        this.value = value;
    }
}