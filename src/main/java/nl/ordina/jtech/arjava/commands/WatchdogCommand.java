package nl.ordina.jtech.arjava.commands;

public class WatchdogCommand extends ATCommand {
    private static final String WATCHDOG_PREFIX = "AT*COMWDG";

    public WatchdogCommand() {
        this.commandPrefix = WATCHDOG_PREFIX;
    }
}
