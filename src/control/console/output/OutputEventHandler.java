package control.console.output;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class OutputEventHandler implements Serializable {
    private List<OutputEventListener> listenerList = new LinkedList<>();

    public void add(OutputEventListener listener, Boolean isInitial) {
        this.listenerList.add(listener);
    }
    public void remove(OutputEventListener listener) {
        this.listenerList.remove(listener);
    }
    public void handle(OutputEvent oEvent){
        for (int i = 0; i < listenerList.size(); i++) {
            OutputEventListener oEventListener = listenerList.get(i);
            oEventListener.onOutputEvent(oEvent);
        }
    }
    public List<OutputEventListener> getList() {
        return this.listenerList;
    }
}