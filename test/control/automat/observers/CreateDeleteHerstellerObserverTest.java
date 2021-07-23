package control.automat.observers;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
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

@DisplayName("CreateDeleteHerstellerObserver Test")
public class CreateDeleteHerstellerObserverTest {

    CreateDeleteHerstellerObserver createDeleteHerstellerObserver;

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

        createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(spyAutomatController, spyOutputEventHandler, spyUpdateGuiEventHandler);
        spyAutomat.createHersteller("rewe");

    }

    @Test
    @DisplayName("HAPPY - aktualisiere after create")
    void aktualisiereTest() throws Exception {
        createDeleteHerstellerObserver.aktualisiere();
        /*ZUSICHERUNG*/
        Mockito.verify(spyOutputEventHandler, times(1)).handle(new OutputEvent(createDeleteHerstellerObserver, "Hersteller hinzugefügt", MessageType.warning));
    }

    @Test
    @DisplayName("HAPPY - aktualisiere after delete")
    void aktualisiereTestDelete() throws Exception {
        spyAutomat.deleteHersteller("rewe");
        createDeleteHerstellerObserver.aktualisiere();
        /*ZUSICHERUNG*/
        Mockito.verify(spyOutputEventHandler, times(0)).handle(new OutputEvent(createDeleteHerstellerObserver, "Hersteller entfernt", MessageType.warning));
    }
}
