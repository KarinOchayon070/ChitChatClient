/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/
public class Message {
    private String nickName;
    private String message;
    private String recipient;

    public Message(String nickName, String message, String recipient) {
        this.nickName = nickName;
        this.message = message;
        this.recipient = recipient;
    }


    public String getRecipient() {
        return recipient;
    }
    public String getNickName() {
        return nickName;
    }
    public String getMessage() {
        return message;
    }

}
