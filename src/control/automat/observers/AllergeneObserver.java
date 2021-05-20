package control.automat.observers;

import control.automat.AutomatController;
import model.automat.verkaufsobjekte.Allergen;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

import java.util.HashSet;
import java.util.Set;

public class AllergeneObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private Set<Allergen> lastAllergene;

    public AllergeneObserver(AutomatController automatController, OutputEventHandler outputEventHandler) {
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        this.lastAllergene = new HashSet<>();
        automatController.meldeAn(this);
    }

    @Override
    public void aktualisiere() {
            if (!checkIfAllergeneMatch()) {
                lastAllergene = automatController.getAllergene();
                Output.print(this, "Allergene haben sich verändert!", MessageType.warning, outputEventHandler);
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
