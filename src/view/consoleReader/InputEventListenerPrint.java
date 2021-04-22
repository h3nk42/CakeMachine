package view.consoleReader;

public class InputEventListenerPrint implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if(null!=event.getText())
            System.out.println("input="+event.getText());
    }
}
