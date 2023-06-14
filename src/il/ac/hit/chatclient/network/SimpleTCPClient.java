package il.ac.hit.chatclient.network;/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (il.ac.hit.chatclient.network.SimpleTCPClient.java) defines a il.ac.hit.chatclient.network.SimpleTCPClient class responsible for establishing a TCP connection with a server and exchanging messages.
    It utilizes the il.ac.hit.chatclient.network.ConnectionProxy class for managing the low-level socket operations.
    The class allows sending messages to the server, receiving messages asynchronously in a separate thread, and closing the connection.
    It uses the Gson library for converting messages to JSON format and vice versa.
    The received messages are rendered in the chat interface using SwingUtilities.invokeLater().
 */

import javax.swing.*;
import java.io.*;
import com.google.gson.Gson;
import il.ac.hit.chatclient.view.ChatPage;
import il.ac.hit.chatclient.objects.Message;

public class SimpleTCPClient {

    // Instance of the il.ac.hit.chatclient.network.ConnectionProxy class for managing the TCP connection
    private ConnectionProxy connectionProxy;

    // Instance of the il.ac.hit.chatclient.client.ChatPage class for accessing the chat interface
    private ChatPage chatPage;

    // Gson object for converting messages to JSON format and vice versa
    private Gson gson = new Gson();

    /**
     * Constructs a il.ac.hit.chatclient.network.SimpleTCPClient object and establishes a connection with the server
     *
     * @param serverIP  The server IP address
     * @param port      The port number
     * @param chatPage  The il.ac.hit.chatclient.client.ChatPage instance
     * @throws IOException If an I/O error occurs when creating the connection
     */
    public SimpleTCPClient(String serverIP, int port, ChatPage chatPage) throws IOException {

        // Assigns the provided il.ac.hit.chatclient.client.ChatPage instance to the member variable chatPage
        this.chatPage = chatPage;

        // Creates a new il.ac.hit.chatclient.network.ConnectionProxy object using the provided server IP and port
        this.connectionProxy = new ConnectionProxy(serverIP, port);

        // Creates a new il.ac.hit.chatclient.common.Message object representing the connection message, with the nickname, a predefined joining message, and recipient set to "global"
        Message connectionMessage = new Message(chatPage.getNickNameField().getText(),
                "Has joined the chat",
                "global");

        // Sends the connection message to the server using the sendMessage() method of the il.ac.hit.chatclient.network.ConnectionProxy
        this.sendMessage(connectionMessage);

        // Starts a new thread for receiving messages from the server using the startReceivingMessages() method
        this.startReceivingMessages();
    }

    /**
     * Sends a message to the server
     *
     * @param message The message to be sent
     */
    public void sendMessage(Message message) {

        // Converts the provided il.ac.hit.chatclient.common.Message object to its JSON representation using the Gson library, and assigns it to the variable gson
        String gson = this.gson.toJson(message);

        // Sends the JSON message to the server using the sendMessage() method of the il.ac.hit.chatclient.network.ConnectionProxy
        this.connectionProxy.sendMessage(gson);
    }

    /**
     * Starts a thread to continuously receive messages from the server
     */
    public void startReceivingMessages() {
        Thread receivingThread = new Thread(() -> {
            try {
                String json;
                while (this.connectionProxy.isConnected() && (json = this.connectionProxy.receiveMessage()) != null) {
                    // Deserializes the received JSON message into a il.ac.hit.chatclient.common.Message object using the Gson library
                    Message message = gson.fromJson(json, Message.class);
                    System.out.println(message);

                    SwingUtilities.invokeLater(() -> {
                        // Renders the received message in the chat interface by invoking the renderMessage() method of the chatPage instance
                        chatPage.renderMessage(message, false);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Starts the receivingThread, which continuously listens for incoming messages from the server and renders them in the chat interface
        receivingThread.start();
    }

    /**
     * Closes the connection with the server
     *
     * @throws IOException If an I/O error occurs when closing the connection
     */
    public void close() throws IOException {
        this.connectionProxy.close();
    }
}