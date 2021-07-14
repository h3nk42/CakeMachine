package lib.beans;

import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Date;

public class KuchenBean implements Serializable, Bean{
    private KuchenArt kuchenArt;
    private String hersteller;
    private Double preis;
    private int naehrwert;
    private Allergen[] allergene;
    private String[] extraData;
    private Long haltbarkeit;
    private Date inspektionsDatum;

    public KuchenBean(){
    }

    public KuchenBean(KuchenArt kuchenArt, String hersteller, Double preis, int naehrwert, Allergen[] allergene, String[] extraData, Duration haltbarkeit, Date inspektionsDatum){
        this.hersteller = hersteller;
        this.kuchenArt = kuchenArt;
        this.preis = preis;
        this.naehrwert = naehrwert;
        this.allergene = allergene;
        this.extraData = extraData;
        this.haltbarkeit = haltbarkeit.get(ChronoUnit.SECONDS)/60/60;
        this.inspektionsDatum = inspektionsDatum;
    }

    public KuchenArt getKuchenArt() {
        return kuchenArt;
    }

    public void setKuchenArt(KuchenArt kuchenArt) {
        this.kuchenArt = kuchenArt;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public int getNaehrwert() {
        return naehrwert;
    }

    public void setNaehrwert(int naehrwert) {
        this.naehrwert = naehrwert;
    }

    public Allergen[] getAllergene() {
        return allergene;
    }

    public void setAllergene(Allergen[] allergene) {
        this.allergene = allergene;
    }

    public String[] getExtraData() {
        return extraData;
    }

    public void setExtraData(String[] extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "KuchenBean{" +
                "kuchenArt=" + kuchenArt +
                ", hersteller='" + hersteller + '\'' +
                ", preis=" + preis +
                ", naehrwert=" + naehrwert +
                ", allergene=" + Arrays.toString(allergene) +
                ", extraData=" + Arrays.toString(extraData) +
                ", haltbarkeit='" +Duration.of(haltbarkeit, ChronoUnit.HOURS) +
                ", inspektionsDatum=" + inspektionsDatum +
                '}';
    }

    public Date getInspektionsDatum() {
        return inspektionsDatum;
    }

    public void setInspektionsDatum(Date inspektionsDatum) {
        this.inspektionsDatum = inspektionsDatum;
    }

    public Long getHaltbarkeit() {
        return haltbarkeit;
    }

    public void setHaltbarkeit(Long haltbarkeit) {
        this.haltbarkeit = haltbarkeit;
    }
}
