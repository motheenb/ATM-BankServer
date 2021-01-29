package org.bank.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    //
    private final int clientID;
    //
    private final Thread clientThread = new Thread(this);

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
        while (!socket.isClosed()) {

        }
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
}
