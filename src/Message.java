public class Message {
    private String nickName;
    private String message;

    public String getRoomName() {
        return roomName;
    }

    private String roomName;

    public Message(String nickName, String message, String roomName) {
        this.nickName = nickName;
        this.message = message;
        this.roomName = roomName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMessage() {
        return message;
    }
}
