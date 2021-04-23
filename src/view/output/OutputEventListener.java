package view.output;

import view.consoleReader.InputEvent;

import java.util.EventListener;

public interface OutputEventListener extends EventListener {
    void onOutputEvent(OutputEvent event);
}
