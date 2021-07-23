package control.automat.events;

import control.automat.AutomatController;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import model.Automat;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import testLib.TestStaticVariables;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@DisplayName("AutomatEventListenerDelete Test")
public class AutomatEventListenerDeleteTest {

    AutomatEventListener automatEventListener;

    @Spy
    AutomatController spyAutomatController;
    OutputEventHandler spyOutputEventHandler;
    Automat spyAutomat;


    @Mock
    AutomatEvent automatEventMock;

    @BeforeEach
    void setUp() throws Exception {
        automatEventMock =  Mockito.mock(AutomatEvent.class);
        when(automatEventMock.getData()).thenReturn(null);

        spyAutomat = Mockito.spy(new Automat(10));
        spyAutomat.createHersteller("rewe");

        spyAutomatController = Mockito.spy(new AutomatController(spyAutomat));
        spyOutputEventHandler = Mockito.spy(new OutputEventHandler());

        automatEventListener = new AutomatEventListenerDelete(spyOutputEventHandler, spyAutomatController);
    }


    @Nested
    class deleteKuchen {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.dKuchen);
            spyAutomat.createKuchen(KuchenArt.Kremkuchen, spyAutomat.getHersteller("rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);

        }

        @Test
        @DisplayName("HAPPY - handleEvent dKuchen - call automat")
        void handleEventDKuchen3() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).deleteKuchen((int) tempMap.get(CakeDataType.fachnummer));
        }

        @Test
        @DisplayName("HAPPY - handleEvent dKuchen - call controller KuchenCapacity")
        void handleEventDKuchen43() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomatController, times(1)).aktualisiereKuchenCapacity();
        }

        @Test
        @DisplayName("HAPPY - handleEvent dKuchen - call controller allergene")
        void handleEventDKuchen432() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomatController, times(1)).aktualisiereAllergene();
        }
    }

    @Nested
    class deleteHersteller {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.dHersteller);
        }

        @Test
        @DisplayName("HAPPY - handleEvent dHersteller - call controller refreshHersteller")
        void handleEventDhersteller4322() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnHerstellerMap("rewe");
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomatController, times(1)).aktualisiereHersteller();
        }

        @Test
        @DisplayName("HAPPY - handleEvent dHersteller - call automat deletHersteller")
        void handleEventDhersteller432() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnHerstellerMap("rewe");
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).deleteHersteller("rewe");
        }

    }
}
