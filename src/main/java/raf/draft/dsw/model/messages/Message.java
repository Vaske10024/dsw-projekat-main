package raf.draft.dsw.model.messages;

import java.time.LocalDate;

public class Message {
    private String content;
    private MessageType type;
    private long timestamp;


    public Message(String content, MessageType type, long timestamp) {
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "[" + type + "][" + timestamp + "] " + content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
