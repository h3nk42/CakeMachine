package control.console.input;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatEventListener;
import control.automat.events.AutomatEventListenerCreate;
import control.console.output.OutputEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("InputEventHandler Test")
public class InputEventHandlerTest {

    InputEventHandler inputEventHandler;

    @Spy
    InputEventListener inputEventListenerSpy;


    @Mock
    InputEvent inputEventMock;

    @BeforeEach
    void setUp() {
        inputEventMock =  Mockito.mock(InputEvent.class);
        when(inputEventMock.getText()).thenReturn(null);

        inputEventHandler = new InputEventHandler();
        inputEventListenerSpy = Mockito.spy(InputEventListener.class);
    }

    @Test
    @DisplayName("add test")
    void addtest() throws Exception {
        inputEventHandler.add(inputEventListenerSpy);
        inputEventHandler.handle(inputEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(inputEventListenerSpy, times(1)).onInputEvent(inputEventMock);
    }

    @Test
    @DisplayName("remove test")
    void removeTest() throws Exception {
        inputEventHandler.add(inputEventListenerSpy);
        inputEventHandler.remove(inputEventListenerSpy);
        inputEventHandler.handle(inputEventMock);
        /* ZUSICHERUNG */
        Mockito.verify(inputEventListenerSpy, times(0)).onInputEvent(inputEventMock);
    }

}
