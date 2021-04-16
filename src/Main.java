import automat.Automat;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.kuchen.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        Automat automat = new Automat(5);
        Date now = new Date();
        try {
             automat.createHersteller("Krause");
             automat.createHersteller("Martin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }//    public VerkaufsKuchen createKuchen(KuchenArt kuchenArt, Hersteller hersteller, BigDecimal preis, int naehrwert, Allergen[] allergene, String[] extraData ) throws Exception {

        /*automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);
        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);
        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);
        automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel", "Vanille"});
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());
        System.out.println("Main.java: lineNumber: 30: " + automat.getKuchen().get(2).getFachnummer() );
        automat.removeKuchen(automat.getKuchen().get(1));
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());
        Thread.sleep(5000);
        automat.setInspektionsdatum(automat.getKuchen().get(0), new Date());
        System.out.println("Main.java: lineNumber: 29: " + automat.getKuchen());*/

       /* Obstkuchen obstkuchen = (Obstkuchen) automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);
        System.out.println("Main.java: lineNumber: 41: " + obstkuchen.getFachnummer());
        Obstkuchen obstkuchen2 = (Obstkuchen) automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);
        System.out.println("Main.java: lineNumber: 41: " + obstkuchen2.getFachnummer());
        automat.deleteKuchen((VerkaufsKuchen) obstkuchen2);
        System.out.println("Main.java: lineNumber: 41: " + obstkuchen2.getFachnummer());

        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        System.out.println("Main.java: lineNumber: 48: " + arr.indexOf(1));
        arr.remove(0);*/
        Obstkuchen obstkuchen = (Obstkuchen) automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Erdnuss, Allergen.Gluten}, new String[] {"Apfel"},24);

        Duration d = obstkuchen.getHaltbarkeit().minusHours(12);
        System.out.println("Main.java: lineNumber: 51: " + d);
        System.out.println("Main.java: lineNumber: 49: " + obstkuchen.getHaltbarkeit());
        System.out.println("Main.java: lineNumber: 51: " + automat.getKuchen().get(0).getHaltbarkeit().getSeconds()/60/60);

        System.out.println("Main.java: lineNumber: 56: " + automat.getAllergene(false));
        automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Krause"),BigDecimal.valueOf(1.25), 333, new Allergen[] {Allergen.Haselnuss, Allergen.Sesamsamen}, new String[] {"Apfel"},24);
        System.out.println("Main.java: lineNumber: 56: " + automat.getAllergene(false));





    }
}
