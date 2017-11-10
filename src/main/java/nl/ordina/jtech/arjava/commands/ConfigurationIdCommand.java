package nl.ordina.jtech.arjava.commands;

public class ConfigurationIdCommand extends ATCommand {
    private static final String CONFIG_IDS_PREFIX = "AT*CONFIG_IDS";

    public ConfigurationIdCommand (String sessionId,
                                   String userId, String applicationId) {
        commandPrefix = CONFIG_IDS_PREFIX;
        parameters.add(sessionId);
        parameters.add(userId);
        parameters.add(applicationId);
    }
}
