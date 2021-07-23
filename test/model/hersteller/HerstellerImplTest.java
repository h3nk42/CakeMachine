package model.hersteller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HerstellerImplTest {

    HerstellerFactory herstellerFactory;
    Hersteller hersteller;

    @BeforeEach
    void setUp() throws Exception {
        herstellerFactory = new HerstellerFactoryImpl();
        hersteller = herstellerFactory.produceHersteller("rewe");
    }

    /* getName ---------------------------------------------------------------------------------------------------------  */
    @Test
    void getName() throws Exception {
        /* ZUSICHERUNG */
        String expected = "rewe";
        Assertions.assertEquals(expected, hersteller.getName());
    }
    /*---------------------------------------------------------------------------------------------------------*/

    /* getName ---------------------------------------------------------------------------------------------------------  */
    @Test
    void toStringTest() throws Exception {
        /* ZUSICHERUNG */
        String expected = "rewe";
        Assertions.assertEquals(expected, hersteller.toString());
    }
    /*---------------------------------------------------------------------------------------------------------*/
}
