package il.ac.hit.chatclient.state;/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (il.ac.hit.chatclient.state.ConnectedState.java) represents the behavior of the chat application when it is in the connected state.
    It handles the events related to connecting, disconnecting, and sending messages.
    - The handleOnConnect method does nothing as the client is already connected.
    - The handleOnDisconnect method enables the necessary input fields, closes the TCP connection, changes the state to disconnected,
      and updates the button state. It displays an error message if disconnection fails.
    - The handleOnSendMessage method retrieves the nickname, user input, and recipient from the input fields, creates a message object,
      sends the message via the TCP client, clears the user input field, and renders the message in the chat interface.
*/

import il.ac.hit.chatclient.view.ChatPage;
import il.ac.hit.chatclient.objects.Message;

import java.io.IOException;
import javax.swing.*;

public class ConnectedState implements ConnectionState {

    /**
     * Handles the "Connect" event in the connected state
     * This method does nothing as the client is already connected
     *
     * @param chatPage The il.ac.hit.chatclient.client.ChatPage instance.
     */
    @Override
    public void handleOnConnect(ChatPage chatPage) {
        // Already connected, do nothing
    }

    /**
     * Handles the "Disconnect" event in the connected state
     * Enables the necessary input fields, closes the TCP connection
     * changes the state to disconnected, and updates the button state
     * Displays an error message if disconnection fails
     *
     * @param chatPage The il.ac.hit.chatclient.client.ChatPage instance.
     */
    @Override
    public void handleOnDisconnect(ChatPage chatPage) {

        // Get the nickNameField, portField, serverField
        JTextField nickNameField = chatPage.getNickNameField();
        JTextField portField = chatPage.getPortField();
        JTextField serverField = chatPage.getServerField();

        // Enable the input fields for nickname, port, and server
        nickNameField.setEnabled(true);
        portField.setEnabled(true);
        serverField.setEnabled(true);

        try {
            // Close the TCP connection
            chatPage.getTcpClient().close();

            // Change the state to disconnected
            chatPage.changeState(new DisconnectedState());

            // Update the button state
            chatPage.updateButtonState();
        } catch (IOException e) {
            // Display an error message if disconnection fails
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Failed to disconnect from the server.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the "Send il.ac.hit.chatclient.common.Message" event in the connected state
     * Retrieves the nickname, user input, and recipient from the input fields
     * creates a message object, sends the message via the TCP client
     * clears the user input field, and renders the message in the chat interface
     *
     * @param chatPage The il.ac.hit.chatclient.client.ChatPage instance.
     */
    @Override
    public void handleOnSendMessage(ChatPage chatPage) {
        // Get the nickNameField, portField, serverField
        String nickName = chatPage.getNickNameField().getText();
        String userInput = chatPage.getUserInputField().getText();
        String recipient = chatPage.getRecipientInputField().getText();

        // Create a new message object with the provided nickname, user input, and recipient
        Message message = new Message(nickName, userInput, recipient);

        // Send the message through the TCP client
        chatPage.getTcpClient().sendMessage(message);

        // Clear the user input field
        chatPage.getUserInputField().setText("");

        // Render the message in the chat interface, indicating that it was sent by the user (true)
        chatPage.renderMessage(message, true);
    }
}
