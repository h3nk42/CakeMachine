package control.automat.events;

import control.automat.AutomatController;
import control.console.output.MessageType;
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

import static org.mockito.Mockito.*;


@DisplayName("AutomatEventListenerCreate Test")
public class AutomatEventListenerCreateTest {

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

        automatEventListener = new AutomatEventListenerCreate(spyOutputEventHandler, spyAutomatController);
    }

    @DisplayName("createKuchen Test")
    @Nested
    class createKuchen {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.cKuchen);
        }

        @Test
        @DisplayName("HAPPY - handleEvent cKuchen - kremkuchen")
        void handleEventCkuchen() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Kremkuchen);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /* ZUSICHERUNG */
            Mockito.verify(spyAutomat, times(1)).createKuchen(ArgumentMatchers.eq(KuchenArt.Kremkuchen), ArgumentMatchers.any(Hersteller.class), ArgumentMatchers.any(BigDecimal.class), ArgumentMatchers.any(int.class), ArgumentMatchers.any(Allergen[].class), ArgumentMatchers.any(String[].class), ArgumentMatchers.any(Integer.class));
        }

        @Test
        @DisplayName("HAPPY - handleEvent cKuchen - obstKuchen")
        void handleEventCkuchen2() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obstkuchen);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).createKuchen(ArgumentMatchers.eq(KuchenArt.Obstkuchen), ArgumentMatchers.any(Hersteller.class), ArgumentMatchers.any(BigDecimal.class), ArgumentMatchers.any(int.class), ArgumentMatchers.any(Allergen[].class), ArgumentMatchers.any(String[].class), ArgumentMatchers.any(Integer.class));
        }

        @Test
        @DisplayName("HAPPY - handleEvent cKuchen - obstTorte")
        void handleEventCkuchen3() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obsttorte);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).createKuchen(ArgumentMatchers.eq(KuchenArt.Obsttorte), ArgumentMatchers.any(Hersteller.class), ArgumentMatchers.any(BigDecimal.class), ArgumentMatchers.any(int.class), ArgumentMatchers.any(Allergen[].class), ArgumentMatchers.any(String[].class), ArgumentMatchers.any(Integer.class));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent cKuchen - exception kremkuchen")
        void handleEventCkuchenKremleer() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Kremkuchen);
            tempMap.put(CakeDataType.kremsorte, null);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*   ZUSICHERUNG */
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener,"Kremsorte leer", MessageType.error));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent cKuchen - exception obstkuchen")
        void handleEventCkuchenObstleer() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obstkuchen);
            tempMap.put(CakeDataType.obstsorte, null);
            when(automatEventMock.getData()).thenReturn(tempMap);
                automatEventListener.onAutomatEvent(automatEventMock);
            /*   ZUSICHERUNG */
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener,"Obstsorte leer", MessageType.error));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent cKuchen - exception obsttorte")
        void handleEventCkuchenObstKremleer() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obsttorte);
            tempMap.put(CakeDataType.obstsorte, null);
            when(automatEventMock.getData()).thenReturn(tempMap);
                automatEventListener.onAutomatEvent(automatEventMock);
            /*   ZUSICHERUNG */
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener,"Obstsorte | Kremsorte leer", MessageType.error));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent cKuchen - exception unknownSorte")
        void handleEventCkuchenUnknownArt() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Unknown);
            tempMap.put(CakeDataType.obstsorte, null);
            when(automatEventMock.getData()).thenReturn(tempMap);
                automatEventListener.onAutomatEvent(automatEventMock);

            /*   ZUSICHERUNG */
            String expected = "Kuchenart nicht erkannt";
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener,"Kuchenart nicht erkannt", MessageType.error));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent cKuchen - exception herstellerUnbekannt")
        void handleEventCkuchenUnknownHersteller() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obstkuchen);
            tempMap.put(CakeDataType.hersteller, "rolf");
            when(automatEventMock.getData()).thenReturn(tempMap);
                automatEventListener.onAutomatEvent(automatEventMock);
            /*   ZUSICHERUNG */
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener,"Hersteller nicht erkannt", MessageType.error));
        }
    }
    @DisplayName("createHersteller Test")
    @Nested
    class createHersteller {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.cHersteller);
        }

        @Test
        @DisplayName("HAPPY - handleEvent cHersteller aufruf am automat")
        void handleEventChersteller3() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnHerstellerMap("test");
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).createHersteller("test");
        }

        @Test
        @DisplayName("HAPPY - handleEvent cHersteller aufruf automatController")
        void handleEventChersteller4() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnHerstellerMap("test");
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomatController, times(1)).aktualisiereHersteller();
        }
    }
}
