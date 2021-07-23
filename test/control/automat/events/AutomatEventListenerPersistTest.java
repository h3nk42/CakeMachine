package control.automat.events;

import control.automat.AutomatController;
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


@DisplayName("AutomatEventListenerPersist Test")
public class AutomatEventListenerPersistTest {

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

        automatEventListener = new AutomatEventListenerPersist(spyOutputEventHandler, spyAutomatController);

        Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
        tempMap.put(CakeDataType.bool, true);
        when(automatEventMock.getData()).thenReturn(tempMap);
    }


    @Nested
    class persistJos {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.pJOS);
        }

        @Test
        @DisplayName("HAPPY - handleEvent pJOS - call automat")
        void handleEventDKuchen3() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).wasPersisted();
        }

    }

    @Nested
    class loadJos {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.lJOS);
        }

        @Test
        @DisplayName("HAPPY - handleEvent pJOS - call automat")
        void handleEventDKuchen3() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomatController, times(1)).rehydrate(ArgumentMatchers.any(Automat.class));
        }

    }
}
