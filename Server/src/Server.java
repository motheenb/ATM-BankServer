import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Motheen Baig
 */
public class Server {

    private final int port = 43594;

    public Server() {
        Runnable listener;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            listener = new Runnable() {
                @Override
                public void run() {
                    while (!serverSocket.isClosed()) {

                    }
                }
            };
            listener.run();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String...args) {
        new Server();
    }

}
