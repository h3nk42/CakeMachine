package model.automat.hersteller;

import java.io.Serializable;
import java.util.HashMap;

public class HerstellerFactoryImpl implements HerstellerFactory, Serializable {

    private HashMap<String, Hersteller> herstellerListe;

    public HerstellerFactoryImpl() {
        this.herstellerListe = new HashMap<String, Hersteller>();
    }

    @Override
    public Hersteller produceHersteller(String _name) throws Exception {
        if(_name.contains(" ")) {
            throw new Exception("Keine Leerzeichen im Herstellernamen");
        }
        if(_name.equals("")) {
            throw new Exception("Keine leeren Herstellernamen");
        }
        if (this.herstellerListe.containsKey(_name.toLowerCase())) {
            throw new Exception("Identischer Hersteller schon vorhanden!");
        } else {
            Hersteller tempHersteller = new HerstellerImpl(_name);
            this.herstellerListe.put(_name.toLowerCase(), tempHersteller);
            return tempHersteller;
        }
    }
    @Override
    public HashMap<String, Hersteller> getHerstellerListe() {
        return herstellerListe;
    }

    @Override
    public void deleteHersteller(String herstellerName) {
        this.herstellerListe.remove(herstellerName.toLowerCase());
    }

    @Override
    public String toString() {
        return "HerstellerFactoryImpl{" +
                "herstellerListe=" + herstellerListe +
                '}';
    }
}
