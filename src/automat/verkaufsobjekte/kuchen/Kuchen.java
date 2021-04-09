package automat.verkaufsobjekte.kuchen;

import automat.verkaufsobjekte.Allergen;
import automat.hersteller.Hersteller;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
    KuchenArt getKuchenArt();
}
