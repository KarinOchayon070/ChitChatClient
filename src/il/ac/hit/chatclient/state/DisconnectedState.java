/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (DisconnectedState.java) defines the DisconnectedState class, which is responsible for handling events and actions when
    the chat application is in the disconnected state. It implements the ConnectionState interface.
    - In the handleOnConnect method, it retrieves the server IP, port, and nickname from the input fields in the ChatPage instance.
      It validates the input, disables the input fields, creates a new TCP client, establishes a connection to the server, and updates the state to connected.
    - The handleOnDisconnect method does nothing because the chat application is already in the disconnected state.
    - The handleOnSendMessage method also does nothing because messages cannot be sent when the application is disconnected.
 */

package il.ac.hit.chatclient.state;
import il.ac.hit.chatclient.network.SimpleTCPClient;
import il.ac.hit.chatclient.view.ChatPage;
import java.io.IOException;
import javax.swing.*;

public class DisconnectedState implements ConnectionState {

    /**
     * Handles the connect event when the chat application is in the disconnected state
     * Retrieves the server IP, port, and nickname from the input fields, validates the input
     * disables the input fields, creates a new TCP client, establishes a connection to the server
     * and updates the state to connected
     *
     * @param chatPage The ChatPage instance
     */

    @Override
    public void handleOnConnect(ChatPage chatPage) {
        // Get the port, server, and nickname from the input fields
        int port = Integer.parseInt(chatPage.getPortField().getText());
        String server = chatPage.getServerField().getText();
        String nickName = chatPage.getNickNameField().getText();

        // Validate the input fields
        if (nickName.isEmpty() || server.isEmpty() || port < 0) {
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Must fill all fields!", " Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable the input fields for nickname, port, and server
        JTextField nickNameField = chatPage.getNickNameField();
        JTextField portField = chatPage.getPortField();
        JTextField serverField = chatPage.getServerField();
        nickNameField.setEnabled(false);
        portField.setEnabled(false);
        serverField.setEnabled(false);

        try {
            // Create a new TCP client and establish a connection
            chatPage.setTcpClient(new SimpleTCPClient(server, port, chatPage));

            // Display a success message
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Connected to server successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Change the state to connected and update the button state
            chatPage.changeState(new ConnectedState());
            chatPage.updateButtonState();

        } catch (IOException e) {
            // Display an error message if connection fails
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Failed to connect to the server.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the event when the Disconnect button is clicked in the ChatPage
     * Since the chat application is already in the disconnected state, no action is needed
     *
     * @param chatPage The ChatPage instance
     */
    @Override
    public void handleOnDisconnect(ChatPage chatPage) {
        // Already disconnected, do nothing
    }

    /**
     * Handles the event when the Send button is clicked in the ChatPage
     * Since the application is disconnected, messages cannot be sent, so no action is needed
     *
     * @param chatPage The ChatPage instance
     */
    @Override
    public void handleOnSendMessage(ChatPage chatPage) {
        // Cannot send message when disconnected, do nothing
    }
}
