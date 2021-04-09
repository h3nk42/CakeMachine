package automat.verkaufsobjekte.kuchen;

import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.VerkaufsobjektImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class KuchenImpl extends VerkaufsobjektImpl implements Kuchen {

    protected Hersteller hersteller;
    protected int naehrwert;
    protected Duration haltbarkeit;
    protected Collection<Allergen> allergene;
    protected KuchenArt kuchenArt;

    public KuchenImpl(Hersteller hersteller, BigDecimal preis, Date inspektionsdatum, KuchenArt kuchenArt) {
        super(preis, inspektionsdatum);
        this.allergene = new ArrayList<>();
        this.hersteller = hersteller;
        this.kuchenArt = kuchenArt;
    }

    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }

    @Override
    public  KuchenArt getKuchenArt() {
        return kuchenArt;
    }

    @Override
    public String toString() {
        return "{" +
                "hersteller=" + hersteller +
                //", naehrwert=" + naehrwert +
                //", haltbarkeit=" + haltbarkeit +
                ", allergene=" + allergene +
                "} " + super.toString();
    }
}
