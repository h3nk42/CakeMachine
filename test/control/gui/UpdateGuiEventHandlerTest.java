package control.gui;

import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import control.console.output.OutputEventListener;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventHandler;
import control.gui.event.UpdateGuiEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("InputEventHandler Test")
public class UpdateGuiEventHandlerTest {

    UpdateGuiEventHandler updateGuiEventHandler;

    @Spy
    UpdateGuiEventListener updateGuiEventListenerSpy;


    @Mock
    UpdateGuiEvent updateGuiEventMock;

    @BeforeEach
    void setUp() {
        updateGuiEventMock =  Mockito.mock(UpdateGuiEvent.class);
        when(updateGuiEventMock.getData()).thenReturn(null);

        updateGuiEventHandler = new UpdateGuiEventHandler();
        updateGuiEventListenerSpy = Mockito.spy(UpdateGuiEventListener.class);
    }

    @Test
    @DisplayName("add test")
    void addtest() throws Exception {
        updateGuiEventHandler.add(updateGuiEventListenerSpy);
        updateGuiEventHandler.handle(updateGuiEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(updateGuiEventListenerSpy, times(1)).onUpdateGuiEvent(updateGuiEventMock);
    }

    @Test
    @DisplayName("remove test")
    void removeTest() throws Exception {
        updateGuiEventHandler.add(updateGuiEventListenerSpy);
        updateGuiEventHandler.remove(updateGuiEventListenerSpy);
        updateGuiEventHandler.handle(updateGuiEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(updateGuiEventListenerSpy, times(0)).onUpdateGuiEvent(updateGuiEventMock);
    }

}
