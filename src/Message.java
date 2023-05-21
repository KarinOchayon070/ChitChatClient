public class Message {
    private String nickName;
    private String message;

    public Message(String nickName, String message) {
        this.nickName = nickName;
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMessage() {
        return message;
    }
}
