package AutomatTests;

import automat.Automat;
import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.kuchen.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AutomatTests {

    Automat automat;
    Map<String, Hersteller> herstellerMap;
    Date now = new Date();

    @Mock
    Hersteller herstellerMock;


    @BeforeEach
    void setUp() throws Exception {
        automat = new Automat(10);
        automat.addHersteller("Rewe");
        automat.addHersteller("Lidl");
    }

  /*  @Test
    public void testMock() {
        when(herstellerMock.getName()).thenReturn("mockmock");
        System.out.println("AutomatTests/AutomatTests.java: lineNumber: 39: " + herstellerMock.getName());
    }*/

    //Good
    @Test
    void CreateHersteller() {
        Assertions.assertEquals("Rewe", automat.getHersteller("Rewe").getName());
        Assertions.assertEquals("Lidl", automat.getHersteller("lidl").getName());
        Assertions.assertEquals(2, automat.getHersteller().size());
    }


    //Bad
    @Test
    void createHerstellerSameName() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.addHersteller("Rewe");
        },"Identischer Hersteller schon vorhanden!");
        Assertions.assertTrue(e.getMessage().contains("Identischer Hersteller schon vorhanden!"));
    }

    //Good
    @Test
    void createKremkuchen() throws Exception {
        VerkaufsKuchen kremkuchen = new KremkuchenImpl(automat.getHersteller("Rewe"), now, "vanille");
        automat.addKuchen(kremkuchen);
        Assertions.assertEquals(1, automat.getFaecher().size());
        Assertions.assertEquals(KuchenArt.Kremkuchen, automat.getFaecher().get(0).getKuchenArt());
        Assertions.assertEquals(automat.getHersteller("Rewe"), automat.getFaecher().get(0).getHersteller());
        Assertions.assertEquals("vanille", ((Kremkuchen) automat.getFaecher().get(0)).getKremsorte());
        Assertions.assertEquals(BigDecimal.valueOf(1.75), automat.getFaecher().get(0).getPreis());
    }

    //Good
    @Test
    void createObstkuchen() throws Exception {
        VerkaufsKuchen obstkuchen = new ObstkuchenImpl(automat.getHersteller("Rewe"), now, "Apfel");
        automat.addKuchen(obstkuchen);
        Assertions.assertEquals(1, automat.getFaecher().size());
        Assertions.assertEquals(KuchenArt.Obstkuchen, automat.getFaecher().get(0).getKuchenArt());
        Assertions.assertEquals(automat.getHersteller("Rewe"), automat.getFaecher().get(0).getHersteller());
        Assertions.assertEquals("Apfel", ((Obstkuchen) automat.getFaecher().get(0)).getObstsorte());
        Assertions.assertEquals(BigDecimal.valueOf(1.5), automat.getFaecher().get(0).getPreis());
    }

    //Good
    @Test
    void createObsttorte() throws Exception {
        VerkaufsKuchen obststorte = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        automat.addKuchen(obststorte);
        Assertions.assertEquals(1, automat.getFaecher().size());
        Assertions.assertEquals(KuchenArt.Obsttorte, automat.getFaecher().get(0).getKuchenArt());
        Assertions.assertEquals(automat.getHersteller("Rewe"), automat.getFaecher().get(0).getHersteller());
        Assertions.assertEquals("Apfel", ((Obsttorte) automat.getFaecher().get(0)).getObstsorte());
        Assertions.assertEquals("Schokolade", ((Obsttorte) automat.getFaecher().get(0)).getKremsorte());
        Assertions.assertEquals(BigDecimal.valueOf(2.25), automat.getFaecher().get(0).getPreis());
    }

    //Good
    @Test
    void createMultipleKuchen() throws Exception {
        VerkaufsKuchen obsttorte = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        VerkaufsKuchen obstkuchen = new ObstkuchenImpl(automat.getHersteller("Rewe"), now, "Apfel");
        VerkaufsKuchen kremkuchen = new KremkuchenImpl(automat.getHersteller("Rewe"), now, "vanille");
        automat.addKuchen(obsttorte);
        automat.addKuchen(obstkuchen);
        automat.addKuchen(kremkuchen);
        Assertions.assertEquals(3, automat.getFaecher().size());
        Assertions.assertEquals(obsttorte, automat.getFaecher().get(obsttorte.getFachnummer()));
        Assertions.assertEquals(obstkuchen, automat.getFaecher().get(obstkuchen.getFachnummer()));
        Assertions.assertEquals(kremkuchen, automat.getFaecher().get(kremkuchen.getFachnummer()));
        automat.removeKuchen(0);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorte.getFachnummer());
    }

    @Test
    void deleteKuchenWithIndex() throws Exception {
        VerkaufsKuchen obsttorte = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorte);
        Assertions.assertEquals(obsttorte, automat.getFaecher().get(obsttorte.getFachnummer()));
        automat.removeKuchen(0);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorte.getFachnummer());
    }

    @Test
    void deleteHersteller() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        VerkaufsKuchen obstkuchenRewe = new ObstkuchenImpl(automat.getHersteller("Rewe"), now, "Apfel");
        VerkaufsKuchen kremkuchenRewe = new KremkuchenImpl(automat.getHersteller("Rewe"), now, "vanille");
        VerkaufsKuchen obsttorteLidl = new ObsttorteImpl(automat.getHersteller("Lidl"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        automat.addKuchen(obstkuchenRewe);
        automat.addKuchen(kremkuchenRewe);
        automat.addKuchen(obsttorteLidl);
        Assertions.assertEquals(4, automat.getKuchen().size());
        automat.removeHersteller("Rewe");
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(null, automat.getFaecher().get(1));
        Assertions.assertEquals(null, automat.getFaecher().get(2));
        Assertions.assertEquals(-1, obsttorteRewe.getFachnummer());
        Assertions.assertEquals(-1, obstkuchenRewe.getFachnummer());
        Assertions.assertEquals(-1, kremkuchenRewe.getFachnummer());
        Assertions.assertEquals(1, automat.getKuchen().size());
    }

    //bad
    @Test
    void reAddKuchenAfterHerstellerDeleted() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        VerkaufsKuchen obsttorteLidl = new ObsttorteImpl(automat.getHersteller("Lidl"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        automat.addKuchen(obsttorteLidl);
        Assertions.assertEquals(2, automat.getKuchen().size());
        automat.removeHersteller("Rewe");
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorteRewe.getFachnummer());
        Assertions.assertEquals(obsttorteLidl, automat.getFaecher().get(obsttorteLidl.getFachnummer()));
        Assertions.assertEquals(1, automat.getKuchen().size());
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.addKuchen(obsttorteRewe);
        },"Hersteller des Kuchens nicht existent");
        Assertions.assertTrue(e.getMessage().contains("Hersteller des Kuchens nicht existent"));
    }

    //Good
    @Test
    void refillFachAfterDelete() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        VerkaufsKuchen obsttorteLidl = new ObsttorteImpl(automat.getHersteller("Lidl"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        automat.addKuchen(obsttorteLidl);
        Assertions.assertEquals(2, automat.getKuchen().size());
        automat.removeKuchen(0);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorteRewe.getFachnummer());
        Assertions.assertEquals(obsttorteLidl, automat.getFaecher().get(obsttorteLidl.getFachnummer()));
        Assertions.assertEquals(1, automat.getKuchen().size());
        automat.addKuchen(obsttorteRewe);
        Assertions.assertEquals(obsttorteRewe, automat.getFaecher().get(obsttorteRewe.getFachnummer()));
    }

    //good
    @Test
    void deleteKuchenNoIndex() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        Assertions.assertEquals(1, automat.getKuchen().size());
        Assertions.assertEquals(obsttorteRewe, automat.getFaecher().get(obsttorteRewe.getFachnummer()));
        automat.removeKuchen(obsttorteRewe);
        Assertions.assertEquals(0, automat.getKuchen().size());
        Assertions.assertEquals(null, automat.getFaecher().get(0));
    }

    //good
    @Test
    void getAllergeneAdd() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        VerkaufsKuchen obsttorteRewe2 = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        Assertions.assertEquals(Allergen.Gluten, obsttorteRewe.getAllergene().toArray()[0]);
        Assertions.assertEquals(Allergen.Erdnuss, obsttorteRewe.getAllergene().toArray()[1]);
        Assertions.assertEquals(true, automat.getAllergeneVorhanden().isEmpty());
        automat.addKuchen(obsttorteRewe2);
        Assertions.assertEquals(1, automat.getAllergeneVorhanden().get(Allergen.Gluten));
        Assertions.assertEquals(1, automat.getAllergeneVorhanden().get(Allergen.Erdnuss));
    }

    //bad
    @Test
    void duplicateCakeAdd() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.addKuchen(obsttorteRewe);
        }, "Kuchen bereits im Automaten");
        Assertions.assertTrue(e.getMessage().contains("Kuchen bereits im Automaten"));
    }

    @Test
    void getAllergeneRemoveCake() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        automat.addKuchen(obsttorteRewe);
        automat.removeKuchen(obsttorteRewe);
        Assertions.assertEquals(true, automat.getAllergeneVorhanden().isEmpty());
    }

    @Test
    void deleteNonExistentCake() throws Exception {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.removeKuchen(obsttorteRewe);
        }, "Kuchen bereits im Automaten");
        Assertions.assertTrue(e.getMessage().contains("Kuchen nicht im Automaten"));
    }

    @Test
    void updateKuchenInspektionsdatum() {
        VerkaufsKuchen obsttorteRewe = new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade");
        Date oldDate = obsttorteRewe.getInspektionsdatum();
        Date newDate = new Date();
        obsttorteRewe.setInspektionsdatum(newDate);
        Assertions.assertEquals(false, oldDate == obsttorteRewe.getInspektionsdatum());
        Assertions.assertEquals(true, newDate == obsttorteRewe.getInspektionsdatum());
    }

    @Test
    void getKuchenArt() throws Exception {
        ArrayList<VerkaufsKuchen> kuchen = new ArrayList<>();
        kuchen.add(new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade"));
        kuchen.add(new ObstkuchenImpl(automat.getHersteller("Rewe"), now, "Apfel"));
        kuchen.add(new KremkuchenImpl(automat.getHersteller("Rewe"), now, "vanille"));
        kuchen.add(new ObsttorteImpl(automat.getHersteller("Lidl"), now, "Apfel", "Schokolade"));
        automat.addKuchen(kuchen);
        Assertions.assertEquals(2, automat.getKuchen(KuchenArt.Obsttorte).size());
        Assertions.assertEquals(1, automat.getKuchen(KuchenArt.Obstkuchen).size());
        Assertions.assertEquals(1, automat.getKuchen(KuchenArt.Kremkuchen).size());
    }

    @Test
    void getHerstellerCounter() throws Exception {
        ArrayList<VerkaufsKuchen> kuchen = new ArrayList<>();
        kuchen.add(new ObsttorteImpl(automat.getHersteller("Rewe"), now, "Apfel", "Schokolade"));
        kuchen.add(new ObstkuchenImpl(automat.getHersteller("Rewe"), now, "Apfel"));
        kuchen.add(new KremkuchenImpl(automat.getHersteller("Rewe"), now, "vanille"));
        kuchen.add(new ObsttorteImpl(automat.getHersteller("Lidl"), now, "Apfel", "Schokolade"));
        automat.addKuchen(kuchen);
        Assertions.assertEquals(3, automat.getKuchenCounter(automat.getHersteller("Rewe")));
    }

}
