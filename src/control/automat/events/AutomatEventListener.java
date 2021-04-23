package control.automat.events;

import java.util.EventListener;

public interface AutomatEventListener extends EventListener {
    void onAutomatEvent(AutomatEvent event);
}
