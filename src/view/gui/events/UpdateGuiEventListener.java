package view.gui.events;

import java.util.EventListener;

public interface UpdateGuiEventListener extends EventListener {
    void onUpdateGuiEvent(UpdateGuiEvent event);
}
