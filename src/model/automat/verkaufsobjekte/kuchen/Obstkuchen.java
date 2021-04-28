package model.automat.verkaufsobjekte.kuchen;

import model.automat.verkaufsobjekte.Verkaufsobjekt;

public interface Obstkuchen extends Kuchen, Verkaufsobjekt {
    String getObstsorte();
}
