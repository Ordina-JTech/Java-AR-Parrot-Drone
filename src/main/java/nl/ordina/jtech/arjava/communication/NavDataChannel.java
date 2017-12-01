package nl.ordina.jtech.arjava.communication;

import java.io.IOException;
import java.net.*;

public class NavDataChannel {
    private static final int HOST_PORT = 5554;
    private static final int CLIENT_PORT = 5554;
    private static final String HOST_ADDRESS = "192.168.1.1";
    private static final String MULTICAST_ADDRESS = "224.1.1.1";
    private static final int bufferSize = 1024;
    private MulticastSocket clientSocket;

    public NavDataChannel() {
        try {
            clientSocket = new MulticastSocket(CLIENT_PORT);
            clientSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initiateNavDataReception() {
        byte[] data = new byte[] {0x01, 0x00, 0x00, 0x00};

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length,
                    InetAddress.getByName(HOST_ADDRESS), HOST_PORT);
            clientSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readNavData() {
        byte[] receiveData = new byte[bufferSize];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.setSoTimeout(500);
            clientSocket.receive(receivePacket);
        } catch (IOException e) {
            if (!(e instanceof SocketTimeoutException)) {
                e.printStackTrace();
            }
        }

        byte[] actualData = new byte[receivePacket.getLength()];
        System.arraycopy(receivePacket.getData(), receivePacket.getOffset(),
                actualData, 0, receivePacket.getLength());
        return actualData;
    }
}
