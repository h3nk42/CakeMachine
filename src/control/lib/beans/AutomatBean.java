package control.lib.beans;

import java.io.Serializable;

public class AutomatBean implements Serializable, Bean {

    private Integer fachAnzahl;

    public AutomatBean()
    {
    }
    public AutomatBean(Integer fachAnzahl){
        this.fachAnzahl = fachAnzahl;
    }

    public Integer getFachAnzahl() {
        return fachAnzahl;
    }

    public void setFachAnzahl(Integer fachAnzahl) {
        this.fachAnzahl = fachAnzahl;
    }
}
