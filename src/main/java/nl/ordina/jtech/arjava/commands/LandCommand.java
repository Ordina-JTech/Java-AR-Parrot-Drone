package nl.ordina.jtech.arjava.commands;

public class LandCommand extends RefCommand {
    public LandCommand() {
        // Set 9th bit to 0 to land.
        refParameter &= ~(1 << 9);
        parameters.add(refParameter);
    }
}
