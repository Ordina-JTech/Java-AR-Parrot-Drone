package nl.ordina.jtech.arjava.drone;

import javafx.scene.image.ImageView;
import nl.ordina.jtech.arjava.video.VideoReceiver;

public class Drone {
    private CommandDispatcher commandDispatcher;
    private RemoteController remoteController;
    private VideoReceiver videoReceiver;
    private boolean connectedToDrone;
    private boolean inManualControl;
    Thread communicationThread;
    Thread remoteControlThread;
    Thread videoReceiverThread;

    public Drone() {
        commandDispatcher = new CommandDispatcher();
        remoteController = new RemoteController(commandDispatcher);
        videoReceiver = new VideoReceiver();
        connectedToDrone = false;
    }

    public boolean isConnectedToDrone() {
        return connectedToDrone;
    }

    public boolean isInManualControl() {
        return inManualControl;
    }

    public void setRequestedPitch(float requestedPitch) {
        remoteController.setRequestedPitch(requestedPitch);
    }

    public void setRequestedRoll(float requestedRoll) {
        remoteController.setRequestedRoll(requestedRoll);
    }

    public void setRequestedGaz(float requestedGaz) {
        remoteController.setRequestedGaz(requestedGaz);
    }

    public void setRequestedRotate(float requestedRotate) {
        remoteController.setRequestedRotate(requestedRotate);
    }

    public void connectToDrone() {
        if (!connectedToDrone) {
            commandDispatcher.enable();
            Thread communicationThread = new Thread(commandDispatcher);
            communicationThread.start();
            connectedToDrone = true;
        }
    }

    public void disconnectFromDrone() {
        stopCamera();
        disableManualControl();
        commandDispatcher.disable();

        try {
            if (communicationThread != null) {
                communicationThread.join(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        connectedToDrone = false;
    }

    public void enableManualControl() {
        if (connectedToDrone) {
            remoteController.enable();
            Thread remoteControlThread = new Thread(remoteController);
            remoteControlThread.start();
            inManualControl = true;
        }
    }

    public void disableManualControl() {
        remoteController.disable();

        try {
            if (remoteControlThread != null) {
                remoteControlThread.join(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inManualControl = false;
    }

    public void takeOff() {
        commandDispatcher.sendTakeOffCommand();
    }

    public void land() {
        commandDispatcher.sendLandCommand();
    }

    public void startCamera(ImageView imageView) {
        videoReceiver.setImageView(imageView);
        Thread videoReceiverThread = new Thread(videoReceiver);
        videoReceiverThread.start();
    }

    public void stopCamera() {
        videoReceiver.requestStop();

        try {
            if (videoReceiverThread != null) {
                videoReceiverThread.join(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
