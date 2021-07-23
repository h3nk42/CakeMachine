package control.console.output;

import control.console.input.InputEvent;
import control.console.input.InputEventHandler;
import control.console.input.InputEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("InputEventHandler Test")
public class OutputEventHandlerTest {

    OutputEventHandler outputEventHandler;

    @Spy
    OutputEventListener outputEventListenerSpy;


    @Mock
    OutputEvent outputEventMock;

    @BeforeEach
    void setUp() {
        outputEventMock =  Mockito.mock(OutputEvent.class);
        when(outputEventMock.getText()).thenReturn(null);

        outputEventHandler = new OutputEventHandler();
        outputEventListenerSpy = Mockito.spy(OutputEventListener.class);
    }

    @Test
    @DisplayName("add test")
    void addtest() throws Exception {
        outputEventHandler.add(outputEventListenerSpy, true);
        outputEventHandler.handle(outputEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(outputEventListenerSpy, times(1)).onOutputEvent(outputEventMock);
    }

    @Test
    @DisplayName("remove test")
    void removeTest() throws Exception {
        outputEventHandler.add(outputEventListenerSpy, true);
        outputEventHandler.remove(outputEventListenerSpy);
        outputEventHandler.handle(outputEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(outputEventListenerSpy, times(0)).onOutputEvent(outputEventMock);
    }

}
