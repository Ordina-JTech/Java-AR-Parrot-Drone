package nl.ordina.jtech.arjava.commands;

public class CalibrateCommand extends ATCommand {
    private static final String CALIB_PREFIX = "AT*CALIB";

    public CalibrateCommand(CalibrationDevice calibrationDevice) {
        commandPrefix = CALIB_PREFIX;
        parameters.add(calibrationDevice.getValue());
    }
}
