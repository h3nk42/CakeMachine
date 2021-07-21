package model.verkaufsobjekte.kuchen;

import model.verkaufsobjekte.Verkaufsobjekt;

public interface Obstkuchen extends Kuchen, Verkaufsobjekt {
    String getObstsorte();
}
