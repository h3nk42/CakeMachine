package view.consoleReader;

public class InputEventListenerCreate implements InputEventListener {

    @Override
    public void onInputEvent(InputEvent event) {
        if(null!=event.getText())
            System.out.println("input="+event.getText());
    }

    @Override
    public String toString() {
        return "create";
    }
}
