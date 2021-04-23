package view.consoleReader;

import control.automat.Automat;

import java.util.EventObject;

public class InputEvent extends EventObject {
    private String text;
    private Automat automat;

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    public InputEvent(Object source, String text) {
        super(source);
        this.text=text;
    }

    public Automat getAutomat() {
       return this.automat;
    }

    public String getText(){
        return this.text;
    }
}
