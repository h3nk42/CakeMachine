package view.consoleReader;

import control.console.Console;
import control.console.ConsoleState;

public class InputEventListenerMode implements InputEventListener {

    private Console c;

    public InputEventListenerMode(Console c) {
        this.c = c;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String text = event.getText().toLowerCase();
            switch (text) {
                /*case "c":
                    c.changeState(ConsoleState.c);
                    break;
                case "r":
                    c.changeState(ConsoleState.r);
                    break;
                case "u":
                    c.changeState(ConsoleState.u);
                    break;
                case "d":
                    c.changeState(ConsoleState.d);
                    break;
                case "p":
                    c.changeState(ConsoleState.p);
                    break;
                case "conf":
                    c.changeState(ConsoleState.config);
                    break;*/
            }
        }
    }

    @Override
    public String toString() {
        return "mode";
    }
}
