package nl.ordina.jtech.arjava.communication;

import nl.ordina.jtech.arjava.commands.ATCommand;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CommandChannel {
    private static final int HOST_PORT = 5556;
    private static final String HOST_ADDRESS = "192.168.1.1";
    private DatagramSocket clientSocket;
    private long sequenceNumber;

    public CommandChannel() {
        sequenceNumber = 1;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendCommand(ATCommand command) {
        command.setSequenceNumber(getNextSequenceNumber());
        byte[] data = command.getCommandString().getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length,
                    InetAddress.getByName(HOST_ADDRESS), HOST_PORT);
            clientSocket.send(packet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("Sent: " + command.getCommandString());
    }

    private long getNextSequenceNumber() {
        return sequenceNumber++;
    }

    public void resetSequenceNumber() {
        sequenceNumber = 1;
    }
}
