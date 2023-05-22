public interface ConnectionState {
    void handleOnConnect(ChatPage chatPage);

    void handleOnDisconnect(ChatPage chatPage);

    void handleOnSendMessage(ChatPage chatPage);
}