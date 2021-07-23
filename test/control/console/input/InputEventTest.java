package control.console.input;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatOperationType;
import control.automat.events.CakeDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("InputEvent Test")
public class InputEventTest {

    InputEvent inputEvent;
    HashMap<CakeDataType, Object> tempMap;
    @BeforeEach
    void setUp() {
        inputEvent = new InputEvent(this, "test", InputEventType.print);
    }

    @Test
    @DisplayName("getData test")
    void getDataTest() {
        String actual = inputEvent.getText();
        /* ZUSICHERUNG */
        String expected = "test";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getOperationType test")
    void getOperationType() {
        InputEventType actual = inputEvent.getInputEventType();
        /* ZUSICHERUNG */
        InputEventType expected = InputEventType.print;
        Assertions.assertEquals(expected, actual);
    }
}
