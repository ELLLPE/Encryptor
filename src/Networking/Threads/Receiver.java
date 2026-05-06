package Networking.Threads;

import java.io.BufferedReader;
import java.io.IOException;

import Networking.DisconnectListener;
import Networking.MessageListener;

public class Receiver implements Runnable {
    private DisconnectListener disconnect;
    private MessageListener listener;
    private BufferedReader reader;

    public Receiver(MessageListener listener, DisconnectListener disconnect, BufferedReader reader) {
        this.disconnect = disconnect;
        this.listener = listener;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                listener.messageReceived(msg);
            }

            disconnect.disconnected();
        } catch (IOException e) {
            disconnect.disconnected();
        }
    }

}
