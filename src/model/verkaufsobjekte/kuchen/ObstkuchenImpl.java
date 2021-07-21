package model.verkaufsobjekte.kuchen;
import model.Automat;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;

import java.math.BigDecimal;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen, VerkaufsKuchen {

    public ObstkuchenImpl(Hersteller hersteller, String obstsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat,Integer haltbarkeitInStunden) {
        super(hersteller, preis, KuchenArt.Obstkuchen, naehrwert, automat,haltbarkeitInStunden);
        this.obstsorte = obstsorte;
        this.allergeneSetup(_allergene);
    }

    @Override
    public String toString() {
        return
                super.toString() +
                ", Obstsorte=" + obstsorte
                ;
    }
}
