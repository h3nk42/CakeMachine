package model;

import model.hersteller.Hersteller;
import model.hersteller.HerstellerFactory;
import model.hersteller.HerstellerFactoryImpl;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@DisplayName("Automat Tests")
 class AutomatTests {
    @Nested
    @DisplayName("Base - SPY")
    class AutomatTestBase {

        Automat automat;
        Automat spyAutomat;

        @BeforeEach
        void setUp() throws Exception {
            automat = new Automat(2);
            spyAutomat = Mockito.spy(automat);

        }

        @Test
        @DisplayName("READ - Fachanzahl returend correctly")
        void getFachAnzahl() {
            int actual = automat.getFachanzahl();
            /* ZUSICHERUNG */
            int expected = 2;
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("READ - isFull initial false")
        void isFull() {
            boolean actual = automat.isFull();
            /* ZUSICHERUNG */
            boolean expected = false;
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("READ - isEmpty initual true")
        void isEmpty() {
            boolean actual = automat.isEmpty();
            /* ZUSICHERUNG */
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("SPYTEST - READ - isEmpty  should be false")
        void isEmptySpyTest() {
            List<Integer> mockList = new ArrayList<>();
            mockList.add(1);
            Mockito.doReturn(mockList).when(spyAutomat).getKuchen();
            boolean actual = spyAutomat.isEmpty();
            /* ZUSICHERUNG */
            boolean expected = false;
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("SPYTEST - READ - isFull should be true")
        void isFullSpyTest() {
            List<Integer> mockList = new ArrayList<>();
            mockList.add(1);
            mockList.add(1);
            Mockito.doReturn(mockList).when(spyAutomat).getKuchen();
            boolean actual = spyAutomat.isFull();
            /* ZUSICHERUNG */
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }


        @Test
        @DisplayName("toString test")
        void toStringTest() {
            String string = spyAutomat.toString();
            boolean actual = string.contains("Automat{faecher=[null, null], kuchenCounter={}, kuchenMap={") && string.contains("}, allergeneVorhanden={}, herstellerFactory=HerstellerFactoryImpl{herstellerList={}}, inspektionsDaten={}, fachAnzahl=2}");
            /* ZUSICHERUNG */
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }


    }

    @Nested
    @DisplayName("Complex")
    class AutomatTestComplex {

        Automat automat;
        Hersteller hersteller;
        Hersteller herstellerTwo;
        VerkaufsKuchen verkaufsKuchen;
        VerkaufsKuchen verkaufsKuchenTwo;


        @BeforeEach
        void setUp() throws Exception {
            automat = new Automat(2);
            hersteller = automat.createHersteller("rewe");
            herstellerTwo = automat.createHersteller("test");
            verkaufsKuchen = automat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            verkaufsKuchenTwo = automat.createKuchen(KuchenArt.Obsttorte, herstellerTwo, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
        }

        @Test
        @DisplayName("deleteHersteller -> hersteller Cakes get deleted as well")
        void deleteHerstellerCascading() throws Exception {
            automat.deleteHersteller("rewe");
            int actual = automat.getFachnummer(verkaufsKuchen);
            /* ZUSICHERUNG */
            int expected = -1;
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("deleteHersteller -> other hersteller Cakes stay")
        void deleteHerstellerCascadingOnlyForOne() throws Exception {
            automat.deleteHersteller("rewe");
            int actual = automat.getFachnummer(verkaufsKuchenTwo);
            /* ZUSICHERUNG */
            int expected = 1;
            Assertions.assertEquals(expected, actual);
        }

            // delete hersteller -> delete kuchen
    }

    @Nested
    @DisplayName("Hersteller")
    class AutomatTestHersteller {

        Automat automat;


        @BeforeEach
        void setUp() throws Exception {
            automat = new Automat(2);
        }

        @Test
        @DisplayName("READ - getHersteller initial Value is not null")
        void getHerstellerInitialValue() throws Exception {
            Collection<Hersteller> actual = automat.getHersteller();
            /* ZUSICHERUNG */
            Collection<Hersteller> expected = new ArrayList<>();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("CREATE - createHersteller is returning Hersteller")
        void createHerstellerReturn() throws Exception {
            String actual = automat.createHersteller("rewe").getName();
            /* ZUSICHERUNG */
            String expected = "rewe";
            Assertions.assertEquals(expected, actual);
        }

        @Nested
        @DisplayName("after Hersteller was created Tests")
        class AutomatTestHerstellerWithCreated {

            Hersteller hersteller;

            @BeforeEach
            void beforeEach() throws Exception {
                hersteller = automat.createHersteller("test");
            }

            // CREATE / GET
            @Test
            @DisplayName("CREATE - createHersteller is adding to HerstellerList")
            void getHerstellerTest() {
                Collection<Hersteller> actual = automat.getHersteller();
                /* ZUSICHERUNG */
                Collection<Hersteller> expected = new ArrayList<>();
                expected.add(hersteller);
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("READ - getHersteller is returning specific")
            void getHerstellerSpecificTest() {
                Hersteller actual = automat.getHersteller("test");
                /* ZUSICHERUNG */
                Hersteller expected = hersteller;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("READ - getHersteller specific is not caseSensitive")
            void getHerstellerSpecificCaseSensitiveTest() {
                Hersteller actual = automat.getHersteller("TeSt");
                /* ZUSICHERUNG */
                Hersteller expected = hersteller;
                Assertions.assertEquals(expected, actual);
            }
            // DELETE
            // UNHAPPY
            @Test
            @DisplayName("DELETE - deleteHersteller, non existent")
            void deleteHerstellerTestNonExistent() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    automat.deleteHersteller("rewe");
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Hersteller nicht gefunden";
                Assertions.assertTrue(actual.contains(expected));
            }

            // HAPPY
            @Test
            @DisplayName("DELETE - deleteHersteller should remove from List")
            void deleteHerstellerTestSuccess() throws Exception {
                automat.deleteHersteller("test");
                Collection<Hersteller> actual = automat.getHersteller();
                /* ZUSICHERUNG */
                Collection<Hersteller> expected = new ArrayList<>();
                Assertions.assertEquals(expected, actual);
            }

        }


    }
@Nested
    @DisplayName("Kuchen")
    class AutomatTestKuchen {
        Automat automat;

        @Spy
        Automat spyAutomat;

        @BeforeEach
        void setUp() throws Exception {
            automat = new Automat(2);
            spyAutomat = Mockito.spy(automat);
        }

        @Nested
        @DisplayName("getKuchen - Initial")
        class getKuchen {
            @Test
            @DisplayName("READ - getKuchen is empty")
            void getKuchenInitialValue() {
                Collection<VerkaufsKuchen> actual = automat.getKuchen();
                /* ZUSICHERUNG */
                Collection<VerkaufsKuchen> expected = new ArrayList<>();
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("READ - getKuchen(type) is empty")
            void getKuchenTypeInitialValue() {
                Collection<VerkaufsKuchen> actual = automat.getKuchen(KuchenArt.Kremkuchen);
                /* ZUSICHERUNG */
                Collection<VerkaufsKuchen> expected = new ArrayList<>();
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("createKuchen - unhappy")
        class createKuchenUnhappy {
            @Test
            @DisplayName("UNHAPPY - kremkuchen wrong extraData ")
            void createKuchenReturn1() throws Exception {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    VerkaufsKuchen actual = automat.createKuchen(KuchenArt.Kremkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "test"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "only one String in extraData allowed";
                Assertions.assertTrue(actual.contains(expected));
            }
            @Test
            @DisplayName("UNHAPPY - obstkuchen wrong extraData ")
            void createKuchenReturn2() throws Exception {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    VerkaufsKuchen actual = automat.createKuchen(KuchenArt.Obstkuchen, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "test"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "only one String in extraData allowed";
                Assertions.assertTrue(actual.contains(expected));
            }
            @Test
            @DisplayName("UNHAPPY - obsttorte wrong extraData ")
            void createKuchenReturn3() throws Exception {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    VerkaufsKuchen actual = automat.createKuchen(KuchenArt.Obsttorte, automat.getHersteller("Rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "need two strings in extraData";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - hersteller != null but not in automat ")
            void createKuchenHerstellerNonExistent() throws Exception {
                HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
                Hersteller hersteller = herstellerFactory.produceHersteller("rewe");
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.createKuchen(KuchenArt.Kremkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Hersteller des Kuchens nicht im Automaten vorhanden";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - hersteller == null ")
            void createKuchenHerstellerNull() throws Exception {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.createKuchen(KuchenArt.Kremkuchen, null, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Hersteller des Kuchens null";
                Assertions.assertTrue(actual.contains(expected));
            }
            @Test
            @DisplayName("UNHAPPY - automat voll ")
            void createKuchenFull() throws Exception {
                HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
                Hersteller hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.createKuchen(KuchenArt.Kremkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                    spyAutomat.createKuchen(KuchenArt.Kremkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                    spyAutomat.createKuchen(KuchenArt.Kremkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Alle Fächer voll";
                Assertions.assertTrue(actual.contains(expected));
            }
        }
        @Nested
        @DisplayName("createKuchen - happy - SPYTEST")
        class createKuchenHappy {

            Hersteller hersteller;

            @BeforeEach
            void setUp() throws Exception {
                HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
            }

            @Test
            @DisplayName("HAPPY - kremkuchen created ")
            void createKuchenSuccess() throws Exception {
                VerkaufsKuchen actual = spyAutomat.createKuchen(KuchenArt.Kremkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                /* ZUSICHERUNG */
                Assertions.assertNotNull(actual);
            }

            @Test
            @DisplayName("HAPPY - obstkuchen created ")
            void createKuchenSuccess2() throws Exception {
                VerkaufsKuchen actual = spyAutomat.createKuchen(KuchenArt.Obstkuchen, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
                /* ZUSICHERUNG */
                Assertions.assertNotNull(actual);
            }

            @Test
            @DisplayName("HAPPY - obsttorte created ")
            void createKuchenSuccess3() throws Exception {
                VerkaufsKuchen actual = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
                /* ZUSICHERUNG */
                Assertions.assertNotNull(actual);
            }

        }

        @Nested
        @DisplayName("getKuchen - After createKuchen")
        class getKuchenAfterCreate {

            Hersteller hersteller;
            VerkaufsKuchen verkaufsKuchen;
            @BeforeEach
            void setUp() throws Exception {
                HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("READ - getKuchen(0) is correct")
            void getKuchen() {
                VerkaufsKuchen actual = automat.getKuchen().get(0);
                /* ZUSICHERUNG */
                VerkaufsKuchen expected = verkaufsKuchen;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("READ - getKuchen size is 1")
            void getKuchenSize() {
                int actual = automat.getKuchen().size();
                /* ZUSICHERUNG */
                int expected = 1;
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("deleteKuchen")
        class deleteKuchen {

            Hersteller hersteller;
            VerkaufsKuchen verkaufsKuchen;
            @BeforeEach
            void setUp() throws Exception {
                HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("UNHAPPY - deleteKuchen outOfBounds")
            void deleteKuchenOutOfBounds() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.deleteKuchen(3);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Fachnummer ausserhalb der Fachanzahl";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - deleteKuchen already empty")
            void deleteKuchenEmptyIndex() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.deleteKuchen(1);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Fach bereits leer";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("HAPPY - deleteKuchen(index)")
            void deleteKuchenSuccess() throws Exception {
                spyAutomat.deleteKuchen(0);
                int actual = automat.getKuchen().size();
                /* ZUSICHERUNG */
                int expected = 0;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("HAPPY - deleteKuchen(verkaufsKuchen)")
            void deleteKuchenNoIndexSuccess() throws Exception {
                spyAutomat.deleteKuchen(verkaufsKuchen);
                int actual = automat.getKuchen().size();
                /* ZUSICHERUNG */
                int expected = 0;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("UNHAPPY - deleteKuchen(verkaufsKuchen)")
            void deleteKuchenNoIndexExpection() throws Exception {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.deleteKuchen(0);
                    spyAutomat.deleteKuchen(verkaufsKuchen);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Kuchen nicht im Automaten";
                Assertions.assertTrue(actual.contains(expected));
            }
        }

        @Nested
        @DisplayName("getKuchenCounter")
        class getKuchenCounter {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("UNHAPPY - (hersteller) exception hersteller null")
            void getKuchenCounterSpecialNull() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.getKuchenCounter(null);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Hersteller ist null";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - (hersteller) exception hersteller null")
            void getKuchenCounterSpecialNotNull() throws Exception {
                Hersteller hersteller2 = herstellerFactory.produceHersteller("test");
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.getKuchenCounter(hersteller2);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Hersteller des Kuchens nicht im Automaten vorhanden";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("HAPPY - (hersteller) count correct")
            void getKuchenCounterSuccess() throws Exception {
                int actual = spyAutomat.getKuchenCounter(hersteller);
                /* ZUSICHERUNG */
                int expected = 1;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("HAPPY - (hersteller) count correct after Kuchen delete")
            void getKuchenCounterSuccessAfterDelete() throws Exception {
                spyAutomat.deleteKuchen(verkaufsKuchen);
                int actual = spyAutomat.getKuchenCounter(hersteller);
                /* ZUSICHERUNG */
                int expected = 0;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("HAPPY - () correct Map returned")
            void getKuchenCounterGeneral() throws Exception {
                int actual = spyAutomat.getKuchenCounter().get(hersteller);
                /* ZUSICHERUNG */
                int expected = 1;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("HAPPY - () correct Map returned after delete")
            void getKuchenCounterGeneralDelete() throws Exception {
                spyAutomat.deleteKuchen(verkaufsKuchen);
                int actual = spyAutomat.getKuchenCounter().get(hersteller);
                /* ZUSICHERUNG */
                int expected = 0;
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("getAllergene")
        class getAllergene {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("HAPPY - () correct Set returned ")
            void getAllergene() {
                Set<Allergen> actualIn = spyAutomat.getAllergene(true);
                Set<Allergen> actualOut = spyAutomat.getAllergene(false);
                boolean actual = actualIn.contains(Allergen.Gluten) && actualIn.contains(Allergen.Sesamsamen) && actualIn.contains(Allergen.Haselnuss) && actualOut.contains(Allergen.Erdnuss);
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected, actual);
            }

            @Test
            @DisplayName("HAPPY - () correct Set returned after delete ")
            void getAllergeneAfterDelete() throws Exception {
                spyAutomat.deleteKuchen(verkaufsKuchen);
                Set<Allergen> actualIn = spyAutomat.getAllergene(true);
                Set<Allergen> actualOut = spyAutomat.getAllergene(false);
                boolean actual = actualIn.size() == 0 && actualOut.contains(Allergen.Erdnuss) && actualOut.contains(Allergen.Gluten) && actualOut.contains(Allergen.Sesamsamen) && actualOut.contains(Allergen.Haselnuss);
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("getInspektionsdatum")
        class getInspektionsdatum {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;
            Date dateBefore = new Date(Instant.now().minusSeconds(5).getEpochSecond()*1000);

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("HAPPY - inspectionDate correct ")
            void getInspektionsdatum() {
                Date dateAfter = new Date(Instant.now().plusSeconds(5).getEpochSecond()*1000);
                Date actualDate = spyAutomat.getInspektionsdatum(verkaufsKuchen);
                boolean actual = actualDate.before(dateAfter) && actualDate.after(dateBefore);
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected, actual);
            }

        }

        @Nested
        @DisplayName("getFachnummer")
        class getFachnummer {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("HAPPY - getFachnummer correct ")
            void getFachnummer() {
                int actual = spyAutomat.getFachnummer(verkaufsKuchen);
                /* ZUSICHERUNG */
                int expected = 0;
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("swapFachnummer")
        class swapFachnummer {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("UNHAPPY - swapFachnummer index oob ")
            void swapFachnummerOOB2() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.swapFachnummer(3,1);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Index out of bounds";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - swapFachnummer same ")
            void swapFachnummerSame() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.swapFachnummer(1,1);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "need different indices";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - swapFachnummer nullPointer (1,null) ")
            void swapFachnummerNullPointer() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.swapFachnummer(1,0);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Index zeigt auf leeres Fach";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - swapFachnummer nullPointer (null,1) ")
            void swapFachnummerNullPointer2() {
                Exception e = Assertions.assertThrows(Exception.class, () -> {
                    spyAutomat.swapFachnummer(0,1);
                });
                String actual = e.getMessage();
                /* ZUSICHERUNG */
                String expected = "Index zeigt auf leeres Fach";
                Assertions.assertTrue(actual.contains(expected));
            }

            @Test
            @DisplayName("UNHAPPY - swapFachnummer success ")
            void swapFachnummerSuccess() throws Exception {
                spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
                spyAutomat.swapFachnummer(0,1);
                int actual = spyAutomat.getFachnummer(verkaufsKuchen);
                /* ZUSICHERUNG */
                int expected = 1;
                Assertions.assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("aktualisiereInspektionsdatum")
        class aktualisiereInspektionsdatum {

            Hersteller hersteller;
            HerstellerFactory herstellerFactory;
            VerkaufsKuchen verkaufsKuchen;

            @BeforeEach
            void setUp() throws Exception {
                herstellerFactory = new HerstellerFactoryImpl();
                hersteller = herstellerFactory.produceHersteller("rewe");
                Mockito.doReturn(hersteller).when(spyAutomat).getHersteller("rewe");
                verkaufsKuchen = spyAutomat.createKuchen(KuchenArt.Obsttorte, hersteller, BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille", "Apfel"}, 24);
            }

            @Test
            @DisplayName("HAPPY - aktualisiereInspektionsdatum success ")
            void aktualisiereInspektionsdatumSuccess() throws Exception {
                long oldTime = spyAutomat.getInspektionsdatum(verkaufsKuchen).getTime();
                Date oldDate = spyAutomat.getInspektionsdatum(verkaufsKuchen);
                spyAutomat.aktualisiereInspektionsdatum(verkaufsKuchen.getFachnummer());
                long newTime = spyAutomat.getInspektionsdatum(verkaufsKuchen).getTime();
                Date newDate= spyAutomat.getInspektionsdatum(verkaufsKuchen);
                boolean actual = oldTime <= newTime && oldDate != newDate;
                /* ZUSICHERUNG */
                boolean expected = true;
                Assertions.assertEquals(expected, actual);
            }
        }
    }
}
