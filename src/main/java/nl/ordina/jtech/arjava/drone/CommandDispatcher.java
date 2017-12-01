package nl.ordina.jtech.arjava.drone;

import nl.ordina.jtech.arjava.commands.*;
import nl.ordina.jtech.arjava.communication.CommandChannel;

import java.util.LinkedList;
import java.util.Queue;

public class CommandDispatcher implements Runnable {
    private static final int WATCHDOG_INTERVAL = 100;
    private CommandChannel commandChannel;
    private Queue<ATCommand> commandQueue;
    private boolean enabled;
    private long lastCommandTime;

    public CommandDispatcher() {
        commandChannel = new CommandChannel();
        commandQueue = new LinkedList<ATCommand>();
    }

    public void run() {
        lastCommandTime = 0;
        while(enabled) {
            while(!commandQueue.isEmpty()) {
                commandChannel.sendCommand(commandQueue.poll());
                lastCommandTime = System.currentTimeMillis();
            }

            // Make sure the connection is kept alive.
            if ((System.currentTimeMillis() - lastCommandTime) > WATCHDOG_INTERVAL) {
                commandChannel.sendCommand(new WatchdogCommand());
                lastCommandTime = System.currentTimeMillis();
            }
        }

        commandChannel.resetSequenceNumber();
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void sendTakeOffCommand() {
        commandQueue.add(new TakeOffCommand());
    }

    public void sendLandCommand() {
        commandQueue.add(new LandCommand());
    }

    public void sendProgressiveCommand(float roll, float pitch, float gaz, float rotate) {
        commandQueue.add(new ProgressiveCommand(ControlMode.Progressive, roll, pitch, gaz, rotate));
    }

    public void sendConfigCommand(String configurationOption, String configurationValue) {
        commandQueue.add(new ConfigurationCommand(configurationOption, configurationValue));
    }
}
