package control.console.input;


import java.util.Scanner;

public class Reader {
    private final Input input;
    private InputEventHandler inputEventHandler;
    private boolean isFirst = true;

    public Reader(InputEventHandler inputEventHandler) {
        this.inputEventHandler = inputEventHandler;
        this.input = new Input();
    }

    public void start(){
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
            }while (true);
        }
    }

    private String awaitInput() {
        String inText = input.awaitInput();
        return inText;
    }
}
