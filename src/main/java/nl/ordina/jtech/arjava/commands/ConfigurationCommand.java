package nl.ordina.jtech.arjava.commands;

public class ConfigurationCommand extends ATCommand {
    private static final String CONFIG_PREFIX = "AT*CONFIG";

    public ConfigurationCommand (String configurationOption,
                                 String configurationValue) {
        commandPrefix = CONFIG_PREFIX;
        parameters.add(configurationOption);
        parameters.add(configurationValue);
    }
}
