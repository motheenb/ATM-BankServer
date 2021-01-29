package org.bank.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Motheen Baig
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private final ClientHandler clientHandler = new ClientHandler(this);

    public Server() {
        Server.log("Server listening on port: " + Configs.SERVER_PORT);
        try {
            serverSocket = new ServerSocket(Configs.SERVER_PORT);
        } catch (final IOException e) {
            Server.log("Error listening on port: " + Configs.SERVER_PORT);
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            clientHandler.acceptConnection(serverSocket);
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

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
