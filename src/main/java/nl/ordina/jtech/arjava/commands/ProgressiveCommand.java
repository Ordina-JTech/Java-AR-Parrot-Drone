package nl.ordina.jtech.arjava.commands;

public class ProgressiveCommand extends ATCommand {
    private static final String PCMD_PREFIX = "AT*PCMD";

    public ProgressiveCommand(ControlMode controlMode,
                              float roll, float pitch, float gaz, float rotate) {
        commandPrefix = PCMD_PREFIX;
        parameters.add(controlMode.getValue());
        parameters.add(roll);
        parameters.add(pitch);
        parameters.add(gaz);
        parameters.add(rotate);
    }
}
