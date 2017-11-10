package nl.ordina.jtech.arjava.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class ATCommand {
    protected String commandPrefix;
    protected List<Object> parameters;
    private long sequenceNumber;


    public ATCommand() {
        parameters = new ArrayList<Object>();
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getCommandString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(commandPrefix)
                .append("=")
                .append(sequenceNumber);

        for (Object o : parameters) {
            stringBuilder.append(",");
            if (o instanceof String) {
                stringBuilder.append("\"");
                stringBuilder.append(o);
                stringBuilder.append("\"");
            } else if (o instanceof Float) {
                float f = (Float) o;
                stringBuilder.append(Float.floatToIntBits(f));
            } else {
                stringBuilder.append(o);
            }
        }

        stringBuilder.append("\r");

        return stringBuilder.toString();
    }
}
