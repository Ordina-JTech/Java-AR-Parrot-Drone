package nl.ordina.jtech.arjava.drone;

import nl.ordina.jtech.arjava.communication.CommandDispatcher;

public class RemoteController implements Runnable {
    private static final int COMMAND_INTERVAL = 30;
    private float requestedPitch;
    private float requestedRoll;
    private float requestedGaz;
    private float requestedRotate;
    private CommandDispatcher commandDispatcher;
    private boolean remoteControlEnabled;

    public RemoteController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public void run() {
        while (remoteControlEnabled) {
            commandDispatcher.sendProgressiveCommand(
                    requestedRoll, requestedPitch, requestedGaz, requestedRotate);
            try {
                Thread.sleep(COMMAND_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setRequestedPitch(float requestedPitch) {
        this.requestedPitch = requestedPitch;
    }

    public void setRequestedRoll(float requestedRoll) {
        this.requestedRoll = requestedRoll;
    }

    public void setRequestedGaz(float requestedGaz) {
        this.requestedGaz = requestedGaz;
    }

    public void setRequestedRotate(float requestedRotate) {
        this.requestedRotate = requestedRotate;
    }

    public void enable() {
        remoteControlEnabled = true;
    }

    public void disable() {
        remoteControlEnabled = false;
    }
}
