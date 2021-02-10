package org.bank.server.clients;

import org.bank.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Motheen Baig
 */
public class ClientHandler {

    public static Client clients[] = new Client[10];

    public static void acceptConnection(final ServerSocket serverSocket) {
        int freeID;
        Socket incoming;
        try {
            incoming = serverSocket.accept();
            freeID = getFreeID();
            Server.log("Accepted connection: " + freeID + ", from: " + incoming.getInetAddress());
            clients[freeID] = new Client(freeID, incoming);
            clients[freeID].getClientThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeConnection(final int clientID) {
        if (clients[clientID] != null){
            clients[clientID] = null;
        }
        return clients[clientID] == null ? true : false;
    }

    private static int getFreeID() {
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                return i;
            }
        }
        return -1;
    }


    public Client[] getClients() {
        return clients;
    }
}
