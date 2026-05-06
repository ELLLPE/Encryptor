package Networking.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Networking.Threads.Receiver;
import Networking.Threads.Sender;

public class Client {
    private boolean connected = false;

    // Initialize socket and input/output streams
    private Socket socket = null;

    private BufferedReader reader = null;
    private PrintWriter writer = null;
    private BufferedReader console = null;

    public Client(String addr, int port) {
        // Establish a connection
        try {
            socket = new Socket(addr, port);
            System.out.println("Connected");
            connected = true;

            console = new BufferedReader(new InputStreamReader(System.in));

            // Setup for reader
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Setup for writer
            writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        // Reader
        Receiver receiver = new Receiver(
                msg -> {
                    System.out.println("Received: " + msg);
                },
                () -> {
                    System.out.println("Disconnected");
                    connected = false;
                },
                reader);

        new Thread(receiver).start();

        // Writer
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Sender sender = new Sender(writer, queue);
        new Thread(sender).start();

        try {
            while (connected)
                queue.add(console.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 5000);
    }
}
