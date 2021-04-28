package model.automat.verkaufsobjekte.kuchen;

import model.automat.verkaufsobjekte.Allergen;
import model.automat.hersteller.Hersteller;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();

}
