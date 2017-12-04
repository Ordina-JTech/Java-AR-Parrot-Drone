package nl.ordina.jtech.arjava.AI;

import nl.ordina.jtech.arjava.drone.RemoteController;

public class FlightPath implements Runnable {
    private RemoteController remoteController;

    public FlightPath(RemoteController remoteController) {
        this.remoteController = remoteController;
    }

    @Override
    public void run() {
        // Flight path sequence here
        remoteController.takeOff();
        wait(5000);
        remoteController.setRequestedPitch(-0.1F);
        wait(3000);
        remoteController.setRequestedPitch(0);
        wait(1000);
        remoteController.land();
        remoteController.disable();
    }

    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
