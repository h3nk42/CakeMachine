package control.automat.events;

import control.automat.AutomatController;
import model.Automat;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AutomatEventListenerUpdate  implements AutomatEventListener, Serializable {

    private UpdateGuiEventHandler updateGuiEventHandler;
    private Automat automat;
    private AutomatController automatC;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerUpdate(OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler, AutomatController automatController) {
        this.updateGuiEventHandler = updateGuiEventHandler;
        this.automatC = automatController;
        this.automat = automatController.getAutomat();
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        automat = automatC.getAutomat();
        if (null != event.getData()) {
            switch(event.getOperationType()) {
                case swapKuchen:
                    handleSwapKuchen(event);
                    break;
                case inspectKuchen:
                    handleInspectKuchen(event);
                    break;
            }
        }
    }

    private void handleSwapKuchen(AutomatEvent event) {
            int[] faecher = (int[]) event.getData().get(CakeDataType.fachnummer);
            try {
                automat.swapFachnummer(faecher[0], faecher[1]);
                //Output.print(this, "erfolg", MessageType.success, outputEventHandler);
                updateGui();
            } catch (Exception e) {
                OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
                outputEventHandler.handle(outputEvent);
            }
        }

    private void handleInspectKuchen(AutomatEvent event) {
        int fachNummer = (int) event.getData().get(CakeDataType.fachnummer);
        try {
            automat.aktualisiereInspektionsdatum(fachNummer);
            //Output.print(this, "erfolg", MessageType.success, outputEventHandler);
            updateGui();
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void updateGui() {
        Map<CakeDataType, Object> eventData = new HashMap<>();
        eventData.put(CakeDataType.kuchenListe, automat.getKuchen());
        UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.kuchenData);
        updateGuiEventHandler.handle(updateGuiEvent);
    }

}
