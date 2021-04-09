package automat.verkaufsobjekte.kuchen;
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

    public KremkuchenImpl(Hersteller hersteller, Date inspektionsdatum, String kremsorte) {
        super(hersteller, BigDecimal.valueOf(1.75), inspektionsdatum, KuchenArt.Kremkuchen);
        this.kremsorte = kremsorte;
        allergene.add(Allergen.Gluten);
        allergene.add(Allergen.Sesamsamen);
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
