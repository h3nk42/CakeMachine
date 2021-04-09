package automat.verkaufsobjekte.kuchen;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;

import java.math.BigDecimal;
import java.util.Date;

public class ObsttorteImpl extends KuchenImpl implements Obsttorte, VerkaufsKuchen {

    private String obstsorte;
    private String kremsorte;

    public ObsttorteImpl(Hersteller hersteller, Date inspektionsdatum, String obstsorte, String kremsorte) {
        super(hersteller, BigDecimal.valueOf(2.25), inspektionsdatum, KuchenArt.Obsttorte);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
        allergene.add(Allergen.Gluten);
        allergene.add(Allergen.Erdnuss);
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
        return super.toString();
    }
}
