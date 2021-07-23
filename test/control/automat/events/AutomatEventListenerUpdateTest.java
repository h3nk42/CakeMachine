package control.automat.events;

import control.automat.AutomatController;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
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
public class AutomatEventListenerUpdateTest {

    AutomatEventListener automatEventListener;

    @Spy
    AutomatController spyAutomatController;
    OutputEventHandler spyOutputEventHandler;
    UpdateGuiEventHandler spyUpdateGuiEventHandler;
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
        spyUpdateGuiEventHandler = Mockito.spy(new UpdateGuiEventHandler());

        automatEventListener = new AutomatEventListenerUpdate(spyOutputEventHandler,spyUpdateGuiEventHandler, spyAutomatController);

        spyAutomat.createHersteller("rewe");
        spyAutomat.createKuchen(KuchenArt.Kremkuchen, spyAutomat.getHersteller("rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);
        spyAutomat.createKuchen(KuchenArt.Kremkuchen, spyAutomat.getHersteller("rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);

    }


    @Nested
    class swapKuchen {
        @BeforeEach
        void setUp() throws Exception {
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.swapKuchen);
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0,1);
            tempMap.put(CakeDataType.bool, true);
            when(automatEventMock.getData()).thenReturn(tempMap);
        }

        @Test
        @DisplayName("HAPPY - handleEvent swapKuchen")
        void handleEventSwap() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).swapFachnummer(0,1);
        }

    }

    @Nested
    class inspectKuchen {
        @BeforeEach
        void setUp() throws Exception {
            Map<CakeDataType, Object> tempMap = TestStaticVariables.TestMaps.returnFachnummerMap(0);
            tempMap.put(CakeDataType.bool, true);
            when(automatEventMock.getData()).thenReturn(tempMap);
            when(automatEventMock.getOperationType()).thenReturn(AutomatOperationType.inspectKuchen);
        }

        @Test
        @DisplayName("HAPPY - handleEvent inspectKuchen")
        void handleEventInspect() throws Exception {
            automatEventListener.onAutomatEvent(automatEventMock);
            /*ZUSICHERUNG*/
            Mockito.verify(spyAutomat, times(1)).aktualisiereInspektionsdatum(0);
        }


    }

}
