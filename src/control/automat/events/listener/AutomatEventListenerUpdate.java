package control.automat.events.listener;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import view.gui.events.GuiEventType;
import view.gui.events.UpdateGuiEvent;
import view.gui.events.UpdateGuiEventHandler;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

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
            int[] faecher = (int[]) event.getData().get(DataType.fachnummer);
            try {
                automat.swapFachnummer(faecher[0], faecher[1]);
                Output.print(this, "erfolg", MessageType.success, outputEventHandler);
                updateGui();
            } catch (Exception e) {
                OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
                outputEventHandler.handle(outputEvent);
            }
        }

    private void handleInspectKuchen(AutomatEvent event) {
        int fachNummer = (int) event.getData().get(DataType.fachnummer);
        try {
            automat.aktualisiereInspektionsdatum(fachNummer);
            Output.print(this, "erfolg", MessageType.success, outputEventHandler);
            updateGui();
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void updateGui() {
        Map<DataType, Object> eventData = new HashMap<>();
        eventData.put(DataType.kuchenListe, automat.getKuchen());
        UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.kuchenData);
        updateGuiEventHandler.handle(updateGuiEvent);
    }

}
