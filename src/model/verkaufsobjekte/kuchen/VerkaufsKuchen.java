package model.verkaufsobjekte.kuchen;

import model.verkaufsobjekte.Verkaufsobjekt;

import java.util.Date;

public interface VerkaufsKuchen extends Kuchen, Verkaufsobjekt {
    KuchenArt getKuchenArt();
    Date getInsertionDate();
}
