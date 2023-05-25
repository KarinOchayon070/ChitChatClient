/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import javax.swing.*;
import java.io.*;
import com.google.gson.Gson;

public class SimpleTCPClient {
    private ConnectionProxy connectionProxy;
    private ChatPage chatPage;
    private Gson gson = new Gson();

    SimpleTCPClient(String serverIP, int port, ChatPage chatPage) throws IOException {
        this.chatPage = chatPage;
        this.connectionProxy = new ConnectionProxy(serverIP, port);

        Message connectionMessage = new Message(chatPage.getNickNameField().getText(),
                "Has joined the chat",
                "global");

        this.sendMessage(connectionMessage);
        this.startReceivingMessages();
    }

    public void sendMessage(Message message) {
        String gson = this.gson.toJson(message);
        this.connectionProxy.sendMessage(gson);
    }

    public void startReceivingMessages() {
        Thread receivingThread = new Thread(() -> {
            try {
                String json;
                while (this.connectionProxy.isConnected() && (json = this.connectionProxy.receiveMessage()) != null) {
                    Message message = gson.fromJson(json, Message.class);

                    SwingUtilities.invokeLater(() -> {
                        chatPage.renderMessage(message, false);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receivingThread.start();
    }

    public void close() throws IOException {
        this.connectionProxy.close();
    }
}