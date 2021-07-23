package control.automat.observers;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.AutomatEventListenerUpdate;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import control.gui.event.UpdateGuiEventHandler;
import model.Automat;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("AllergeneObserver Test")
public class AllergeneObserverTest {

    AllergeneObserver allergeneObserver;

    @Spy
    AutomatController spyAutomatController;
    OutputEventHandler spyOutputEventHandler;
    UpdateGuiEventHandler spyUpdateGuiEventHandler;
    Automat spyAutomat;


    @Mock
    AutomatEvent automatEventMock;

    @BeforeEach
    void setUp() throws Exception {
        automatEventMock = Mockito.mock(AutomatEvent.class);
        when(automatEventMock.getData()).thenReturn(null);

        spyAutomat = Mockito.spy(new Automat(10));

        spyAutomatController = Mockito.spy(new AutomatController(spyAutomat));
        spyOutputEventHandler = Mockito.spy(new OutputEventHandler());
        spyUpdateGuiEventHandler = Mockito.spy(new UpdateGuiEventHandler());

        allergeneObserver = new AllergeneObserver(spyAutomatController, spyOutputEventHandler, spyUpdateGuiEventHandler);
        spyAutomat.createHersteller("rewe");
        spyAutomat.createKuchen(KuchenArt.Kremkuchen, spyAutomat.getHersteller("rewe"), BigDecimal.valueOf(1.99), 300, new Allergen[]{Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[]{"Vanille"}, 24);

    }

    @Test
    @DisplayName("HAPPY - aktualisiere after create")
    void aktualisiereTest() throws Exception {
        spyAutomatController.aktualisiereAllergene();
        allergeneObserver.aktualisiere();

        /*ZUSICHERUNG*/
        Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(allergeneObserver, "Allergene haben sich verändert!", MessageType.warning));
    }

    @Test
    @DisplayName("HAPPY - aktualisiere after delete")
    void aktualisiereTestDelete() throws Exception {
        spyAutomat.deleteKuchen(0);
        spyAutomatController.aktualisiereAllergene();
        allergeneObserver.aktualisiere();
        /*ZUSICHERUNG*/
        Mockito.verify(spyOutputEventHandler, times(0)).handle(new OutputEvent(allergeneObserver, "Allergene haben sich verändert!", MessageType.warning));
    }
}
