package model.verkaufsobjekte.kuchen;

import model.verkaufsobjekte.Allergen;
import model.hersteller.Hersteller;

import java.time.Duration;
import java.util.ArrayList;

public interface Kuchen {
    Hersteller getHersteller();
    ArrayList<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();

}
