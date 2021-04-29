package control.automat.observers;

import model.automat.verkaufsobjekte.Allergen;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

import java.util.HashSet;
import java.util.Set;

public class AllergeneObserver implements Observer {

    private AutomatSubject automatSubject;
    private OutputEventHandler outputEventHandler;
    private Set<Allergen> lastAllergene;

    public AllergeneObserver(AutomatSubject automatSubject, OutputEventHandler outputEventHandler) {
        this.automatSubject = automatSubject;
        this.outputEventHandler = outputEventHandler;
        this.lastAllergene = new HashSet<>();
        automatSubject.meldeAn(this);
    }

    @Override
    public void aktualisiere() {
            if (!checkIfAllergeneMatch()) {
                lastAllergene = automatSubject.getAllergene();
                Output.print(this, "Allergene haben sich verändert!", MessageType.warning, outputEventHandler);
            }
    }

    private boolean checkIfAllergeneMatch() {
        if(lastAllergene.size()!=automatSubject.getAllergene().size()){
            return false;
        } else {
            for(Allergen a: lastAllergene) {
                if( !automatSubject.getAllergene().contains(a) ){
                    return false;
                }
            }
        }
        return true;
    }
}
