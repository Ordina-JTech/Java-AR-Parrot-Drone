package nl.ordina.jtech.arjava.communication;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class VideoChannel
{
    private static final int HOST_PORT = 5555;
    private static final String HOST_ADDRESS = "192.168.1.1";
    private boolean connectedToDrone;
    private Socket clientSocket;

    public VideoChannel() {
        connectedToDrone = false;
    }

    public boolean isConnectedToDrone() {
        return connectedToDrone;
    }

    public void connectToDrone() {
        try {
            clientSocket = new Socket(HOST_ADDRESS, HOST_PORT);
            connectedToDrone = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnectFromDrone() {
        try {
            clientSocket.close();
            connectedToDrone = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public InputStream getSocketInputStream() {
        try {
            return clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
