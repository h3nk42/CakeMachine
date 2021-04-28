package model.automat.verkaufsobjekte.kuchen;

import model.automat.verkaufsobjekte.Verkaufsobjekt;

public interface VerkaufsKuchen extends Kuchen, Verkaufsobjekt {
    KuchenArt getKuchenArt();
}
