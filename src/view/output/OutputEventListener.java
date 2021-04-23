package view.output;

import java.util.EventListener;

public interface OutputEventListener extends EventListener {
    void onOutputEvent(OutputEvent event);
}
