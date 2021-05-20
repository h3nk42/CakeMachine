package view.gui;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.OperationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;


public class FeController {


    private AutomatEventHandler automatEventHandler;
    /* HERSTELLER AREA */
    @FXML
    private TextArea herstellerInputField ;
    private String herstellerName;

    @FXML
    private void printHelloWorld(ActionEvent event) {
        event.consume();
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, this.herstellerName);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, OperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
        AutomatEvent readEvent = new AutomatEvent(this, tempMap, OperationType.rHersteller);
        automatEventHandler.handle(readEvent);
    }

    public void setAutomatEventHandler(AutomatEventHandler automatEventHandler) {
        this.automatEventHandler = automatEventHandler;
    }

    public void initialize() {
        herstellerInputField.textProperty().addListener((obs, oldText, newText) -> {
            this.herstellerName = newText;
        });
    }

}
