package control.automat.events.listener;

import control.automat.Automat;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

public class AutomatEventListenerCreate implements AutomatEventListener {

    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerCreate(Automat automat, OutputEventHandler outputEventHandler) {
        this.automat = automat;
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        if (null != event.getData()) {
            switch(event.getOperationType()) {
                case cKuchen:
                    handleCK(event);
                    break;
                case cHersteller:
                    handleCH(event);
                    break;
            }
        }
    }

    private void handleCH(AutomatEvent event) {
        String name = (String) event.getData().get(DataType.hersteller);
        try {
            automat.createHersteller(name);
            OutputEvent outputEvent = new OutputEvent(this, "erfolg: " + automat.getHersteller().toString(), MessageType.success);
            outputEventHandler.handle(outputEvent);
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void handleCK(AutomatEvent event) {

    }

    @Override
    public String toString() {
        return "create";
    }
}
