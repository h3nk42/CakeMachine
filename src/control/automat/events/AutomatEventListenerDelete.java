package control.automat.events;

import control.automat.AutomatController;
import model.Automat;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

import java.io.Serializable;

public class AutomatEventListenerDelete implements AutomatEventListener, Serializable {

    private AutomatController automatC;
    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerDelete(OutputEventHandler outputEventHandler, AutomatController automatController) {
        this.automatC = automatController;
        this.automat = automatController.getAutomat();
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        automat = automatC.getAutomat();
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
        int fachnummer = (int) event.getData().get(CakeDataType.fachnummer);
        try{
            automat.deleteKuchen(fachnummer);
            automatC.aktualisiereKuchenCapacity();
            automatC.aktualisiereAllergene();
            return;
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void handleDeleteHersteller(AutomatEvent event) {
        String herstellerName = (String) event.getData().get(CakeDataType.hersteller);
        try{
            automat.deleteHersteller(herstellerName);
            //Output.print(this, "erfolg", MessageType.success, outputEventHandler);
            automatC.aktualisiereHersteller();
            return;
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }


}
