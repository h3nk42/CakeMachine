package automat.verkaufsobjekte.kuchen;

import automat.verkaufsobjekte.Verkaufsobjekt;

public interface Obstkuchen extends Kuchen, Verkaufsobjekt {
    String getObstsorte();
}
