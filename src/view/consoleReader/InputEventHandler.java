package view.consoleReader;

import control.automat.Automat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InputEventHandler {
    private List<InputEventListener> listenerList = new LinkedList<>();
    private Map<InputEventListener, Boolean> dontExecute =  new HashMap<>();
    private Automat automat;

    public InputEventHandler(Automat automat) {
        this.automat = automat;
    }
    public void add(InputEventListener listener, Boolean isInitial) {
        if(!isInitial){
            this.dontExecute.put(listener, true);
        }
        this.dontExecute.put(listener, false);
        this.listenerList.add(listener);
    }
    public void remove(InputEventListener listener) {
        this.listenerList.remove(listener);
    }
    public void handle(InputEvent event){
        event.setAutomat(this.automat);
        for (int i = 0; i < listenerList.size(); i++) {
            InputEventListener tempListener =   listenerList.get(i);
            if (this.dontExecute.get(tempListener)) {
                this.dontExecute.put(tempListener, false);
            } else {
                tempListener.onInputEvent(event);
            }
        }
    }
    public List<InputEventListener> getList() {
        return this.listenerList;
    }
}