package view.input;

import java.util.EventObject;

public class InputEvent extends EventObject {
    private String text;
    private InputEventType inputEventType;
    public InputEvent(Object source, String text, InputEventType inputEventType) {
        super(source);
        this.inputEventType = inputEventType;
        this.text=text;
    }
    public String getText(){
        return this.text;
    }

    public InputEventType getInputEventType() {
        return inputEventType;
    }
}
