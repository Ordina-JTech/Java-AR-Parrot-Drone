package nl.ordina.jtech.arjava.communication;

import java.io.IOException;
import java.net.*;

public class SensorDataChannel {
    private static final int HOST_PORT = 33333;
    private static final String HOST_ADDRESS = "192.168.1.1";
    private DatagramSocket clientSocket;

    public SensorDataChannel() {
        try {
            clientSocket = new DatagramSocket(HOST_PORT);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

    // Subscribing works by sending a packet to the drone on the right port.
    public void subscribe() {
        byte[] data = "somebytes".getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length,
                    InetAddress.getByName(HOST_ADDRESS), HOST_PORT);
            clientSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Attempted to subscribe to ultrasonic data.");
    }

    public String readMessage() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.setSoTimeout(500);
            clientSocket.receive(receivePacket);
        } catch (IOException e) {
            if (!(e instanceof  SocketTimeoutException)) {
                e.printStackTrace();
            }
        }

        byte[] actualData = new byte[receivePacket.getLength()];
        System.arraycopy(receivePacket.getData(), receivePacket.getOffset(),
                actualData, 0, receivePacket.getLength());
        return new String(actualData);
    }
}
