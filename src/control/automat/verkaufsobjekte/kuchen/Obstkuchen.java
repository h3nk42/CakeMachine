package control.automat.verkaufsobjekte.kuchen;

import control.automat.verkaufsobjekte.Verkaufsobjekt;

public interface Obstkuchen extends Kuchen, Verkaufsobjekt {
    String getObstsorte();
}
