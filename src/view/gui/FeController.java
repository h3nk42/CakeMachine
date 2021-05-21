package view.gui;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.AutomatOperationType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import view.gui.events.GuiEventType;
import view.gui.events.UpdateGuiEvent;
import view.gui.events.UpdateGuiEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static control.console.AutomatConsole.extractAllergene;


public class FeController implements UpdateGuiEventListener {


    private AutomatEventHandler automatEventHandler;
    private Collection<Hersteller> herstellerList;

    /* HERSTELLER AREA */
    @FXML
    private TextArea herstellerInputField ;
    private String herstellerName;
    private String selectedHerstellerName = "";
    @FXML
    private ListView herstellerView;

    /* KUCHEN AREA */
    @FXML
    private Spinner preisSpinner;
    @FXML
    private Spinner naehrwertSpinner;
    @FXML
    private Spinner haltbarkeitSpinner;
    @FXML
    private TextArea kremsorteInput ;
    private String kremsorte = "";

    @FXML
    private TextArea obstsorteInput ;
    private String obstsorte = "";

    @FXML
    private TextArea allergeneInput;
    private String allergene = "";
    @FXML
    private Label kremsorteLabel;
    @FXML
    private Label obstsorteLabel;
    @FXML
    private RadioButton kremkuchenButton;
    @FXML
    private RadioButton obstkuchenButton;
    @FXML
    private RadioButton obsttorteButton;
    private KuchenArt kuchenArt = KuchenArt.Kremkuchen;

    @FXML
    private void createHerstellerButtonHandler(ActionEvent event) {
        event.consume();
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, this.herstellerName);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
      /*  AutomatEvent readEvent = new AutomatEvent(this, tempMap, AutomatOperationType.rHersteller);
        automatEventHandler.handle(readEvent);*/
    }

    @FXML
    private void deleteHerstellerButtonHandler(ActionEvent event) {
        event.consume();
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, this.selectedHerstellerName);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dHersteller);
        automatEventHandler.handle(automatEvent);
    }

    @FXML
    private void createKuchenButtonHandler(ActionEvent event) {
        event.consume();
        try {
            extractAllergene(this.allergene);
        } catch (Exception e) {
            System.out.println("view/gui/FeController.java: lineNumber: 98: " + e.getMessage());
        }
       /* Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, this.selectedHerstellerName);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dHersteller);
        automatEventHandler.handle(automatEvent);*/
    }

    @FXML
    private void kremkuchenButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Kremkuchen;
        obsttorteButton.setSelected(false);
        obstkuchenButton.setSelected(false);
        kremsorteInput.setVisible(true);
        kremsorteLabel.setVisible(true);
        obstsorteLabel.setVisible(false);
        obstsorteInput.setVisible(false);
    }
    @FXML
    private void obstkuchenButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Obstkuchen;
        obsttorteButton.setSelected(false);
        kremkuchenButton.setSelected(false);
        obstsorteLabel.setVisible(true);
        obstsorteInput.setVisible(true);
        kremsorteInput.setVisible(false);
        kremsorteLabel.setVisible(false);
    }
    @FXML
    private void obsttorteButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Obsttorte;
        obstkuchenButton.setSelected(false);
        kremkuchenButton.setSelected(false);
        obstsorteLabel.setVisible(true);
        obstsorteInput.setVisible(true);
        kremsorteInput.setVisible(true);
        kremsorteLabel.setVisible(true);
    }


    public void setAutomatEventHandler(AutomatEventHandler automatEventHandler) {
        this.automatEventHandler = automatEventHandler;
    }


    public void initialize() {
        herstellerInputField.textProperty().addListener((obs, oldText, newText) -> {
            this.herstellerName = newText;
        });
        herstellerView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               selectedHerstellerName = newValue;
            }
        });
        preisSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
           if((double)newVal-(double)oldVal == 1){
               preisSpinner.getValueFactory().setValue((double)newVal + 9);
           }else if((double)newVal-(double)oldVal == -1){
               preisSpinner.getValueFactory().setValue((double)newVal - 9);
           }
        });
        naehrwertSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if((double)newVal-(double)oldVal == 1){
                naehrwertSpinner.getValueFactory().setValue((double)newVal + 24);
            }else if((double)newVal-(double)oldVal == -1){
                naehrwertSpinner.getValueFactory().setValue((double)newVal - 24);
            }
        });
        haltbarkeitSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if((double)newVal-(double)oldVal == 1){
                haltbarkeitSpinner.getValueFactory().setValue((double)newVal + 7);
            }else if((double)newVal-(double)oldVal == -1){
                haltbarkeitSpinner.getValueFactory().setValue((double)newVal - 7);
            }
        });
        kremsorteInput.textProperty().addListener((obs, oldText, newText) -> {
            this.kremsorte = newText;
        });
        obstsorteInput.textProperty().addListener((obs, oldText, newText) -> {
            this.obstsorte = newText;
        });
        allergeneInput.textProperty().addListener((obs, oldText, newText) -> {
            this.allergene = newText;
        });
    }

    @Override
    public void onUpdateGuiEvent(UpdateGuiEvent event) {
        GuiEventType guiEventType = event.getGuiEventType();
        switch (guiEventType){
            case herstellerData:
                this.herstellerList = (Collection<Hersteller>) event.getData().get(DataType.hersteller);
                this.updateHerstellerView();
                break;
            case kuchenData:
                break;
        }

    }

    private void updateHerstellerView() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Hersteller hersteller : this.herstellerList) {
           items.add(hersteller.getName());
        }
        this.herstellerView.setItems(items);
    }
}
