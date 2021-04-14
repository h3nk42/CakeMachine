package automat.verkaufsobjekte.kuchen;
import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;

import java.math.BigDecimal;
import java.util.Date;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen, VerkaufsKuchen {

    private String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, String obstsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat) {
        super(hersteller, preis, KuchenArt.Obstkuchen, naehrwert, automat);
        this.obstsorte = obstsorte;
        for (int i = 0; i < _allergene.length; i++) {
            this.allergene.add(_allergene[i]);
        }
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }

    @Override
    public KuchenArt getKuchenArt() {
        return kuchenArt;
    }

    @Override
    public String toString() {
        return "{" +
                super.toString() +
                ", Obstsorte=" + obstsorte +
                "} ///";
    }
}
