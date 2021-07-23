package model.verkaufsobjekte;

import model.Automat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VerkaufsObjektImplTest {

    @Mock
    Automat automatMock;

    Verkaufsobjekt verkaufsobjekt;

    @BeforeEach
    void setUp() {
        automatMock = mock(Automat.class);
        verkaufsobjekt = new VerkaufsobjektImpl(BigDecimal.valueOf(10), automatMock);
    }


    @Test
    void getPreisTest()  {
        BigDecimal actual = verkaufsobjekt.getPreis();
        /* ZUSICHERUNG */
        BigDecimal expected = BigDecimal.valueOf(10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getInspektionsdatumTest() {
        Date newDate = new Date();
        when(automatMock.getInspektionsdatum(verkaufsobjekt)).thenReturn(newDate);
        Date actual = verkaufsobjekt.getInspektionsdatum();
        /* ZUSICHERUNG */
        Date expected = newDate;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getFachnummerTest() {
        when(automatMock.getFachnummer(verkaufsobjekt)).thenReturn(42);
        int actual = verkaufsobjekt.getFachnummer();
        /* ZUSICHERUNG */
        int expected = 42;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toStringTest() {
        Date newDate = new Date();
        when(automatMock.getInspektionsdatum(verkaufsobjekt)).thenReturn(newDate);
        when(automatMock.getFachnummer(verkaufsobjekt)).thenReturn(42);
        String actual = verkaufsobjekt.toString();
        /* ZUSICHERUNG */
        String expected = "fachnummer=42, preis=10, inspektionsdatum="+newDate;
        Assertions.assertEquals(expected, actual);
    }

}
