import java.io.IOException;
import javax.swing.JOptionPane;

public class DisconnectedState implements ConnectionState {
    @Override
    public void handleOnConnect(ChatPage chatPage) {
        if (chatPage.getNickNameField().getText().isEmpty() || chatPage.getServerField().getText().isEmpty()
                || chatPage.getPortField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Must fill all fields!", " Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int port = Integer.parseInt(chatPage.getPortField().getText());
        String server = chatPage.getServerField().getText();

        try {
            chatPage.setTcpClient(new SimpleTCPClient(server, port, chatPage.getChatContainer()));
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
