import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;



public class SimpleTCPClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JPanel chatContainer;
    private Gson gson = new Gson();

    SimpleTCPClient(String serverIP, int port, ChatPage chatPage) throws IOException {
        this.chatContainer = chatPage.getChatContainer();
        this.socket = new Socket(serverIP, port);
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
                        JTextArea textArea = new JTextArea(message.getNickName() + ":" + message.getMessage());
                        textArea.setBackground(Color.CYAN);
                        textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

                        chatContainer.add(textArea);
                        chatContainer.revalidate();
                        chatContainer.repaint();
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
