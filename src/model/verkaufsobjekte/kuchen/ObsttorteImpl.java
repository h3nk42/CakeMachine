package model.verkaufsobjekte.kuchen;
import model.Automat;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;
import java.math.BigDecimal;

public class ObsttorteImpl extends KuchenImpl implements Obsttorte, VerkaufsKuchen {

    public ObsttorteImpl(Hersteller hersteller, String obstsorte, String kremsorte, Allergen[] _allergene, BigDecimal preis, int naehrwert, Automat automat, Integer haltbarkeitInStunden) {
        super(hersteller, preis, KuchenArt.Obsttorte, naehrwert, automat,haltbarkeitInStunden);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
        this.allergeneSetup(_allergene);
    }

    @Override
    public String toString() {
        return
                super.toString() +
                ", Obstsorte=" + obstsorte +
                ", kremsorte=" + kremsorte;

    }
}
