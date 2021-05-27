package model.automat.verkaufsobjekte.kuchen;
import control.automat.Automat;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.Allergen;

import java.math.BigDecimal;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, VerkaufsKuchen {

    public KremkuchenImpl(Hersteller hersteller, String kremsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat,Integer haltbarkeitInStunden) {
        super(hersteller, preis, KuchenArt.Kremkuchen, naehrwert, automat,haltbarkeitInStunden);
        this.kremsorte = kremsorte;
        this.allergeneSetup(_allergene);
    }

    @Override
    public String toString() {
        return
                super.toString() +
                ", Kremsorte=" + kremsorte
               ;
    }
}
