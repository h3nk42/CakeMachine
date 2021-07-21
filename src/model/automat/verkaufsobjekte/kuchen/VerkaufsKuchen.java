package model.automat.verkaufsobjekte.kuchen;

import model.automat.verkaufsobjekte.Verkaufsobjekt;

import java.util.Date;

public interface VerkaufsKuchen extends Kuchen, Verkaufsobjekt {
    KuchenArt getKuchenArt();
    Date getInsertionDate();
}
