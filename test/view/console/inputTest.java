package view.console;

import control.console.input.InputEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@DisplayName("Input Test")
class inputTest {
    Input input;


    @BeforeEach
    void setUp() {
        input = new Input();
    }

    @Test
    @DisplayName("readInput test")
    void readInputTest() {
        System.setIn(new ByteArrayInputStream("test".getBytes()));
        String actual = input.readInput();
        /* ZUSICHERUNG */
        String expected = "test";
        Assertions.assertEquals(expected, actual);
    }
}
