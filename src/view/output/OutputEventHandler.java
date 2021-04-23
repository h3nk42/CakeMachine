package view.output;

import control.automat.Automat;
import view.consoleReader.InputEvent;
import view.consoleReader.InputEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OutputEventHandler {
    private List<OutputEventListener> listenerList = new LinkedList<>();
    private Map<InputEventListener, Boolean> dontExecute =  new HashMap<>();

    public void add(OutputEventListener listener, Boolean isInitial) {
        /*if(!isInitial){
            this.dontExecute.put(listener, true);
        }
        this.dontExecute.put(listener, false);*/
        this.listenerList.add(listener);
    }
    public void remove(OutputEventListener listener) {
        this.listenerList.remove(listener);
    }
    public void handle(OutputEvent oEvent){
        for (int i = 0; i < listenerList.size(); i++) {
            OutputEventListener oEventListener =   listenerList.get(i);
           /* if (this.dontExecute.get(tempListener)) {
                this.dontExecute.put(tempListener, false);
            } else {
                tempListener.onInputEvent(event);
            }*/
            oEventListener.onOutputEvent(oEvent);
        }
    }
    public List<OutputEventListener> getList() {
        return this.listenerList;
    }
}