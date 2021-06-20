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

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AutomatController implements Subjekt, Serializable {

    private transient AutomatEventHandler automatEventHandler;
    private transient OutputEventHandler outputEventHandler;
    private transient UpdateGuiEventHandler updateGuiEventHandler;

    private transient List<Observer> beobachterList = new LinkedList<>();
    private double kuchenCapacity = 0;
    private Set<Allergen> allergene = new HashSet<>();

    private Automat automat;

    public AutomatController(Automat automat, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        rehydrate(automat, automatEventHandler,outputEventHandler,updateGuiEventHandler);
    }

    public void rehydrate(Automat automat, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        this.automat = automat;
        this.beobachterList = new LinkedList<>();
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
        this.aktualisiereKuchenCapacity();
        this.benachrichtige();
    }


    /* ------- OBSERVER STATUS UPDATES ------- */
    public void aktualisiereKuchenCapacity() {
        this.setKuchenCapacity(calcKuchenCapacity());
    }

    public void aktualisiereAllergene() {
        this.setAllergene(automat.getAllergene(true));
    }

    public void aktualisiereHersteller() {
        this.benachrichtige();
    }

    protected double calcKuchenCapacity() {
        double kuchenAnzahl = Double.valueOf(automat.getKuchen().size());
        double fachAnzahl = Double.valueOf(automat.getFachanzahl());
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
        for (Observer beobachter : this.beobachterList) {
            beobachter.aktualisiere();
        }
    }

    public double getCapacity() {
        return this.kuchenCapacity;
    }

    public Set<Allergen> getAllergene() {return this.allergene;}

    public void setKuchenCapacity(double kuchenCapacity) {
        this.kuchenCapacity = kuchenCapacity;
        this.benachrichtige();
    }
    public void setAllergene(Set<Allergen> allergene) {
        this.allergene = allergene;
        this.benachrichtige();
    }

    public void setAutomatEventHandler(AutomatEventHandler automatEventHandler) {
        this.automatEventHandler = automatEventHandler;
    }

    public void setOutputEventHandler(OutputEventHandler outputEventHandler) {
        this.outputEventHandler = outputEventHandler;
    }

    public void setUpdateGuiEventHandler(UpdateGuiEventHandler updateGuiEventHandler) {
        this.updateGuiEventHandler = updateGuiEventHandler;
    }

    @Override
    public String toString() {
        return "AutomatController{" +
                "automatEventHandler=" + automatEventHandler +
                ", outputEventHandler=" + outputEventHandler +
                ", updateGuiEventHandler=" + updateGuiEventHandler +
                ", beobachterList=" + beobachterList +
                ", kuchenCapacity=" + kuchenCapacity +
                ", allergene=" + allergene + "\n" +
                "automat=" + automat +
                '}';
    }

    public Automat getAutomat() {
        return this.automat;
    }
}
