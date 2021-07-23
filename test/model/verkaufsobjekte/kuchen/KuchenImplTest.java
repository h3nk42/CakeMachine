package model.verkaufsobjekte.kuchen;

import model.Automat;
import model.hersteller.Hersteller;
import model.hersteller.HerstellerFactory;
import model.hersteller.HerstellerFactoryImpl;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.Verkaufsobjekt;
import model.verkaufsobjekte.VerkaufsobjektImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KuchenImplTest {

    Kuchen kuchen;
    int haltbarkeit = 24;
    Allergen[] allergene = new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen};
    Hersteller hersteller;
    Date newDate = new Date();
    @Mock
    Automat automatMock;


    @BeforeEach
    void setUp() throws Exception {
        automatMock = mock(Automat.class);
        HerstellerFactory herstellerFactory = new HerstellerFactoryImpl();
        hersteller = herstellerFactory.produceHersteller("rewe");
        kuchen = new KremkuchenImpl(hersteller, "vanille", allergene,BigDecimal.valueOf(1.99), 300,automatMock,haltbarkeit);
        when(automatMock.getInspektionsdatum((Verkaufsobjekt) kuchen)).thenReturn(newDate);
        when(automatMock.getFachnummer((Verkaufsobjekt) kuchen)).thenReturn(42);
    }

    @Test
    void getHerstellerTest()  {
        Hersteller actual = kuchen.getHersteller();
        /* ZUSICHERUNG */
        Hersteller expected = hersteller;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllergeneTest()  {
        ArrayList<Allergen> actualAllergene = kuchen.getAllergene();
        boolean actual = actualAllergene.contains(Allergen.Haselnuss) && actualAllergene.contains(Allergen.Gluten) && actualAllergene.contains(Allergen.Sesamsamen);
        /* ZUSICHERUNG */
        boolean expected = true;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getNaehrwertTest()  {
        int actual = kuchen.getNaehrwert();
        /* ZUSICHERUNG */
        int expected = 300;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHaltbarkeitTest() {
        Duration duration = kuchen.getHaltbarkeit();
        boolean actual = duration.minusSeconds(haltbarkeit*60*60).getSeconds() == 0;
        /* ZUSICHERUNG */
        boolean expected = true;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toStringTest() {
        String actual = kuchen.toString();
        /* ZUSICHERUNG */
        String expected = "fachnummer=42, preis=1.99, inspektionsdatum="+newDate+"kuchenArt=Kremkuchen, hersteller=rewe, naehrwert=300, Hltbr verbleibend (h):24, allergene=[Haselnuss, Gluten, Sesamsamen], Kremsorte=vanille";
        Assertions.assertEquals(expected, actual);
    }
}
