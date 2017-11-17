package nl.ordina.jtech.arjava.drone;

import javafx.scene.image.ImageView;
import nl.ordina.jtech.arjava.gui.DroneControlFeedback;
import nl.ordina.jtech.arjava.gui.UltrasonicData;
import nl.ordina.jtech.arjava.video.VideoReceiver;
import sun.management.Sensor;

public class Drone {
    private CommandDispatcher commandDispatcher;
    private RemoteController remoteController;
    private VideoReceiver videoReceiver;
    private SensorDataReceiver sensorDataReceiver;
    private boolean connectedToDrone;
    private boolean inManualControl;
    private Thread communicationThread;
    private Thread remoteControlThread;
    private Thread videoReceiverThread;
    private Thread sensorDataThread;

    public Drone() {
        commandDispatcher = new CommandDispatcher();
        remoteController = new RemoteController(commandDispatcher);
        videoReceiver = new VideoReceiver();
        sensorDataReceiver = new SensorDataReceiver();
        connectedToDrone = false;
    }

    public boolean isConnectedToDrone() {
        return connectedToDrone;
    }

    public boolean isInManualControl() {
        return inManualControl;
    }

    public void setDroneControlFeedback(DroneControlFeedback droneControlFeedback) {
        remoteController.setDroneControlFeedback(droneControlFeedback);
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
            communicationThread = new Thread(commandDispatcher);
            communicationThread.start();
            connectedToDrone = true;
        }
    }

    public void disconnectFromDrone() {
        stopCamera();
        disableManualControl();
        stopUltrasonicData();
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
            remoteControlThread = new Thread(remoteController);
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
        videoReceiverThread = new Thread(videoReceiver);
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

    public void startUltrasonicData(UltrasonicData ultrasonicData) {
        sensorDataReceiver.setUltrasonicData(ultrasonicData);
        sensorDataThread = new Thread(sensorDataReceiver);
        sensorDataThread.start();
    }

    public void stopUltrasonicData() {
        sensorDataReceiver.requestStop();

        try {
            if (sensorDataThread != null) {
                sensorDataThread.join(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
