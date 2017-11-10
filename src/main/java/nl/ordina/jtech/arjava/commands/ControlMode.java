package nl.ordina.jtech.arjava.commands;

public enum ControlMode {
    Hover(0),
    Progressive(1),
    CombinedYaw(1 << 1),
    AbsoluteControl(1 << 2);

    private final int value;

    ControlMode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
