/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import java.io.IOException;
import javax.swing.*;

public class DisconnectedState implements ConnectionState {
    @Override
    public void handleOnConnect(ChatPage chatPage) {
        int port = Integer.parseInt(chatPage.getPortField().getText());
        String server = chatPage.getServerField().getText();
        String nickName = chatPage.getNickNameField().getText();

        if (nickName.isEmpty() || server.isEmpty() || port < 0) {
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Must fill all fields!", " Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nickNameField = chatPage.getNickNameField();
        JTextField portField = chatPage.getPortField();
        JTextField serverField = chatPage.getServerField();
        nickNameField.setEnabled(false);
        portField.setEnabled(false);
        serverField.setEnabled(false);


        try {
            chatPage.setTcpClient(new SimpleTCPClient(server, port, chatPage));

            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Connected to server successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            chatPage.changeState(new ConnectedState());
            chatPage.updateButtonState();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Failed to connect to the server.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleOnDisconnect(ChatPage chatPage) {
        // Already disconnected, do nothing
    }

    @Override
    public void handleOnSendMessage(ChatPage chatPage) {
        // Cannot send message when disconnected, do nothing

    }
}
