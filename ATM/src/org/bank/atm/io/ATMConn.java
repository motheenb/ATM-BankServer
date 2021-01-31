package org.bank.atm.io;

import org.bank.atm.ATM;
import org.bank.atm.misc.Console;
import org.bank.atm.State;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Motheen Baig
 */
public class ATMConn implements Runnable {

    private final ATM atm;
    private final Thread connThread = new Thread(this);
    //
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    //
    private Encryption encryption;

    public ATMConn(final ATM atm) {
        this.atm = atm;
        Console.log("Connecting to server.");
        try {
            socket = new Socket("127.0.0.1", 43594);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            encryption = new Encryption();
            connThread.start();
        } catch (final IOException e) {
            Console.log("Error connecting to server.");
        }
    }

    @Override
    public void run() {
        int messageSize;
        byte messageBytes[], plainBytes[];
        String serverMessage;
        writeToServer("0:type=ATM:id=-1");
        Console.log("Sent ATM connect request to server.");
        while (isConnected()) {
            try {
                if (inputStream.available() > -1) {
                    messageSize = getInputStream().readInt();
                    if (messageSize > -1) {
                        messageBytes = new byte[messageSize];
                        inputStream.readFully(messageBytes, 0, messageSize);
                        // decrypt messageBytes[]
                        plainBytes = encryption.RSADecrypt(messageBytes);
                        serverMessage = new String(plainBytes);
                        handleServerMessages(serverMessage);
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleServerMessages(final String serverMessage) {
        int clientID;
        final String data[] = serverMessage.split(":");
        final int requestID = Integer.parseInt(data[0]);
        final String clientStatus = data[3].split("=")[1];
        Console.log("Server Request ID: " + requestID + ", Server Message: " + serverMessage);
        switch (requestID) {
            case 0 -> {
                clientID = Integer.parseInt(data[2].split("=")[1]);
                Console.log("Client ID: " + clientID + ", Client Status: " + clientStatus);
                if (clientStatus.equalsIgnoreCase("ready")) {
                    ATM.STATE = State.EnterCard;
                }
            }
            case 1 -> {
                clientID = Integer.parseInt(data[2].split("=")[1]);
                if (clientID == atm.getClientID()) {
                    if (clientStatus.equalsIgnoreCase("card_ok")) {
                        ATM.STATE = State.EnterPIN;
                    } else if (clientStatus.equalsIgnoreCase("card_failed")) {
                        atm.setErrorMessage("Card number is invalid/blocked, contact customer support.");
                    }
                } else {
                    // ClientID received does not match up with current ClientID.
                }
            }
            case 2 -> {
                if (clientStatus.equalsIgnoreCase("card_pin_ok")) {
                    final String balance = data[4].split("=")[1];
                    final String fName = data[5].split("=")[1];
                    final String lName = data[6].split("=")[1];
                    atm.setBalance(Float.parseFloat(balance));
                    atm.setfName(fName);
                    atm.setlName(lName);
                    ATM.STATE = State.MainMenu;
                } else if (clientStatus.equalsIgnoreCase("card_pin_failed")) {
                    atm.setErrorMessage("Card PIN is invalid, contact customer support.");
                    // Pin retries
                }
            }
        }
    }

    public void writeToServer(final String clientMessage) {
        final byte cryptoBytes[] = encryption.RSAEncrypt(clientMessage.getBytes());
        try {
            outputStream.writeInt(cryptoBytes.length);
            outputStream.write(cryptoBytes, 0, cryptoBytes.length);
            outputStream.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }

    public ATM getAtm() {
        return atm;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public Thread getConnThread() {
        return connThread;
    }

    public Encryption getEncryption() {
        return encryption;
    }
}
