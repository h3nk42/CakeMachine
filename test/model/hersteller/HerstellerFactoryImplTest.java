package model.hersteller;

import model.Automat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class HerstellerFactoryImplTest {

    HerstellerFactoryImpl herstellerFactory;

    @BeforeEach
    void setUp() throws Exception {
        herstellerFactory = new HerstellerFactoryImpl();
    }

    /* CREATE HERSTELLER ---------------------------------------------------------------------------------------------------------  */
    /* HAPPY ---------------------------------------------------------------------------------------------------------  */
    @Test
    void createHersteller() throws Exception {
        Hersteller hersteller = herstellerFactory.produceHersteller("rewe");
        /* ZUSICHERUNG */
        String expected = "rewe";
        Assertions.assertEquals(expected, hersteller.getName());
    }

    /* UNHAPPY ---------------------------------------------------------------------------------------------------------  */
    @Test
    void failCreateHerstellerWithSpace() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            herstellerFactory.produceHersteller("rewe ");
        });
        /* ZUSICHERUNG */
        String expected = "Keine Leerzeichen im Herstellernamen";
        Assertions.assertTrue(e.getMessage().contains(expected));
    }
    @Test
    void failCreateHerstellerIdentical() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            herstellerFactory.produceHersteller("rewe");
        });
        /* ZUSICHERUNG */
        String expected = "Identischer Hersteller schon vorhanden!";
        Assertions.assertTrue(e.getMessage().contains(expected));
    }
    @Test
    void failCreateHerstellerIdenticalCaseSensitive() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            herstellerFactory.produceHersteller("ReWe");
        });
        /* ZUSICHERUNG */
        String expected = "Identischer Hersteller schon vorhanden!";
        Assertions.assertTrue(e.getMessage().contains(expected));
    }
    @Test
    void failCreateHerstellerEmptyString() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            herstellerFactory.produceHersteller("");
        });
        /* ZUSICHERUNG */
        String expected = "Keine leeren Herstellernamen";
        Assertions.assertTrue(e.getMessage().contains(expected));
    }
    /*---------------------------------------------------------------------------------------------------------*/

    /* getHerstellerList ---------------------------------------------------------------------------------------------------------  */
    @Test
    void getHerstellerCountEmpty() throws Exception {
        HashMap<String, Hersteller> herstellerList = herstellerFactory.getHerstellerList();
        int actual = herstellerFactory.getHerstellerList().size();
        /* ZUSICHERUNG */
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHerstellerCountMultiple() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        herstellerFactory.produceHersteller("rewe2");
        int actual = herstellerFactory.getHerstellerList().size();
        /* ZUSICHERUNG */
        int expected = 2;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHerstellerByString() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        HashMap<String, Hersteller> herstellerList = herstellerFactory.getHerstellerList();
        Hersteller actualHersteller = herstellerList.get("rewe");
        String actual = actualHersteller.getName();
        /* ZUSICHERUNG */
        String expected = "rewe";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHerstellerByStringIsCaseSensitive() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        HashMap<String, Hersteller> herstellerList = herstellerFactory.getHerstellerList();
        Hersteller actual = herstellerList.get("ReWe");
        /* ZUSICHERUNG */
        Hersteller expected = null;
        Assertions.assertEquals(expected, actual);
    }
    /*---------------------------------------------------------------------------------------------------------*/

    /* deleteHersteller ---------------------------------------------------------------------------------------------------------  */
    @Test
    void deleteHersteller() throws Exception {
        herstellerFactory.produceHersteller("rewe");
        herstellerFactory.deleteHersteller("rewe");
        int actual = herstellerFactory.getHerstellerList().size();
        /* ZUSICHERUNG */
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void failDeleteHerstellerNotExistent() {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            herstellerFactory.deleteHersteller("rewe");
        });
        /* ZUSICHERUNG */
        String expected = "Hersteller nicht vorhanden";
        Assertions.assertTrue(e.getMessage().contains(expected));
    }
    /*---------------------------------------------------------------------------------------------------------*/

    /*toString*/
    @Test
    void toStringTest() {
        String actual = herstellerFactory.toString();
        /* ZUSICHERUNG */
        String expected = "HerstellerFactoryImpl{herstellerList={}}";
        Assertions.assertEquals(expected, actual);
    }
    /**/

}
