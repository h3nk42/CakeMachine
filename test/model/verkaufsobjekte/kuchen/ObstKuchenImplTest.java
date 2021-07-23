package model.verkaufsobjekte.kuchen;

import model.Automat;
import model.hersteller.Hersteller;
import model.hersteller.HerstellerFactory;
import model.hersteller.HerstellerFactoryImpl;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.Verkaufsobjekt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObstKuchenImplTest {

        Obstkuchen kuchen;
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
            kuchen = new ObstkuchenImpl(hersteller, "Apfel", allergene, BigDecimal.valueOf(1.99), 300,automatMock,haltbarkeit);
            when(automatMock.getInspektionsdatum((Verkaufsobjekt) kuchen)).thenReturn(newDate);
            when(automatMock.getFachnummer((Verkaufsobjekt) kuchen)).thenReturn(42);
        }

    @Test
    void getObstSorteTest()  {
        String actual = kuchen.getObstsorte();
        /* ZUSICHERUNG */
        String expected = "Apfel";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getKuchenArtTest()  {
        VerkaufsKuchen vk = (VerkaufsKuchen) kuchen;
        KuchenArt actual = vk.getKuchenArt();
        /* ZUSICHERUNG */
        KuchenArt expected = KuchenArt.Obstkuchen;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toStringTest() {
        String actual = kuchen.toString();
        /* ZUSICHERUNG */
        String expected = "fachnummer=42, preis=1.99, inspektionsdatum="+newDate+"kuchenArt=Obstkuchen, hersteller=rewe, naehrwert=300, Hltbr verbleibend (h):24, allergene=[Haselnuss, Gluten, Sesamsamen], Obstsorte=Apfel";
        Assertions.assertEquals(expected, actual);
    }
}
