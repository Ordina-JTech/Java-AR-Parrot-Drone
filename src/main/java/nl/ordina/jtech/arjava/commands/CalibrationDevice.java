package nl.ordina.jtech.arjava.commands;

public enum CalibrationDevice {
    Magnetometer(0);

    private final int value;

    CalibrationDevice(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
