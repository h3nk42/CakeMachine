package view.console;


import control.console.input.InputEvent;
import control.console.input.InputEventHandler;
import control.console.input.InputEventType;

import java.util.Scanner;

public class Reader {
    private final Input input;
    private InputEventHandler inputEventHandler;
    private boolean runAsLoop;
    private boolean isFirst = true;

    public Reader(InputEventHandler inputEventHandler, boolean runAsLoop) {
        this.inputEventHandler = inputEventHandler;
        this.runAsLoop = runAsLoop;
        this.input = new Input();
    }

    public void start(){
        boolean run = true;
        try(Scanner s=new Scanner(System.in)){
            do {
                if(isFirst) {
                    InputEvent print = new InputEvent(this, "", InputEventType.print);
                    inputEventHandler.handle(print);
                    isFirst = false;
                }
                /* HANDLE NEXT INPUT */
                String inPutText = this.awaitInput();
                InputEvent handleInput =new InputEvent(this,inPutText,InputEventType.read);
                inputEventHandler.handle(handleInput);
                if(!runAsLoop) run = false;
            }while (run);
        }
    }

    private String awaitInput() {
        String inText = input.readInput();
        return inText;
    }
}
