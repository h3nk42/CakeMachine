package control.automat;

import model.Automat;
import model.verkaufsobjekte.Allergen;

import java.io.Serializable;
import java.util.*;

public class AutomatController implements Subjekt, Serializable {

    private transient List<Observer> beobachterList;
    private double kuchenCapacity = 0;
    private Set<Allergen> allergene = new HashSet<>();


    private Automat automat;

    public AutomatController(Automat automat) {
        this.automat = automat;
        this.beobachterList = new LinkedList<>();
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

    public List<Observer> getBeobachter(){
        return this.beobachterList;
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

    private void setKuchenCapacity(double kuchenCapacity) {
        this.kuchenCapacity = kuchenCapacity;
        this.benachrichtige();
    }

    private void setAllergene(Set<Allergen> allergene) {
        this.allergene = allergene;
        this.benachrichtige();
    }

    @Override
    public String toString() {
        return "AutomatController{" +
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
