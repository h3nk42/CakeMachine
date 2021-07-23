package control.console.input;

import java.util.EventObject;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputEvent that = (InputEvent) o;
        return Objects.equals(text, that.text) && inputEventType == that.inputEventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, inputEventType);
    }
}
