package control.automat.events;

import control.automat.Automat;
import view.consoleReader.InputEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AutomatEventHandler {
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

    public List<AutomatEventListener> getList() {
        return this.listenerList;
    }
}