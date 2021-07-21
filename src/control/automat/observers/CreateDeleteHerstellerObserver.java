package control.automat.observers;

import model.Automat;
import control.automat.AutomatController;
import control.automat.events.CakeDataType;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
import control.console.output.MessageType;
import view.console.Printer;
import control.console.output.OutputEventHandler;

import java.util.HashMap;
import java.util.Map;

public class CreateDeleteHerstellerObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;
    private Integer herstellerAnzahl = 0;

    public CreateDeleteHerstellerObserver(AutomatController automatController, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler){
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        automatController.meldeAn(this);
        updateHerstellerAnzahl();
    }

    @Override
    public void aktualisiere() {
        switch(updateHerstellerAnzahl()) {
            case -1:
                Printer.print(this, "Hersteller entfernt", MessageType.warning, outputEventHandler);
                sendUpdateEvent();
                break;
            case 0:
                //Output.print(this, "Kuchen gleich", MessageType.warning, outputEventHandler);
                break;
            case 1:
                Printer.print(this, "Hersteller hinzugefügt", MessageType.warning, outputEventHandler);
                sendUpdateEvent();
                break;
        }
    }

    private void sendUpdateEvent() {
        Map<CakeDataType, Object> eventData = new HashMap<>();
        Automat a = automatController.getAutomat();
        eventData.put(CakeDataType.hersteller, a.getKuchenCounter());
        UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.herstellerData);
        updateGuiEventHandler.handle(updateGuiEvent);
    }

    private int updateHerstellerAnzahl() {
        Automat a = automatController.getAutomat();
        int tempCount = a.getHersteller().size();
        if(herstellerAnzahl<tempCount) {
            setCount();
            return 1;
        } else if (herstellerAnzahl>tempCount) {
            setCount();
            return -1;
        }
        else {
            setCount();
            return 0;
        }
    }

    private void setCount() {
        Automat a = automatController.getAutomat();
        this.herstellerAnzahl = a.getHersteller().size();
    }


}
