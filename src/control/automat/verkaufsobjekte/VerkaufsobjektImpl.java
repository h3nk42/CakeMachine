package control.automat.verkaufsobjekte;
import control.automat.Automat;
import java.math.BigDecimal;
import java.util.Date;

public class VerkaufsobjektImpl implements Verkaufsobjekt {

    private BigDecimal preis;
    private Automat automat;
    
    public VerkaufsobjektImpl (BigDecimal preis, Automat automat) {
        this.preis = preis;
        this.automat = automat;
    }
    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return automat.getInspektionsdatum(this);
    }

    @Override
    public int getFachnummer() {
        return automat.getFachnummer(this);
    }

    @Override
    public String toString() {
        return "{"+
                "preis=" + preis +
                ", inspektionsdatum=" + getInspektionsdatum() +
                ", fachnummer=" + getFachnummer() ;
    }
}
