package org.bank.atm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ATMConn implements Runnable {

    private final ATM atm;
    private final Thread connThread = new Thread(this);
    //
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public ATMConn(final ATM atm) {
        this.atm = atm;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {

        }
    }

    public void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 43594);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            connThread.start();
        } catch (final IOException e) {
            e.printStackTrace();
        }
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
}
