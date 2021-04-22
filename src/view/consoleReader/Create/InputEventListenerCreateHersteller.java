package view.consoleReader.Create;

import control.console.Console;
import view.consoleReader.InputEvent;
import view.consoleReader.InputEventListener;

public class InputEventListenerCreateHersteller implements InputEventListener {

    private Console c;

    public InputEventListenerCreateHersteller(Console c) {
        this.c = c;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if(null!=event.getText()) {
            String text = event.getText();
           /* StringBuilder sb = new StringBuilder(text);
            sb.*/
            String[] splitText = text.split(" \\s+");
            try {
                c.getAutomat().createHersteller(splitText[0]);
                System.out.println("view/consoleReader/Create/InputEventListenerCreateHersteller.java: lineNumber: 24: " + c.getAutomat().getHersteller());
            } catch (Exception e) {
                System.out.println("\u001B[31m \n --- " + e.getMessage() + " --- \n \u001B[0m");
            }
        }
    }

    @Override
    public String toString() {
        return "create";
    }
}
