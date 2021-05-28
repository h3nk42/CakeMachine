package control.automat.observers;

import control.automat.AutomatController;
import control.automat.events.DataType;
import view.gui.events.GuiEventType;
import view.gui.events.UpdateGuiEvent;
import view.gui.events.UpdateGuiEventHandler;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

import java.util.HashMap;
import java.util.Map;

public class CreateDeleteCakeObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;
    private Integer kuchenAnzahl = 0;

    public CreateDeleteCakeObserver(AutomatController automatController, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler){
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        automatController.meldeAn(this);
        updateKuchenAnzahl();
    }

    @Override
    public void aktualisiere() {
        switch(updateKuchenAnzahl()) {
            case -1:
                Output.print(this, "Kuchen entfernt", MessageType.warning, outputEventHandler);
                sendUpdateEvent();
                break;
            case 0:
                //Output.print(this, "Kuchen gleich", MessageType.warning, outputEventHandler);
                break;
            case 1:
                sendUpdateEvent();
                Output.print(this, "Kuchen hinzugefügt", MessageType.warning, outputEventHandler);
                break;
        }
    }

    private int updateKuchenAnzahl() {
        int tempCount = automatController.getKuchen().size();
        if(kuchenAnzahl<tempCount) {
            setCount();
            return 1;
        } else if (kuchenAnzahl>tempCount) {
            setCount();
            return -1;
        }
        else {
            setCount();
            return 0;
        }
    }

    private void sendUpdateEvent() {
        Map<DataType, Object> eventData = new HashMap<>();
        eventData.put(DataType.kuchenListe, automatController.getKuchen());
        UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.kuchenData);
        updateGuiEventHandler.handle(updateGuiEvent);
        eventData.put(DataType.hersteller, automatController.getKuchenCounter());
        UpdateGuiEvent updateGuiEventHersteller = new UpdateGuiEvent(this, eventData, GuiEventType.herstellerData);
        updateGuiEventHandler.handle(updateGuiEventHersteller);
    }

    private void setCount() {
        this.kuchenAnzahl = automatController.getKuchen().size();
    }


}
