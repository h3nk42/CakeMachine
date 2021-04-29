package control.automat.observers;

import model.automat.verkaufsobjekte.Allergen;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AutomatSubject implements Subjekt {

    private List<Observer> beobachterList = new LinkedList<>();
    private double kuchenCapacity;
    private Set<Allergen> allergene;

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
