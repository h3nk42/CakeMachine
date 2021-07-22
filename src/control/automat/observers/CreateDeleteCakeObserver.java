package control.automat.observers;

import control.automat.Observer;
import control.console.output.OutputEvent;
import model.Automat;
import control.automat.AutomatController;
import control.automat.events.CakeDataType;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;

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
        OutputEvent outputEvent;
        switch(updateKuchenAnzahl()) {
            case -1:
                outputEvent = new OutputEvent(this, "Kuchen entfernt", MessageType.warning);
                outputEventHandler.handle(outputEvent);
                sendUpdateEvent();
                break;
            case 0:
                //Output.print(this, "Kuchen gleich", MessageType.warning, outputEventHandler);
                break;
            case 1:
                sendUpdateEvent();
                outputEvent = new OutputEvent(this, "Kuchen hinzugefügt", MessageType.warning);
                outputEventHandler.handle(outputEvent);
                break;
        }
    }

    private int updateKuchenAnzahl() {
        Automat a = automatController.getAutomat();
        int tempCount = a.getKuchen().size();
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
        Automat a = automatController.getAutomat();
        Map<CakeDataType, Object> eventData = new HashMap<>();
        eventData.put(CakeDataType.kuchenListe, a.getKuchen());
        UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.kuchenData);
        updateGuiEventHandler.handle(updateGuiEvent);
        eventData.put(CakeDataType.hersteller, a.getKuchenCounter());
        UpdateGuiEvent updateGuiEventHersteller = new UpdateGuiEvent(this, eventData, GuiEventType.herstellerData);
        updateGuiEventHandler.handle(updateGuiEventHersteller);
    }

    private void setCount() {
        Automat a = automatController.getAutomat();
        this.kuchenAnzahl = a.getKuchen().size();
    }


}
