package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ServerSocket serverSocket;
    private Socket socket;
    public  ArrayList<ServerHandle> workers = new ArrayList<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void serverConnect() throws IOException {
        while (true) {
            socket = serverSocket.accept();
            ServerHandle worker = new ServerHandle(socket);
            worker.server = this;
            worker.start();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(1402);
            server.serverConnect();
        } catch (IOException e) {
        }
    }
}

