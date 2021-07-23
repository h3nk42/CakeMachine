package model.hersteller;

import java.io.Serializable;
import java.util.HashMap;

public class HerstellerFactoryImpl implements HerstellerFactory, Serializable {

    private HashMap<String, Hersteller> herstellerList;

    public HerstellerFactoryImpl() {
        this.herstellerList = new HashMap<String, Hersteller>();
    }

    @Override
    public Hersteller produceHersteller(String _name) throws Exception {
        if(_name.contains(" ")) {
            throw new Exception("Keine Leerzeichen im Herstellernamen");
        }
        if(_name.equals("")) {
            throw new Exception("Keine leeren Herstellernamen");
        }
        if (this.herstellerList.containsKey(_name.toLowerCase())) {
            throw new Exception("Identischer Hersteller schon vorhanden!");
        } else {
            Hersteller tempHersteller = new HerstellerImpl(_name);
            this.herstellerList.put(_name.toLowerCase(), tempHersteller);
            return tempHersteller;
        }
    }
    @Override
    public HashMap<String, Hersteller> getHerstellerList() {
        return herstellerList;
    }

    @Override
    public void deleteHersteller(String herstellerName) throws Exception {
        if(herstellerList.get(herstellerName) == null) {
            throw new Exception("Hersteller nicht vorhanden");
        }
        this.herstellerList.remove(herstellerName.toLowerCase());
    }

    @Override
    public String toString() {
        return "HerstellerFactoryImpl{" +
                "herstellerList=" + herstellerList +
                '}';
    }
}
