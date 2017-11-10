package nl.ordina.jtech.arjava.communication;

import nl.ordina.jtech.arjava.commands.*;

import java.util.LinkedList;
import java.util.Queue;

public class CommandDispatcher implements Runnable {
    private static final int WATCHDOG_INTERVAL = 100;
    private CommandChannel commandChannel;
    private Queue<ATCommand> commandQueue;
    private boolean enabled;

    public CommandDispatcher() {
        commandChannel = new CommandChannel();
        commandQueue = new LinkedList<ATCommand>();
    }

    public void run() {
        while(enabled) {
            while(!commandQueue.isEmpty()) {
                commandChannel.sendCommand(commandQueue.poll());
            }

            try {
                Thread.sleep(WATCHDOG_INTERVAL);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }

            // Make sure the connection is kept alive.
            commandChannel.sendCommand(new WatchdogCommand());
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
}
