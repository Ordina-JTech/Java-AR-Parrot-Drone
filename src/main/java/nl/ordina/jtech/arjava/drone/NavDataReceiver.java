package nl.ordina.jtech.arjava.drone;

import nl.ordina.jtech.arjava.communication.NavDataChannel;

public class NavDataReceiver implements Runnable {
    private static int POLL_INTERVAL = 100;
    private NavDataChannel navDataChannel;
    private boolean stopRequested;

    public NavDataReceiver() {
        navDataChannel = new NavDataChannel();
    }

    @Override
    public void run() {
        stopRequested = false;
        navDataChannel.initiateNavDataReception();

        while(!stopRequested) {
            byte[] navData = navDataChannel.readNavData();
            // System.out.println("Received: " + new String(navData));

            try {
                Thread.sleep(POLL_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestStop() {
        stopRequested = true;
    }
}
