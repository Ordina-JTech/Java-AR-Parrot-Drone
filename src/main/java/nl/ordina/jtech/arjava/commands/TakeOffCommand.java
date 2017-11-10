package nl.ordina.jtech.arjava.commands;

public class TakeOffCommand extends RefCommand {
    public TakeOffCommand() {
        // Set 9th bit to 1 to take off.
        refParameter |= (1 << 9);
        parameters.add(refParameter);
    }
}
