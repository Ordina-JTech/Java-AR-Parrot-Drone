package nl.ordina.jtech.arjava.commands;

public class ProgressiveMagCommand extends ProgressiveCommand {
    private static final String PCMDMAG_PREFIX = "AT*PCMD_MAG";

    public ProgressiveMagCommand(ControlMode controlMode,
                                 float roll, float pitch, float gaz, float rotate,
                                 float magnetoPsi, float magnetoPsiAccuracy) {
        super(controlMode, roll, pitch, gaz, rotate);
        commandPrefix = PCMDMAG_PREFIX;
        parameters.add(magnetoPsi);
        parameters.add(magnetoPsiAccuracy);
    }
}
