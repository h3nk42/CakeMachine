package control.gui;

import control.automat.events.CakeDataType;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("InputEvent Test")
public class UpdateGuiEventTest {

    UpdateGuiEvent updateGuiEvent;
    HashMap<CakeDataType, Object> tempMap;

    @BeforeEach
    void setUp() {

        tempMap = new HashMap<>();
        updateGuiEvent = new UpdateGuiEvent(this, tempMap, GuiEventType.allergenData);
    }

    @Test
    @DisplayName("getData test")
    void getDataTest() {
        Map<CakeDataType, Object> actual = updateGuiEvent.getData();
        /* ZUSICHERUNG */
        Map<CakeDataType, Object> expected = tempMap;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getOperationType test")
    void getOperationType() {
        GuiEventType actual = updateGuiEvent.getGuiEventType();
        /* ZUSICHERUNG */
        GuiEventType expected = GuiEventType.allergenData;
        Assertions.assertEquals(expected, actual);
    }
}
