package automat.verkaufsobjekte.kuchen;
import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.Verkaufsobjekt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, VerkaufsKuchen {

    private String kremsorte;

    public KremkuchenImpl(Hersteller hersteller, String kremsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat,Integer haltbarkeitInStunden) {
        super(hersteller, preis, KuchenArt.Kremkuchen, naehrwert, automat,haltbarkeitInStunden);
        this.kremsorte = kremsorte;
        for (int i = 0; i < _allergene.length; i++) {
            this.allergene.add(_allergene[i]);
        }
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }


    @Override
    public String toString() {
        return "{" +
                super.toString() +
                ", Kremsorte=" + kremsorte +
                "} ///";
    }
}
