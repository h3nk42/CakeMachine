package view.consoleReader.Create;

import control.console.Console;
import control.console.ConsoleState;
import view.consoleReader.InputEvent;
import view.consoleReader.InputEventListener;

public class InputEventListenerCreate implements InputEventListener {

    private Console c;

    public InputEventListenerCreate(Console c) {
        this.c = c;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String text = event.getText().toLowerCase();
            switch (text) {
                case "h":
                    c.changeState(ConsoleState.ch);
                    break;
                case "k":
                    c.changeState(ConsoleState.ck);
                    break;

            }
        }
    }

    @Override
    public String toString() {
        return "create";
    }
}
