package control.automat.observers;

import control.automat.AutomatController;
import control.automat.events.CakeDataType;
import control.console.output.OutputEvent;
import model.verkaufsobjekte.Allergen;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AllergeneObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;
    private Set<Allergen> lastAllergene;

    public AllergeneObserver(AutomatController automatController, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        this.lastAllergene = new HashSet<>();
        automatController.meldeAn(this);
    }

    @Override
    public void aktualisiere() {
            if (!checkIfAllergeneMatch()) {
                lastAllergene = automatController.getAllergene();
                OutputEvent outputEvent = new OutputEvent(this, "Allergene haben sich verändert!", MessageType.warning);
                outputEventHandler.handle(outputEvent);
                Map<CakeDataType, Object> eventData = new HashMap<>();
                Set<Allergen>[] allergene = new Set[] {automatController.getAutomat().getAllergene(false), automatController.getAutomat().getAllergene(true)};
                eventData.put(CakeDataType.allergene, allergene);
                UpdateGuiEvent updateGuiEvent = new UpdateGuiEvent(this, eventData, GuiEventType.allergenData);
                updateGuiEventHandler.handle(updateGuiEvent);
            }
    }

    private boolean checkIfAllergeneMatch() {
        if(lastAllergene.size()!=automatController.getAllergene().size()){
            return false;
        } else {
            for(Allergen a: lastAllergene) {
                if( !automatController.getAllergene().contains(a) ){
                    return false;
                }
            }
        }
        return true;
    }
}
