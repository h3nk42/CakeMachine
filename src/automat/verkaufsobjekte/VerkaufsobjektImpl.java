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
        return null;
    }

    @Override
    public int getFachnummer() {
        return 0;
    }

    @Override
    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }


    @Override
    public String toString() {
        return "VerkaufsobjektImpl{" +
                "preis=" + preis +
                ", inspektionsdatum=" + inspektionsdatum +
                ", fachnummer=" + fachnummer +
                '}';
    }
}
