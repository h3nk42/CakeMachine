package control.automat.events.listener;

import control.automat.Automat;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import view.output.MessageType;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

public class AutomatEventListenerDelete implements AutomatEventListener {

    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerDelete(Automat automat, OutputEventHandler outputEventHandler) {
        this.automat = automat;
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        if (null != event.getData()) {
            switch (event.getOperationType()) {
                case dHersteller:
                    handleDeleteHersteller(event);
                    break;
                case dKuchen:
                    handleDeleteKuchen(event);
                    break;
            }
        }
    }

    private void handleDeleteKuchen(AutomatEvent event) {
        int fachnummer = (int) event.getData().get(DataType.fachnummer);
        try{
            automat.deleteKuchen(fachnummer);
            OutputEvent outputEvent = new OutputEvent(this, "erfolg", MessageType.success);
            outputEventHandler.handle(outputEvent);
            return;
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void handleDeleteHersteller(AutomatEvent event) {
        String herstellerName = (String) event.getData().get(DataType.hersteller);
        try{
            automat.deleteHersteller(herstellerName);
            OutputEvent outputEvent = new OutputEvent(this, "erfolg", MessageType.success);
            outputEventHandler.handle(outputEvent);
            return;
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }
}
