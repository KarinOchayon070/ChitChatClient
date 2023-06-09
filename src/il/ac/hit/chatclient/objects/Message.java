/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (Message.java) defines a class called Message that represents a message object.
    It has three private fields: nickName for the sender's nickname, message for the content of the message, and recipient for the recipient of the message.
    The class provides a constructor to initialize these fields and getter methods to retrieve the values.
    Essentially, this code provides a data structure to store and access information about a message in a chat application.
 */

package il.ac.hit.chatclient.objects;
public class Message {

    // The private field to store the nickname of the sender
    private String nickName;

    // The private field to store the content of the message
    private String message;

    // The private field to store the recipient of the message
    private String recipient;

    /**
     * Constructor for creating a Message object with the specified nickname, message, and recipient
     * @param nickName The nickname of the sender
     * @param message The content of the message
     * @param recipient The recipient of the message
     */
    public Message(String nickName, String message, String recipient) {
        this.nickName = nickName;
        this.message = message;
        this.recipient = recipient;
    }

    /**
     * Retrieves the recipient of the message
     * @return The recipient of the message
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Retrieves the nickname of the sender
     * @return The nickname of the sender
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Retrieves the content of the message
     * @return The content of the message
     */
    public String getMessage() {
        return message;
    }

}
