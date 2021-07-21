package control.automat;

import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatEventListener;
import control.automat.events.listener.*;
import control.automat.observers.Observer;
import control.automat.observers.Subjekt;
import model.automat.verkaufsobjekte.Allergen;
import view.gui.events.UpdateGuiEventHandler;
import view.output.OutputEventHandler;

import java.io.Serializable;
import java.util.*;

public class AutomatController implements Subjekt, Serializable {

    private transient AutomatEventHandler automatEventHandler;
    private transient OutputEventHandler outputEventHandler;
    private transient UpdateGuiEventHandler updateGuiEventHandler;

    private transient List<Observer> beobachterList = new LinkedList<>();
    private double kuchenCapacity = 0;
    private Set<Allergen> allergene = new HashSet<>();


    private Automat automat;

    public AutomatController(Automat automat, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        this.automat = automat;
        this.automatEventHandler = automatEventHandler;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(outputEventHandler, this);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandler, this);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(outputEventHandler,this);
        AutomatEventListenerUpdate automatEventListenerUpdate = new AutomatEventListenerUpdate(outputEventHandler, updateGuiEventHandler, this);
        AutomatEventListenerPersist automatEventListenerPersist = new AutomatEventListenerPersist(outputEventHandler, this);

        this.beobachterList = new LinkedList<>();
    }

    public AutomatEventHandler getAutomatEventHandler(){
        return this.automatEventHandler;
    }
    public OutputEventHandler getOutputEventHandler(){
        return this.outputEventHandler;
    }
    public UpdateGuiEventHandler getUpdateGuiEventHandler(){
        return this.updateGuiEventHandler;
    }

    public void rehydrate(Automat automat) {
        this.automat = automat;
        this.aktualisiereKuchenCapacity();
        this.aktualisiereAllergene();
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
