import automat.Automat;
import automat.hersteller.Hersteller;
import automat.hersteller.HerstellerFactory;
import automat.hersteller.HerstellerFactoryImpl;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.kuchen.*;

import java.math.BigDecimal;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        Automat automat = new Automat(5);
        Date now = new Date();
        try {
             automat.addHersteller("Krause");
             automat.addHersteller("Martin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }//    public VerkaufsKuchen createKuchen(KuchenArt kuchenArt, Hersteller hersteller, BigDecimal preis, int naehrwert, Allergen[] allergene, String[] extraData ) throws Exception {

        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"});
        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"});
        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"});
        automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel", "Vanille"});
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());
        System.out.println("Main.java: lineNumber: 30: " + automat.getKuchen().get(2).getFachnummer() );
        automat.removeKuchen(automat.getKuchen().get(1));
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());
        Thread.sleep(5000);
        automat.setInspektionsdatum(automat.getKuchen().get(0), new Date());
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());
    }
}
