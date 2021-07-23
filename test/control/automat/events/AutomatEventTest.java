package control.automat.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@DisplayName("AutomatEvent Test")
public class AutomatEventTest {

    AutomatEvent automatEvent;
    HashMap<CakeDataType, Object> tempMap;
    @BeforeEach
    void setUp() {
        tempMap = new HashMap<>();
        automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
    }

    @Test
    @DisplayName("getData test")
    void getDataTest() {
        Map<CakeDataType, Object> actual = automatEvent.getData();
        /* ZUSICHERUNG */
        Map<CakeDataType, Object> expected = tempMap;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getOperationType test")
    void getOperationType() {
        AutomatOperationType actual = automatEvent.getOperationType();
        /* ZUSICHERUNG */
        AutomatOperationType expected = AutomatOperationType.dKuchen;
        Assertions.assertEquals(expected, actual);
    }
}
