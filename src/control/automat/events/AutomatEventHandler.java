package control.automat.events;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class AutomatEventHandler implements Serializable {
    private List<AutomatEventListener> listenerList = new LinkedList<>();

    public void add(AutomatEventListener listener) {
        this.listenerList.add(listener);
    }

    public void remove(AutomatEventListener listener) {
        this.listenerList.remove(listener);
    }

    public void handle(AutomatEvent event) {
        for (int i = 0; i < listenerList.size(); i++) {
            AutomatEventListener tempListener = listenerList.get(i);
            tempListener.onAutomatEvent(event);
        }
    }
}