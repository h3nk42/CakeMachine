package control.console;

import view.input.InputEvent;
import view.input.InputEventListener;

public class InputEventListenerConsole implements InputEventListener {

    @Override
    public void onInputEvent(InputEvent event) {
        if(null!=event.getText()&&event.getText().equals("exit"))
            System.exit(0);
    }
}
