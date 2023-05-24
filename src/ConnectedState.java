import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class ConnectedState implements ConnectionState {
    @Override
    public void handleOnConnect(ChatPage chatPage) {
        // Already connected, do nothing


    }

    @Override
    public void handleOnDisconnect(ChatPage chatPage) {

        JTextField nickNameField = chatPage.getNickNameField();
        JTextField portField = chatPage.getPortField();
        JTextField serverField = chatPage.getServerField();
        nickNameField.setEnabled(true);
        portField.setEnabled(true);
        serverField.setEnabled(true);

        try{
            chatPage.getTcpClient().close();
            chatPage.changeState(new DisconnectedState());
            chatPage.updateButtonState();
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(chatPage.getMainFrame(), "Failed to disconnect from the server.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void handleOnSendMessage(ChatPage chatPage) {
        String nickName = chatPage.getNickNameField().getText();
        String userInput = chatPage.getUserInputField().getText();
        String roomName = chatPage.getRecipientInputField().getText();

        Message message = new Message(nickName, userInput, roomName);
        chatPage.getTcpClient().sendMessage(message);

        JTextArea textArea = new JTextArea();
        textArea.setText(message.getNickName() + ": " + message.getMessage());
        textArea.setEditable(false);
        textArea.setBackground(Color.GREEN);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


        chatPage.getChatContainer().add(textArea);
        chatPage.getChatContainer().revalidate();
        chatPage.getChatContainer().repaint();
    }
}
