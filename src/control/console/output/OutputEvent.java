package control.console.output;

import java.util.EventObject;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputEvent that = (OutputEvent) o;
        return Objects.equals(text, that.text) && messageType == that.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, messageType);
    }
}
