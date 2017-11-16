package nl.ordina.jtech.arjava.drone;

import javafx.application.Platform;
import nl.ordina.jtech.arjava.communication.SensorDataChannel;
import nl.ordina.jtech.arjava.gui.UltrasonicData;

public class SensorDataReceiver implements Runnable {
    private static int POLL_INTERVAL = 1;
    private UltrasonicData ultrasonicData;
    private SensorDataChannel sensorDataChannel;
    private boolean stopRequested;

    public SensorDataReceiver() {
        sensorDataChannel = new SensorDataChannel();
    }

    public void setUltrasonicData(UltrasonicData ultrasonicData) {
        this.ultrasonicData = ultrasonicData;
    }

    public void run() {
        stopRequested = false;
        sensorDataChannel.subscribe();

        while(!stopRequested) {
            processMessage(sensorDataChannel.readMessage());
            try {
                Thread.sleep(POLL_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(new Runnable() {
            public void run() {
                ultrasonicData.resetLabels();
            }
        });
    }

    public void requestStop() {
        stopRequested = true;
    }

    private void processMessage(String message) {
        String[] elements = message.split(" ");

        if (elements.length != 5) {
            // Buffer got cut off on the sender side, silently drop this message.
            return;
        }

        updateLabels(Integer.parseInt(elements[0]),
                Integer.parseInt(elements[1]),
                Integer.parseInt(elements[2]),
                Integer.parseInt(elements[3]),
                Integer.parseInt(elements[4]));
    }

    private void updateLabels(int front, int back, int left, int right, int top) {
        Platform.runLater(new Runnable() {
            public void run() {
                ultrasonicData.setFrontValue(front);
                ultrasonicData.setBackValue(back);
                ultrasonicData.setLeftValue(left);
                ultrasonicData.setRightValue(right);
                ultrasonicData.setTopValue(top);
            }
        });
    }
}
