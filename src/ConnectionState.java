/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/
public interface ConnectionState {
    void handleOnConnect(ChatPage chatPage);

    void handleOnDisconnect(ChatPage chatPage);

    void handleOnSendMessage(ChatPage chatPage);
}
