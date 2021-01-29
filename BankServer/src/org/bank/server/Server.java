package org.bank.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Motheen Baig
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(Configs.SERVER_PORT);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {

        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void log(final Object o) {
        System.out.println(o);
    }

    public static void main(final String...args) {
        new Thread(new Server()).start();
    }

}
