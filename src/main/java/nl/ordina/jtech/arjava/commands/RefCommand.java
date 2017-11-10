package nl.ordina.jtech.arjava.commands;

public abstract class RefCommand extends ATCommand {
    private static final String REF_PREFIX = "AT*REF";
    protected int refParameter;

    public RefCommand() {
        commandPrefix = REF_PREFIX;

        // Bits 18, 20, 22, 24 and 28 should be set to 1.
        // As read in the Developer Guide.
        refParameter |= (1 << 18) | (1 << 20) | (1 << 22) | (1 << 24) | (1 << 28);
    }
}
