package control.automat;

import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.AutomatEventListenerCreate;
import control.automat.events.listener.AutomatEventListenerDelete;
import control.automat.events.listener.AutomatEventListenerRead;
import control.automat.observers.AutomatSubject;
import view.output.OutputEventHandler;

public class AutomatController extends Automat {

    //private final Automat automat;
    private AutomatEventHandler automatEventHandler;
    private OutputEventHandler outputEventHandler;
    private AutomatSubject automatSubject;


    public AutomatController(Integer fachAnzahl, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, AutomatSubject automatSubject) {
        super(fachAnzahl);
        //this.automat = new Automat(fachAnzahl);
        this.automatEventHandler = automatEventHandler;
        this.outputEventHandler = outputEventHandler;
        this.automatSubject = automatSubject;
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(outputEventHandler, this);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandler, this);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(outputEventHandler,this);
        automatEventHandler.add(automatEventListenerCreate);
        automatEventHandler.add(automatEventListenerRead);
        automatEventHandler.add(automatEventListenerDelete);
    }


    /* ------- OBSERVER STATUS UPDATES ------- */
    public void aktualisiereKuchenCapacity() {
        automatSubject.setKuchenCapacity(calcKuchenCapacity());
    }

    public void aktualisiereAllergene() {
        automatSubject.setAllergene(getAllergene(true));
    }

    protected double calcKuchenCapacity() {
        double kuchenAnzahl = Double.valueOf(getKuchen().size());
        double fachAnzahl = Double.valueOf(getFachanzahl());
        double capacity = kuchenAnzahl/fachAnzahl;
        return capacity;
    }
}
