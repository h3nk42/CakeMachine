package control.console.output;

import control.automat.events.CakeDataType;
import control.console.input.InputEvent;
import control.console.input.InputEventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@DisplayName("InputEvent Test")
public class OutputEventTest {

    OutputEvent outputEvent;
    HashMap<CakeDataType, Object> tempMap;
    @BeforeEach
    void setUp() {
        outputEvent = new OutputEvent(this, "test", MessageType.error);
    }

    @Test
    @DisplayName("getData test")
    void getDataTest() {
        String actual = outputEvent.getText();
        /* ZUSICHERUNG */
        String expected = "test";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getOperationType test")
    void getOperationType() {
        MessageType actual = outputEvent.getType();
        /* ZUSICHERUNG */
        MessageType expected = MessageType.error;
        Assertions.assertEquals(expected, actual);
    }
}
