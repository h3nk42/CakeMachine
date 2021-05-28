package control.automat;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.AutomatEventListenerCreate;
import control.automat.events.listener.AutomatEventListenerDelete;
import control.automat.events.listener.AutomatEventListenerRead;
import control.automat.events.listener.AutomatEventListenerUpdate;
import control.automat.observers.AutomatSubject;
import control.automat.observers.Observer;
import control.automat.observers.Subjekt;
import model.automat.verkaufsobjekte.Allergen;
import view.gui.events.UpdateGuiEventHandler;
import view.output.OutputEventHandler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AutomatController extends Automat implements Subjekt {

    private AutomatEventHandler automatEventHandler;
    private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;

    private List<Observer> beobachterList = new LinkedList<>();
    private double kuchenCapacity = 0;
    private Set<Allergen> allergene = new HashSet<>();


    public AutomatController(Integer fachAnzahl, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        super(fachAnzahl);
        this.automatEventHandler = automatEventHandler;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(outputEventHandler, this);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandler, this);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(outputEventHandler,this);
        AutomatEventListenerUpdate automatEventListenerUpdate = new AutomatEventListenerUpdate(outputEventHandler, updateGuiEventHandler, this);
        automatEventHandler.add(automatEventListenerCreate);
        automatEventHandler.add(automatEventListenerRead);
        automatEventHandler.add(automatEventListenerDelete);
        automatEventHandler.add(automatEventListenerUpdate);
    }


    /* ------- OBSERVER STATUS UPDATES ------- */
    public void aktualisiereKuchenCapacity() {
        setKuchenCapacity(calcKuchenCapacity());
    }

    public void aktualisiereAllergene() {
        setAllergene(getAllergene(true));
    }

    public void aktualisiereHersteller() {
        this.benachrichtige();
    }

    protected double calcKuchenCapacity() {
        double kuchenAnzahl = Double.valueOf(getKuchen().size());
        double fachAnzahl = Double.valueOf(getFachanzahl());
        double capacity = kuchenAnzahl/fachAnzahl;
        return capacity;
    }


    @Override
    public void meldeAn(Observer beobachter) {
        this.beobachterList.add(beobachter);
    }

    @Override
    public void meldeAb(Observer beobachter) {
        this.beobachterList.remove(beobachter);
    }

    @Override
    public void benachrichtige() {
        for (Observer beobachter : beobachterList) {
            beobachter.aktualisiere();
        }
    }

    public double getCapacity() {
        return kuchenCapacity;
    }

    public Set<Allergen> getAllergene() {return allergene;}

    public void setKuchenCapacity(double kuchenCapacity) {
        this.kuchenCapacity = kuchenCapacity;
        this.benachrichtige();
    }
    public void setAllergene(Set<Allergen> allergene) {
        this.allergene = allergene;
        this.benachrichtige();
    }
}
