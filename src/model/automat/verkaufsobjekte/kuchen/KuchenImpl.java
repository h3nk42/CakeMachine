package model.automat.verkaufsobjekte.kuchen;

import control.automat.Automat;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.VerkaufsobjektImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public abstract class KuchenImpl extends VerkaufsobjektImpl implements Kuchen {

    private Hersteller hersteller;
    private int naehrwert;
    private Duration haltbarkeit;
    private ArrayList<Allergen> allergene;
    private KuchenArt kuchenArt;
    protected String kremsorte;
    protected String obstsorte;

    public KuchenImpl(Hersteller hersteller, BigDecimal preis, KuchenArt kuchenArt, int naehrwert, Automat automat, Integer haltbarkeitInStunden) {
        super(preis, automat);
        this.naehrwert = naehrwert;
        this.allergene = new ArrayList<>();
        this.hersteller = hersteller;
        this.kuchenArt = kuchenArt;
        Instant now = Instant.now();
        Instant then = Instant.now().plusSeconds(haltbarkeitInStunden*60*60);
        this.haltbarkeit = Duration.between(now,then);
    }

    public String getKremsorte() {
        return kremsorte;
    }

    public String getObstsorte() {
        return obstsorte;
    }

    protected void allergeneSetup(Allergen[] _allergene) {
        for (int i = 0; i < _allergene.length; i++) {
            this.allergene.add(_allergene[i]);
        }
    }

    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    @Override
    public ArrayList<Allergen> getAllergene() {
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

    public KuchenArt getKuchenArt() {
        return kuchenArt;
    }


    @Override
    public String toString() {
        return  super.toString() +
                "kuchenArt=" + kuchenArt +
                ", hersteller=" + hersteller +
                ", naehrwert=" + naehrwert +
                ", haltbarkeit=" + haltbarkeit +
                ", allergene=" + allergene;

    }
}
