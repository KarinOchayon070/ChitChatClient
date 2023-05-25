import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectionProxy {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatPage chatPage;
    private Gson gson = new Gson();

    ConnectionProxy(Socket socket, ChatPage chatPage) throws IOException {
        this.chatPage = chatPage;
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        Message connectionMessage = new Message(chatPage.getNickNameField().getText(),
                "Has joined the chat",
                "global");

        this.sendMessage(connectionMessage);
        this.startReceivingMessages();
    }


    public void sendMessage(Message message) {
        String gson = this.gson.toJson(message);
        this.out.println(gson);
    }

    public void startReceivingMessages() {
        Thread receivingThread = new Thread(() -> {
            try {
                String json;
                while (this.socket.isConnected() && (json = receiveMessage()) != null) {
                    Message message = gson.fromJson(json, Message.class);

                    SwingUtilities.invokeLater(() -> {
                        chatPage.renderMessage(message,false);

                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receivingThread.start();
    }

    public String receiveMessage() throws IOException {
        return this.in.readLine();
    }

    public void close() throws IOException {
        this.socket.close();
        this.in.close();
        this.out.close();
    }
}
