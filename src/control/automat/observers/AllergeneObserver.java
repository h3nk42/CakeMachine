package control.automat.observers;

import control.automat.AutomatController;
import control.automat.events.DataType;
import model.automat.verkaufsobjekte.Allergen;
import view.gui.events.GuiEventType;
import view.gui.events.UpdateGuiEvent;
import view.gui.events.UpdateGuiEventHandler;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

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
                Output.print(this, "Allergene haben sich verändert!", MessageType.warning, outputEventHandler);
                Map<DataType, Object> eventData = new HashMap<>();
                Set<Allergen>[] allergene = new Set[] {automatController.getAllergene(false), automatController.getAllergene(true)};
                eventData.put(DataType.allergene, allergene);
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
