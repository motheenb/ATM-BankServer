package org.bank.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    //
    private final int clientID;
    //
    private final Thread clientThread = new Thread(this);
    //
    private TellerStatus Status = TellerStatus.Ready;

    private enum TellerStatus {
        Ready, WaitingForCard, CardConfirmed, CardFailed, WaitingForPIN, PINConfirmed, PINFailed
    }

    public Client(final int clientID, final Socket socket) {
        this.clientID = clientID;
        this.socket = socket;
        try {
            inputStream = new DataInputStream(this.socket.getInputStream());
            outputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int messageSize;
        String clientMessage;
        byte messageBytes[];
        while (isConnected()) {
            try {
                if (inputStream.available() > -1) {
                    messageSize = inputStream.readInt();
                    if (messageSize > -1) {
                        messageBytes = new byte[messageSize];
                        inputStream.readFully(messageBytes, 0, messageSize);
                        clientMessage = new String(messageBytes);
                        handleClientMessages(clientMessage);
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleClientMessages(final String clientMessage) {
        String data[] = clientMessage.split(":");
        String responseMessage;
        byte responseBytes[];
        final int requestID = Integer.parseInt(data[0]);
        Server.log("Client ID: " + clientID + ", Client Request ID: " + requestID + ", Client Message: " + clientMessage);
        switch (requestID) {
            case 0 -> { // Let ATM know server has accepted its connection and is ready.
                if (Status.equals(TellerStatus.Ready)) {
                    responseMessage = "0:type=ATM:id=" + clientID + ":status=ready";
                    writeToATM(responseMessage);
                    Status = TellerStatus.WaitingForCard;
                } else {
                    // request ATM to force-reset
                }
            }
            case 1 -> {
                if (Status.equals(TellerStatus.WaitingForCard)) {
                    final long cardNumber = Long.parseLong(data[3].split("=")[1]);
                    // Check to see if cardNumber is blocked, exists, valid, etc...
                    final String cardStatus = BankDBManager.clientCardExists(cardNumber) ? "card_ok" : "card_failed";
                    if (cardStatus.equalsIgnoreCase("card_ok")) {
                        Status = TellerStatus.WaitingForPIN;
                    } else {
                        Status = TellerStatus.WaitingForCard;
                    }
                    responseMessage = "1:type=ATM:id=" + clientID + ":status=" + cardStatus;
                    writeToATM(responseMessage);
                } else {
                    // request ATM to force-reset
                }
            }
            case 2 -> {
                if (Status.equals(TellerStatus.WaitingForPIN)) {
                    final long cardNumber = Long.parseLong(data[3].split("=")[1]);
                    final int cardPIN = Integer.parseInt(data[4].split("=")[1]);
                    Server.log("Card Number: " + cardNumber + ", CardPIN: " + cardPIN);
                    responseMessage = "2:type=ATM:id=" + clientID + ":status=card_pin_ok:" + BankDBManager.getCustomerInfo(cardNumber);
                    writeToATM(responseMessage);
                    Status = TellerStatus.PINConfirmed;
                }
            }
        }
    }

    public void writeToATM(final String responseMessage) {
        final byte responseBytes[] = responseMessage.getBytes();
        try {
            outputStream.writeInt(responseBytes.length);
            outputStream.write(responseBytes, 0, responseBytes.length);
            outputStream.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return !socket.isClosed();
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

    public int getClientID() {
        return clientID;
    }

    public Thread getClientThread() {
        return clientThread;
    }

    public TellerStatus getStatus() {
        return Status;
    }
}
