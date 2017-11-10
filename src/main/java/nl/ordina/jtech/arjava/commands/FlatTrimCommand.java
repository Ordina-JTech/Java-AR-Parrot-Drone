package nl.ordina.jtech.arjava.commands;

public class FlatTrimCommand extends ATCommand {
    private static final String FTRIM_PREFIX = "AT*FTRIM";

    public FlatTrimCommand() {
        commandPrefix = FTRIM_PREFIX;
    }
}
