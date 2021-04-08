package automat.verkaufsobjekte.kuchen;
import automat.hersteller.Hersteller;

import java.math.BigDecimal;
import java.util.Date;

public class ObstkuchenImpl extends KuchenImpl implements Obstkuchen, VerkaufsKuchen {

    private String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, Date inspektionsdatum, String obstsorte) {
        super(hersteller, BigDecimal.valueOf(1.50), inspektionsdatum,KuchenArt.Obstkuchen);
        this.obstsorte = obstsorte;
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
        /*return "ObstkuchenImpl{" +
                "obstsorte='" + obstsorte + '\'' +
                "} " + super.toString();*/
        return "obstkuchen";
    }
}
