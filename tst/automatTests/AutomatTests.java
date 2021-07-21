
package automatTests;

import model.Automat;
import model.hersteller.Hersteller;
import model.hersteller.HerstellerFactory;
import model.hersteller.HerstellerFactoryImpl;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class AutomatTests {

    Automat automat;
    Map<String, Hersteller> herstellerMap;
    Date now = new Date();

    @Mock
    Hersteller herstellerMock;


    @BeforeEach
    void setUp() throws Exception {
        automat = new Automat(10);
        automat.createHersteller("Rewe");
        automat.createHersteller("Lidl");
    }

    /* HERSTELLER ---------------------------------------------------------------------------------------------------------  */
    // HERSTELLER --- CREATE
    @Test
    void createHersteller() throws Exception {
        automat.createHersteller("Test");
        Assertions.assertEquals("Test", automat.getHersteller("test").getName());
        Assertions.assertEquals(3, automat.getHersteller().size());
    }

    @Test
    void createHerstellerSameName() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.createHersteller("Rewe");
        },"Identischer Hersteller schon vorhanden!");
        Assertions.assertTrue(e.getMessage().contains("Identischer Hersteller schon vorhanden!"));
    }

    // HERSTELLER --- READ
    @Test
    void getHerstellerSpecific() {
        Assertions.assertEquals("Rewe", automat.getHersteller("Rewe").getName());
        Assertions.assertEquals("Lidl", automat.getHersteller("Lidl").getName());
    }

    @Test
    void getHerstellerNonCaseSensitive() {
        Assertions.assertEquals("Rewe", automat.getHersteller("ReWE").getName());
        Assertions.assertEquals("Lidl", automat.getHersteller("LiDL").getName());
    }

    @Test
    void getHerstellerListe() {
        Assertions.assertEquals(2, automat.getHersteller().size());
    }

    @Test
    void getHerstellerKuchenAnzahl() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        VerkaufsKuchen obsttorteRewe2 = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(2, automat.getKuchenCounter(automat.getHersteller("ReWE")));
        Assertions.assertEquals(0, automat.getKuchenCounter(automat.getHersteller("lidl")));
    }

    // HERSTELLER --- DELETE
    @Test
    void deleteHerstellerIgnoreCase() {
        try {
            automat.deleteHersteller("rEWe");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(null, automat.getHersteller("Rewe"));
    }

    @Test
    void deleteHerstellerWithKuchen() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        automat.deleteHersteller("Rewe");
        Assertions.assertEquals(1, automat.getKuchen().size());
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(1, automat.getKuchen().size());
        Assertions.assertEquals(-1, obsttorteRewe.getFachnummer());
    }


    /* KUCHEN --------------------------------------------------------------------------------------------------------- */
    // KUCHEN --- CREATE

    @Test
    void createKuchenMoreThanFaecherCount() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            for (int i = 0; i < 11; i++) {
                automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
            }
        },"Alle Fächer voll");
        Assertions.assertTrue(e.getMessage().contains("Alle Fächer voll"));
    }
    @Test
    void createKremkuchen() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(kremkuchen, automat.getKuchen().get(0));

    }
    @Test
    void createObstkuchen() throws Exception {
        VerkaufsKuchen obstkuchen = automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel"},36);
        Assertions.assertEquals(KuchenArt.Obstkuchen, automat.getFaecher().get(0).getKuchenArt());
    }

    @Test
    void createObsttorte() throws Exception {
        VerkaufsKuchen obststorte = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(KuchenArt.Obsttorte, automat.getFaecher().get(0).getKuchenArt());
    }

    @Test
    void createMultipleKuchen() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        VerkaufsKuchen obsttorte = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        VerkaufsKuchen obstkuchen = automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel"},36);
        Assertions.assertEquals(obsttorte, automat.getFaecher().get(obsttorte.getFachnummer()));
        Assertions.assertEquals(obstkuchen, automat.getFaecher().get(obstkuchen.getFachnummer()));
        Assertions.assertEquals(kremkuchen, automat.getFaecher().get(kremkuchen.getFachnummer()));
        automat.deleteKuchen(0);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, kremkuchen.getFachnummer());
    }

    @Test
    void createKuchenWithoutHersteller() throws Exception {
        HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
        Hersteller testHersteller = herstellerFactory.produceHersteller("harry");
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.createKuchen(KuchenArt.Obsttorte, testHersteller, BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        },"Hersteller des Kuchens nicht existent");
        Assertions.assertTrue(e.getMessage().contains("Hersteller des Kuchens nicht existent"));

    }

    @Test
    void createKuchenAfterHerstellerDeleted() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(2, automat.getKuchen().size());
        automat.deleteHersteller("Rewe");
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorteRewe.getFachnummer());
        Assertions.assertEquals(obsttorteLidl, automat.getFaecher().get(obsttorteLidl.getFachnummer()));
        Assertions.assertEquals(1, automat.getKuchen().size());
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        },"Kuchen benoetigt Hersteller");
        Assertions.assertTrue(e.getMessage().contains("Kuchen benoetigt Hersteller"));
    }

    @Test
    void refillFachAfterDelete() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        automat.deleteKuchen(obsttorteRewe);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        VerkaufsKuchen obsttorteLidl2 = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(obsttorteLidl2, automat.getFaecher().get(0));
    }

    @Test
    void createDeleteKuchenFaecherCount() throws Exception {
        for (int i = 0; i < 10; i++) {
            automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        }
        for (int i = 0; i < 9; i++) {
            automat.deleteKuchen(i);
        }
        for (int i = 0; i < 9; i++) {
            automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        }
        Assertions.assertEquals(10, automat.getKuchen().size());
    }

    // KUCHEN --- READ --------------------------------------------------------------------------------------------------------- */
    // IM AUTOMAT
    @Test
    void getKuchenWithoutArt() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(kremkuchen, automat.getKuchen().get(0));
        Assertions.assertEquals(1, automat.getKuchen().size());
    }
    @Test
    void getKuchenWithArt() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(new ArrayList<VerkaufsKuchen>(), automat.getKuchen(KuchenArt.Obstkuchen));
        Assertions.assertEquals(1, automat.getKuchen(KuchenArt.Kremkuchen).size());
    }
    @Test
    void getInspektionsdatum() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Date inspektionsdatum = automat.getKuchen(KuchenArt.Kremkuchen).get(0).getInspektionsdatum();
        Assertions.assertEquals(inspektionsdatum, automat.getKuchen(KuchenArt.Kremkuchen).get(0).getInspektionsdatum());
    }
    @Test
    void getFachnummer() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Integer fachnummer = automat.getKuchen(KuchenArt.Kremkuchen).get(0).getFachnummer();
        Assertions.assertEquals(0, fachnummer);
    }
    @Test
    void getAllergene() throws Exception {
        Assertions.assertEquals(true, automat.getAllergene(true).isEmpty());
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten}, new String[] {"Vanille"},24);
        Assertions.assertEquals(Allergen.Haselnuss, kremkuchen.getAllergene().toArray()[0]);
        Assertions.assertEquals(Allergen.Gluten, kremkuchen.getAllergene().toArray()[1]);
        Assertions.assertTrue(automat.getAllergene(true).contains(Allergen.Haselnuss));
        Assertions.assertTrue(automat.getAllergene(true).contains(Allergen.Gluten));
        Assertions.assertTrue(automat.getAllergene(false).contains(Allergen.Sesamsamen));
        Assertions.assertTrue(automat.getAllergene(false).contains(Allergen.Erdnuss));
       /* Assertions.assertEquals(1, model.automat.getAllergeneVorhanden().get(Allergen.Gluten));
        Assertions.assertEquals(1, model.automat.getAllergeneVorhanden().get(Allergen.Haselnuss));
        Assertions.assertEquals(1, model.automat.getAllergeneVorhanden().get(Allergen.Sesamsamen));
        Assertions.assertEquals(null, model.automat.getAllergeneVorhanden().get(Allergen.Erdnuss));*/

    }
    @Test
    void getAllergeneAfterKuchenRemoved() throws Exception {
        Assertions.assertEquals(true, automat.getAllergene(true).isEmpty());
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        automat.deleteKuchen(kremkuchen);
        /*Assertions.assertEquals(null, model.automat.getAllergeneVorhanden().get(Allergen.Haselnuss));
        Assertions.assertEquals(null, model.automat.getAllergeneVorhanden().get(Allergen.Gluten));
        Assertions.assertEquals(null, model.automat.getAllergeneVorhanden().get(Allergen.Sesamsamen));*/
    }
    @Test
    void getFaecher() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(kremkuchen, automat.getFaecher().get(0));
        automat.deleteKuchen(kremkuchen);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
    }


    // KUCHEN ALLEINE

    //TODO
    @Test
    void getHaltbarkeit() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(24, automat.getFaecher().get(0).getHaltbarkeit().getSeconds()/60/60);
    }
    @Test
    void getHersteller() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals(automat.getHersteller("Rewe"), automat.getFaecher().get(0).getHersteller());
    }

    // MOCKITO TEST 1
    @Test
    void getHerstellerMock() throws Exception {
        Hersteller herstellerMock = mock(Hersteller.class);
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.getKuchenCounter(herstellerMock);
        },"Hersteller unbekannt");
        Assertions.assertTrue(e.getMessage().contains("Hersteller unbekannt"));
    }

    @Test
    void getKremsorte() throws Exception {
        VerkaufsKuchen kremkuchen = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Vanille"},24);
        Assertions.assertEquals("Vanille", ((Kremkuchen) automat.getFaecher().get(0)).getKremsorte());
    }
    @Test
    void getObstsorte() throws Exception {
        VerkaufsKuchen obstkuchenRewe = automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel"},36);
        Assertions.assertEquals("Apfel", ((Obstkuchen) automat.getFaecher().get(0)).getObstsorte());
    }
    @Test
    void getObstKremsorte() throws Exception {
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals("Apfel", ((Obsttorte) automat.getFaecher().get(0)).getObstsorte());
        Assertions.assertEquals("Vanille", ((Obsttorte) automat.getFaecher().get(0)).getKremsorte());
    }
    @Test
    void getPreis() throws Exception {
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(BigDecimal.valueOf(1.25), ((VerkaufsKuchen) automat.getFaecher().get(0)).getPreis());
    }
    @Test
    void getNaehrwert() throws Exception {
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(300, ((VerkaufsKuchen) automat.getFaecher().get(0)).getNaehrwert());
    }
    @Test
    void getKuchenArt() throws Exception {
        VerkaufsKuchen obsttorteLidl = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Lidl"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(KuchenArt.Obsttorte, ((VerkaufsKuchen) automat.getFaecher().get(0)).getKuchenArt());
    }

    // KUCHEN --- DELETE --------------------------------------------------------------------------------------------------------- */
    // MOCKITO TEST 2
    @Test
    void deleteKuchenNotInAutomat() throws Exception {
        VerkaufsKuchen vk = mock(VerkaufsKuchen.class);
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            automat.deleteKuchen(vk);
        },"Kuchen nicht im Automaten");
        Assertions.assertTrue(e.getMessage().contains("Kuchen nicht im Automaten"));
    }

    @Test
    void deleteKuchenWithIndex() throws Exception {
        VerkaufsKuchen obsttorte = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(obsttorte, automat.getFaecher().get(obsttorte.getFachnummer()));
        automat.deleteKuchen(0);
        Assertions.assertEquals(null, automat.getFaecher().get(0));
        Assertions.assertEquals(-1, obsttorte.getFachnummer());
    }

    @Test
    void deleteKuchenNoIndex() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Assertions.assertEquals(1, automat.getKuchen().size());
        Assertions.assertEquals(obsttorteRewe, automat.getFaecher().get(obsttorteRewe.getFachnummer()));
        automat.deleteKuchen(obsttorteRewe);
        Assertions.assertEquals(0, automat.getKuchen().size());
        Assertions.assertEquals(null, automat.getFaecher().get(0));
    }

    // KUCHEN --- UPDATE --------------------------------------------------------------------------------------------------------- */
    @Test
    void updateKuchenInspektionsdatum() throws Exception {
        VerkaufsKuchen obsttorteRewe = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        Date oldDate = obsttorteRewe.getInspektionsdatum();
        Date newDate = new Date();
        automat.aktualisiereInspektionsdatum(0);
        Assertions.assertEquals(false, oldDate == obsttorteRewe.getInspektionsdatum());
        Assertions.assertEquals(true, newDate == obsttorteRewe.getInspektionsdatum());
    }

}

