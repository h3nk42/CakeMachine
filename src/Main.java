import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.kuchen.KremkuchenImpl;
import automat.verkaufsobjekte.kuchen.ObstkuchenImpl;

import java.util.Date;
import java.util.Map;

public class Main {


    public static void main(String[] args){
        Automat automat = new Automat(10);
        try {
             automat.addHersteller("Krause");
             automat.addHersteller("Martin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Date now = new Date();
            automat.addKuchen(new KremkuchenImpl( automat.getHersteller("krause"), now, "vanille"));
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("krause"), now, "apfel"));
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("krause"), now, "apfel"));
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("krause"), now, "apfel"));
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("krause"), now, "apfel"));
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("krause"), now, "apfel"));
            automat.removeKuchen(0);
            automat.removeKuchen(2);
            automat.removeKuchen(5);
            automat.addKuchen(new ObstkuchenImpl( automat.getHersteller("martin"), now, "apfel"));

            System.out.println("Main.java: lineNumber: 39: faecher: " + automat.getFaecher());
            System.out.println("Main.java: lineNumber: 36: " + automat.getKuchen());
            System.out.println("Main.java: lineNumber: 37 allergene: " + automat.getAllergeneVorhanden());
            automat.removeHersteller("Krause");
            System.out.println("Main.java: lineNumber: 41" + automat.getKuchen());
            System.out.println("Main.java: lineNumber: 42 allergene: " + automat.getAllergeneVorhanden());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
