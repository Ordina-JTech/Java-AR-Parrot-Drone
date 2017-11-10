package nl.ordina.jtech.arjava.drone;

import javafx.scene.image.ImageView;
import nl.ordina.jtech.arjava.communication.CommandDispatcher;
import nl.ordina.jtech.arjava.video.VideoReceiver;

public class Drone {
    private CommandDispatcher commandDispatcher;
    private RemoteController remoteController;
    private VideoReceiver videoReceiver;
    private boolean connectedToDrone;
    private boolean inManualControl;
    private Thread communicationThread;
    private Thread remoteControlThread;
    private Thread videoReceiverThread;

    public Drone() {
        commandDispatcher = new CommandDispatcher();
        remoteController = new RemoteController(commandDispatcher);
        videoReceiver = new VideoReceiver();
        communicationThread = new Thread(commandDispatcher);
        remoteControlThread = new Thread(remoteController);
        videoReceiverThread = new Thread(videoReceiver);
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
            communicationThread.start();
            connectedToDrone = true;
        }
    }

    public void disconnectFromDrone() {
        commandDispatcher.disable();
        connectedToDrone = false;
    }

    public void enableManualControl() {
        if (connectedToDrone) {
            remoteController.enable();
            remoteControlThread.start();
            inManualControl = true;
        }
    }

    public void disableManualControl() {
        remoteController.disable();
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
        videoReceiverThread.start();
    }

    public void stopCamera() {
        videoReceiver.requestStop();
    }
}
