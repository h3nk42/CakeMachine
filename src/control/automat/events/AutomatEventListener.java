package control.automat.events;

import control.automat.Automat;
import view.consoleReader.InputEvent;

import java.util.EventListener;

public interface AutomatEventListener extends EventListener {
    void onAutomatEvent(AutomatEvent event);
}
