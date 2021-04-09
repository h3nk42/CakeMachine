package tests;

import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.kuchen.Kremkuchen;
import automat.verkaufsobjekte.kuchen.KremkuchenImpl;
import automat.verkaufsobjekte.kuchen.KuchenArt;
import automat.verkaufsobjekte.kuchen.VerkaufsKuchen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

public class Tests {

    static Automat automat;
    static Map<String, Hersteller> herstellerMap;
    static Date now = new Date();

    @BeforeAll
    static void setUp() {
        automat = new Automat(10);

    }

    @Test
    void testCreateHersteller() {
        try {
            automat.addHersteller("Chris");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals("Chris",automat.getHersteller("chris").getName());
    }

    @Test
    void createKremkuchen() throws Exception {
        VerkaufsKuchen kremkuchen = new KremkuchenImpl( automat.getHersteller("Chris"), now, "vanille");
        automat.addKuchen(kremkuchen);
        Assertions.assertEquals(1, automat.getFaecher().size());
        Assertions.assertEquals(KuchenArt.Kremkuchen, automat.getFaecher().get(0).getKuchenArt());
        Assertions.assertEquals(automat.getHersteller("Chris") , automat.getFaecher().get(0).getHersteller());
        Assertions.assertEquals(automat.getHersteller("Chris") , automat.getFaecher().get(0).getHersteller());
        Assertions.assertEquals("vanille" , ((Kremkuchen)automat.getFaecher().get(0)).getKremsorte());

    }

}
