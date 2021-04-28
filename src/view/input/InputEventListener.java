package view.input;

import java.util.EventListener;

public interface InputEventListener extends EventListener {
    void onInputEvent(InputEvent event);
}
