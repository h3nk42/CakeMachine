package automat.verkaufsobjekte;

import java.math.BigDecimal;
import java.util.Date;

public class VerkaufsobjektImpl implements Verkaufsobjekt {

    protected BigDecimal preis;
    protected Date inspektionsdatum;
    protected int fachnummer;
    
    public VerkaufsobjektImpl (BigDecimal preis, Date inspektionsdatum) {
        this.preis = preis;
        this.inspektionsdatum = inspektionsdatum;
    }
    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return fachnummer;
    }

    @Override
    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    @Override
    public void setInspektionsdatum(Date inspektionsdatum) {
        this.inspektionsdatum = inspektionsdatum;
    }



    @Override
    public String toString() {
        return "{"+
                "preis=" + preis +
                ", inspektionsdatum=" + inspektionsdatum +
                ", fachnummer=" + fachnummer +
                "} /// ";
    }
}
