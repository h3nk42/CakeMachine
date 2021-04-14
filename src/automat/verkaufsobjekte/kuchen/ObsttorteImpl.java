package automat.verkaufsobjekte.kuchen;
import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;

import java.math.BigDecimal;
import java.util.Date;

public class ObsttorteImpl extends KuchenImpl implements Obsttorte, VerkaufsKuchen {

    private String obstsorte;
    private String kremsorte;

    public ObsttorteImpl(Hersteller hersteller, String obstsorte, String kremsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat) {
        super(hersteller, preis, KuchenArt.Obsttorte, naehrwert, automat);
        this.kremsorte = kremsorte;
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
    public String getKremsorte() {
        return kremsorte;
    }

    @Override
    public String toString() {
        return "{" +
                super.toString() +
                "Obstsorte=" + obstsorte +
                ", kremsorte=" + kremsorte + "} ///";

    }
}
