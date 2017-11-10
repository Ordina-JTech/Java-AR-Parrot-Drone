package nl.ordina.jtech.arjava.commands;

public class EmergencyCommand extends RefCommand {
    public EmergencyCommand() {
        // Set 8th bit to 1 to trigger emergency.
        // WARNING: Engines will cut off no matter what.
        refParameter |= (1 << 8);
        parameters.add(refParameter);
    }
}
