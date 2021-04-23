package view.consoleReader;

import control.console.Console;
import control.console.ConsoleState;

public class InputEventListenerConstant implements InputEventListener {
    private Console c;

    public InputEventListenerConstant(Console c) {
        this.c = c;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String text = event.getText().toLowerCase();
            switch (text) {
                case "exit":
                    System.exit(0);
                    break;
                case "b":
                    if(c.getState() == ConsoleState.none) {
                        System.out.println("\u001B[31m" + "\n --- es geht nicht weiter zurück! --- \n" + "\u001B[0m");
                    } else {
                        //c.changeState();
                    }
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "constant";
    }
}
