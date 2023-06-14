/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (ConnectionState.java) provided code represents an interface called ConnectionState which defines the behavior for
    handling different connection states in a chat application.
    This interface declares three methods:
    - handleOnConnect: This method handles the "Connect" event and is responsible for performing the necessary actions when a connection is established.
    - handleOnDisconnect: This method handles the "Disconnect" event and defines the actions to be taken when the connection is terminated.
    - handleOnSendMessage: This method handles the "Send Message" event and specifies the behavior for sending a message within the established connection.
    Overall, this interface serves as a blueprint for implementing different connection states and their corresponding actions in a chat application.
 */

package il.ac.hit.chatclient.state;
import il.ac.hit.chatclient.view.ChatPage;

/**
 This interface represents the various connection states in a chat application
 */
public interface ConnectionState {

    /**
     Handles the "Connect" event
     @param chatPage The ChatPage instance
     */
    void handleOnConnect(ChatPage chatPage);
    /**
     Handles the "Disconnect" event
     @param chatPage The ChatPage instance
     */
    void handleOnDisconnect(ChatPage chatPage);
    /**
     Handles the "Send Message" event
     @param chatPage The ChatPage instance
     */
    void handleOnSendMessage(ChatPage chatPage);
}