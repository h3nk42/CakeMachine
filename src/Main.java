import automat.Automat;
import automat.hersteller.HerstellerFactory;
import automat.hersteller.HerstellerFactoryImpl;
import automat.verkaufsobjekte.kuchen.KremkuchenImpl;
import automat.verkaufsobjekte.kuchen.KuchenArt;
import automat.verkaufsobjekte.kuchen.ObstkuchenImpl;
import automat.verkaufsobjekte.kuchen.ObsttorteImpl;

import java.util.Date;

public class Main {


    public static void main(String[] args){
        HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
        try {
             herstellerFactory.produceHersteller("Krause");
             herstellerFactory.produceHersteller("Martin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Main.java:\n" + herstellerFactory.getHerstellerListe());

        Automat automat = new Automat();
        try {
            Date now = new Date();
            automat.addObject(new KremkuchenImpl( herstellerFactory.getHerstellerListe().get("krause"), now, "vanille"));
            automat.addObject(new ObsttorteImpl(herstellerFactory.getHerstellerListe().get("martin"), now, "apfel", "schoko"));
            automat.addObject(new ObsttorteImpl(herstellerFactory.getHerstellerListe().get("martin"), now, "apfel", "schoko"));
            automat.addObject(new ObsttorteImpl(herstellerFactory.getHerstellerListe().get("martin"), now, "apfel", "schoko"));
            automat.addObject(new ObstkuchenImpl( herstellerFactory.getHerstellerListe().get("krause"), now, "kirsche"));
            automat.removeObject(0);
            automat.removeObject(2);
            automat.addObject(new ObstkuchenImpl( herstellerFactory.getHerstellerListe().get("krause"), now, "kirsche"));
            automat.addObject(new ObstkuchenImpl( herstellerFactory.getHerstellerListe().get("krause"), now, "kirsche"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Main.java:\n krause anzahl kuchen: " + automat.kuchenCounter.get( herstellerFactory.getHerstellerListe().get("krause")));
        System.out.println("Main.java:\n martin anzahl kuchen: " + automat.kuchenCounter.get( herstellerFactory.getHerstellerListe().get("martin")));
        System.out.println("Main.java:\n martin anzahl kuchen: " + automat.getKuchen());
    }
}
