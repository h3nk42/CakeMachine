package control.automat.events;

import control.automat.AutomatController;
import control.console.output.OutputEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("AutomatEventHandler Test")
public class AutomatEventHandlerTest {

    AutomatEventHandler automatEventHandler;
    AutomatEventListener automatEventListenerCreate;

    @Spy
    AutomatEventListener automatEventListenerCreateSpy;


    @Mock
    AutomatController automatControllerMock;
    OutputEventHandler outputEventHandlerMock;
    AutomatEvent automatEventMock;

    @BeforeEach
    void setUp() {
        automatEventMock =  Mockito.mock(AutomatEvent.class);
        when(automatEventMock.getData()).thenReturn(null);

        automatControllerMock = Mockito.mock(AutomatController.class);
        outputEventHandlerMock = Mockito.mock(OutputEventHandler.class);
        automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandlerMock, automatControllerMock);
        automatEventListenerCreateSpy = Mockito.spy(automatEventListenerCreate);
        automatEventHandler = new AutomatEventHandler();
    }

    @Test
    @DisplayName("add test")
    void addtest() throws Exception {
        automatEventHandler.add(automatEventListenerCreateSpy);
        automatEventHandler.handle(automatEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(automatEventListenerCreateSpy, times(1)).onAutomatEvent(automatEventMock);
    }

    @Test
    @DisplayName("remove test")
    void removeTest() throws Exception {
        automatEventHandler.add(automatEventListenerCreateSpy);
        automatEventHandler.remove(automatEventListenerCreateSpy);
        automatEventHandler.handle(automatEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(automatEventListenerCreateSpy, times(0)).onAutomatEvent(automatEventMock);
    }



}
