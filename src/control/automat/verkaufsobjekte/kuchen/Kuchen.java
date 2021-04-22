package control.automat.verkaufsobjekte.kuchen;

import control.automat.verkaufsobjekte.Allergen;
import control.automat.hersteller.Hersteller;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
    KuchenArt getKuchenArt();
}
