package nl.ordina.jtech.arjava.drone;

import nl.ordina.jtech.arjava.gui.DroneControlFeedback;

public class RemoteController implements Runnable {
    private static final int COMMAND_INTERVAL = 30;
    private float requestedPitch;
    private float requestedRoll;
    private float requestedGaz;
    private float requestedRotate;
    private CommandDispatcher commandDispatcher;
    private boolean remoteControlEnabled;
    private DroneControlFeedback droneControlFeedback;

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

    public void setDroneControlFeedback(DroneControlFeedback droneControlFeedback) {
        this.droneControlFeedback = droneControlFeedback;
        updateGUI();
    }

    public void setRequestedPitch(float requestedPitch) {
        this.requestedPitch = requestedPitch;
        updateGUI();
    }

    public void setRequestedRoll(float requestedRoll) {
        this.requestedRoll = requestedRoll;
        updateGUI();
    }

    public void setRequestedGaz(float requestedGaz) {
        this.requestedGaz = requestedGaz;
        updateGUI();
    }

    public void setRequestedRotate(float requestedRotate) {
        this.requestedRotate = requestedRotate;
        updateGUI();
    }

    public void enable() {
        remoteControlEnabled = true;
    }

    public void disable() {
        remoteControlEnabled = false;
    }

    private void updateGUI() {
        if (requestedRotate > 0) {
            droneControlFeedback.clockwiseOn();
        } else if (requestedRotate < 0) {
            droneControlFeedback.counterClockwiseOn();
        } else if (requestedRotate == 0) {
            droneControlFeedback.clockwiseOff();
            droneControlFeedback.counterClockwiseOff();
        }

        if (requestedPitch > 0) {
            droneControlFeedback.backwardsOn();
        } else if (requestedPitch < 0) {
            droneControlFeedback.forwardOn();
        } else if (requestedPitch == 0) {
            droneControlFeedback.backwardsOff();
            droneControlFeedback.forwardOff();
        }

        if (requestedRoll > 0) {
            droneControlFeedback.rightOn();
        } else if (requestedRoll < 0) {
            droneControlFeedback.leftOn();
        } else if (requestedRoll == 0) {
            droneControlFeedback.rightOff();
            droneControlFeedback.leftOff();
        }

        if (requestedGaz > 0) {
            droneControlFeedback.upOn();
        } else if (requestedGaz < 0) {
            droneControlFeedback.downOn();
        } else if (requestedGaz == 0) {
            droneControlFeedback.upOff();
            droneControlFeedback.downOff();
        }
    }
}
