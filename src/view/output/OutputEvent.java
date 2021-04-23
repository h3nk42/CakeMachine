package view.output;

import java.util.EventObject;

public class OutputEvent extends EventObject {
    private String text;
    private MessageType messageType;

    public OutputEvent(Object source, String text, MessageType messageType) {
        super(source);
        this.text=text;
        this.messageType = messageType;
    }

    public String getText(){
        return this.text;
    }
    public MessageType getType(){
        return this.messageType;
    }
}
