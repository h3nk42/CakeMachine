package view.console;

import control.console.input.InputEvent;
import control.console.input.InputEventHandler;
import control.console.input.InputEventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.times;

@DisplayName("Reader Test")
class readerTest {
    Reader reader;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    InputEventHandler inputEventHandler;

    @Spy
    InputEventHandler spyHandler;


    @BeforeEach
    void setUp() throws Exception {
        inputEventHandler = new InputEventHandler();
        spyHandler = Mockito.spy(inputEventHandler);
        reader = new Reader(spyHandler,false);
    }

    @Test
    @DisplayName("readStart test")
    void readStartTest() {
        System.setIn(new ByteArrayInputStream("test".getBytes()));
        reader.start();
        InputEvent inputEvent1 = new InputEvent(reader, "", InputEventType.print);
        InputEvent inputEvent2 = new InputEvent(reader, "test", InputEventType.read);
        /* ZUSICHERUNG */
        Mockito.verify(spyHandler,times(1)).handle(inputEvent1);
        Mockito.verify(spyHandler,times(1)).handle(inputEvent2);
    }
}
