package automat.verkaufsobjekte.kuchen;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Verkaufsobjekt;

import java.math.BigDecimal;
import java.util.Date;

public class KremkuchenImpl extends KuchenImpl implements Kremkuchen, VerkaufsKuchen {

    private String kremsorte;

    public KremkuchenImpl(Hersteller hersteller, Date inspektionsdatum, String kremsorte) {
        super(hersteller, BigDecimal.valueOf(1.75), inspektionsdatum, KuchenArt.Kremkuchen);
        this.kremsorte = kremsorte;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }


    @Override
    public String toString() {
        /*return "KremkuchenImpl{" +
                "kremsorte='" + kremsorte + '\'' +
                "} " + super.toString();*/
        return "kremkuchen";
    }
}
