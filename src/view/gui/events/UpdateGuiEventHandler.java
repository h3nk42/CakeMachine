package view.gui.events;

import java.util.LinkedList;
import java.util.List;

public class UpdateGuiEventHandler {
    private List<UpdateGuiEventListener> listenerList = new LinkedList<>();

    public void add(UpdateGuiEventListener listener) {
        this.listenerList.add(listener);
    }

    public void remove(UpdateGuiEventListener listener) {
        this.listenerList.remove(listener);
    }

    public void handle(UpdateGuiEvent event) {
        for (int i = 0; i < listenerList.size(); i++) {
            UpdateGuiEventListener tempListener = listenerList.get(i);
            tempListener.onUpdateGuiEvent(event);
        }
    }

    public List<UpdateGuiEventListener> getList() {
        return this.listenerList;
    }
}