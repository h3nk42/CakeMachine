package control.automat.events;

import control.automat.AutomatController;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import model.Automat;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import testLib.TestStaticVariables;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@DisplayName("AutomatEventListenerRead Test")
public class AutomatEventListenerReadTest {

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

        spyAutomatController = Mockito.spy(new AutomatController(spyAutomat));
        spyOutputEventHandler = Mockito.spy(new OutputEventHandler());

        automatEventListener = new AutomatEventListenerRead(spyOutputEventHandler, spyAutomatController);

        Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
        tempMap.put(CakeDataType.bool, true);
        when(automatEventMock.getData()).thenReturn(tempMap);
    }


    @Nested
    class readHersteller {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.rHersteller);
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent readHersteller - emptyHersteller")
        void handleEventReadHersteller() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener, "keine Hersteller gefunden", MessageType.error));
        }

        @Test
        @DisplayName("HAPPY - handleEvent readHersteller")
        void handleEventReadHersteller2() throws Exception {
            spyAutomat.createHersteller("rewe");
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(ArgumentMatchers.any(OutputEvent.class));
        }

    }

    @Nested
    class readKuchen {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.rKuchen);
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent readKuchen - emptyKuchen Type")
        void handleEventReadKuchen2() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnTestMap(KuchenArt.Obstkuchen);
            tempMap.put(CakeDataType.kuchenart, KuchenArt.Obstkuchen);
            when(automatEventMock.getData()).thenReturn(tempMap);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener, "keine Kuchen dieses Typs vorhanden", MessageType.error));
        }

        @Test
        @DisplayName("UNHAPPY - handleEvent readKuchen - emptyKuchen general")
        void handleEventReadKuchen3() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(automatEventListener, "keine Kuchen vorhanden", MessageType.error));
        }

        @Test
        @DisplayName("HAPPY - handleEvent readKuchen - success general")
        void handleEventReadKuchen4() throws Exception {
            spyAutomat.createHersteller("rewe");
            spyAutomat.createKuchen(KuchenArt.Kremkuchen, spyAutomat.getHersteller("rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(ArgumentMatchers.any(OutputEvent.class));
        }
    }

    @Nested
    class readAllergene {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.rAllergene);
        }
        @Test
        @DisplayName("HAPPY - handleEvent readAllergene - success")
        void handleEventReadKuchen4() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyOutputEventHandler, times(1)).handle(ArgumentMatchers.any(OutputEvent.class));
        }

    }
}
