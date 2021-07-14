package view.gui;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.AutomatOperationType;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lib.ConsoleLib;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenComparators;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;
import view.gui.events.GuiEventType;
import view.gui.events.UpdateGuiEvent;
import view.gui.events.UpdateGuiEventListener;

import java.math.BigDecimal;
import java.util.*;

import static control.console.AutomatConsole.extractAllergene;


public class FeController implements UpdateGuiEventListener {


    private AutomatEventHandler automatEventHandler;
    private ArrayList<VerkaufsKuchen> kuchenList;
    private Map<Hersteller, Integer> herstellerMap = new HashMap<>();
    private Set<Allergen>[] allergeneVorhanden;

    @FXML
    private AnchorPane container ;

    /* ALLERGENE AREA */
    @FXML
    private TextArea allergeneView;

    /* HERSTELLER AREA */
    @FXML
    private TextArea herstellerInputField ;
    private SimpleStringProperty herstellerName = new SimpleStringProperty();
    private String selectedHerstellerName = "";
    @FXML
    private ListView herstellerView;
    @FXML
    private ListView kuchenView;
    private Integer selectedKuchenFachnummer;

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
    @FXML
    private RadioButton fachnummerSortButton;
    @FXML
    private RadioButton herstellerSortButton;
    @FXML
    private RadioButton inspectSortButton;
    @FXML
    private RadioButton haltbarkeitSortButton;

    private KuchenArt kuchenArt = KuchenArt.Kremkuchen;
    private BigDecimal preis = BigDecimal.valueOf(0.1);
    private int naehrwert = 100;
    private int haltbarkeit = 8;

    private SortType selectedSort = SortType.fachnummer;

    private boolean vorhandeneAllergeneAnzeigen = false;

    private int dragStartFachnummer;

    @FXML
    private void createHerstellerButtonHandler(ActionEvent event) {
        event.consume();
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, this.herstellerName.getValue());
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
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
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.kuchenart, this.kuchenArt);
        tempMap.put(DataType.preis, preis);
        tempMap.put(DataType.naehrwert, naehrwert);
        tempMap.put(DataType.haltbarkeit, haltbarkeit);
        tempMap.put(DataType.hersteller, selectedHerstellerName);
        AutomatEvent cKuchenEvent;
        try {
            Allergen[] allergene = extractAllergene(this.allergene);
            tempMap.put(DataType.allergene, allergene);
            switch (this.kuchenArt) {
                case Kremkuchen:
                    tempMap.put(DataType.kremsorte, this.kremsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
                case Obstkuchen:
                    tempMap.put(DataType.obstsorte, this.obstsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
                case Obsttorte:
                    tempMap.put(DataType.kremsorte, this.kremsorte);
                    tempMap.put(DataType.obstsorte, this.obstsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
            }
        } catch (Exception e) {
            System.out.println("view/gui/FeController.java: lineNumber: 98: " + e.getMessage());
        }
    }

    @FXML
    private void deleteKuchenButtonHandler(ActionEvent event) {
        event.consume();
        if (selectedKuchenFachnummer != null) {
            System.out.println("view/gui/FeController.java: lineNumber: 155: " + "delete" + selectedKuchenFachnummer);
            Map<DataType, Object> tempMap = new HashMap<>();
            tempMap.put(DataType.fachnummer, selectedKuchenFachnummer);
            AutomatEvent dKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
            automatEventHandler.handle(dKuchenEvent);
        }
    }

    @FXML
    private void kremkuchenButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Kremkuchen;

        kremkuchenButton.setSelected(true);
        obsttorteButton.setSelected(false);
        obstkuchenButton.setSelected(false);

        kremkuchenButton.setDisable(true);
        obsttorteButton.setDisable(false);
        obstkuchenButton.setDisable(false);

        kremsorteInput.setVisible(true);
        kremsorteLabel.setVisible(true);
        obstsorteLabel.setVisible(false);
        obstsorteInput.setVisible(false);
    }
    @FXML
    private void obstkuchenButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Obstkuchen;

        obstkuchenButton.setSelected(true);
        obsttorteButton.setSelected(false);
        kremkuchenButton.setSelected(false);

        obstkuchenButton.setDisable(true);
        obsttorteButton.setDisable(false);
        kremkuchenButton.setDisable(false);

        obstsorteLabel.setVisible(true);
        obstsorteInput.setVisible(true);
        kremsorteInput.setVisible(false);
        kremsorteLabel.setVisible(false);
    }


    @FXML
    private void obsttorteButtonHandler(ActionEvent event) {
        event.consume();
        this.kuchenArt = KuchenArt.Obsttorte;

        obsttorteButton.setSelected(true);
        obstkuchenButton.setSelected(false);
        kremkuchenButton.setSelected(false);

        obsttorteButton.setDisable(true);
        obstkuchenButton.setDisable(false);
        kremkuchenButton.setDisable(false);

        obstsorteLabel.setVisible(true);
        obstsorteInput.setVisible(true);
        kremsorteInput.setVisible(true);
        kremsorteLabel.setVisible(true);
    }

    @FXML
    private void inspectKuchenButtonHandler(ActionEvent event) {
        event.consume();
        if (selectedKuchenFachnummer != null) {
            Map<DataType, Object> tempMap = new HashMap<>();
            tempMap.put(DataType.fachnummer, selectedKuchenFachnummer);
            AutomatEvent dKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.inspectKuchen);
            automatEventHandler.handle(dKuchenEvent);
        }
    }

    @FXML
    private void saveJosButtonHandler(ActionEvent event) {
        event.consume();
        ConsoleLib.handleSaveJOS(automatEventHandler, this);
    }

    @FXML
    private void loadJosButtonHandler(ActionEvent event) {
        event.consume();
        ConsoleLib.handleLoadJOS(automatEventHandler, this);
    }


    public void setAutomatEventHandler(AutomatEventHandler automatEventHandler) {
        this.automatEventHandler = automatEventHandler;
    }


    public void initialize() {

        /* DATA BINDING */
        herstellerName.bindBidirectional(herstellerInputField.textProperty());


        herstellerView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String[] firstSplit = newValue.split(",");
                    selectedHerstellerName = firstSplit[0];
                }
            }
        });

        kuchenView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {

                    String[] firstSplit = newValue.split("=");
                    String[] secondSplit = firstSplit[1].split(",");
                    selectedKuchenFachnummer = Integer.parseInt(secondSplit[0]);
                }
            }
        });

        kuchenView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(kuchenList != null && kuchenList.size()>1) {
                    String kuchenString = (String) kuchenView.getSelectionModel().getSelectedItem();
                    if(kuchenString == null) return;
                    String[] firstSplit = kuchenString.split("=");
                    String[] secondSplit = firstSplit[1].split(",");
                    dragStartFachnummer = Integer.parseInt(secondSplit[0]);
                }
            }
        });

        kuchenView.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(kuchenList != null && kuchenList.size()>1 ) {
                    String droppedCake = event.getPickResult().getIntersectedNode().toString();
                    String[] firstSplit = droppedCake.split("=");
                    if(firstSplit.length < 3) {return;}
                    String[] secondSplit = firstSplit[2].split(",");
                    int droppedOnFachnummer = Integer.parseInt(secondSplit[0]);
                    if( dragStartFachnummer != droppedOnFachnummer) {
                        Map<DataType, Object> tempMap = new HashMap<>();
                        tempMap.put(DataType.fachnummer, new int[]{dragStartFachnummer, droppedOnFachnummer});
                        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.swapKuchen);
                        automatEventHandler.handle(automatEvent);
                    }
                }
            }
        });


        preisSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
           if((double)newVal-(double)oldVal == 1){
               double temp = (double)newVal + 9;
               preisSpinner.getValueFactory().setValue(temp);
               preis = BigDecimal.valueOf(temp/Double.valueOf(100));
           }else if((double)newVal-(double)oldVal == -1){
               double temp = (double)newVal - 9;
               preisSpinner.getValueFactory().setValue(temp);
               preis = BigDecimal.valueOf(temp/Double.valueOf(100));
           }
        });
        naehrwertSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if((double)newVal-(double)oldVal == 1){
                double temp = (double)newVal + 24;
                naehrwertSpinner.getValueFactory().setValue(temp);
                naehrwert = (int) temp;
            }else if((double)newVal-(double)oldVal == -1){
                double temp = (double)newVal - 24;
                naehrwertSpinner.getValueFactory().setValue(temp);
                naehrwert = (int) temp;
            }
        });
        haltbarkeitSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if((double)newVal-(double)oldVal == 1){
                double temp = (double)newVal + 7;
                haltbarkeitSpinner.getValueFactory().setValue(temp);
                haltbarkeit = (int) temp;
            }else if((double)newVal-(double)oldVal == -1){
                double temp = (double)newVal - 7;
                haltbarkeitSpinner.getValueFactory().setValue(temp);
                haltbarkeit = (int) temp;

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
                this.herstellerMap = (Map<Hersteller, Integer>) event.getData().get(DataType.hersteller);
                this.updateHerstellerView();
                break;
            case kuchenData:
                this.kuchenList = (ArrayList<VerkaufsKuchen>) event.getData().get(DataType.kuchenListe);
                updateKuchenView();
                break;
            case allergenData:
                this.allergeneVorhanden = (Set<Allergen>[]) event.getData().get(DataType.allergene);
                updateAllergeneView();
        }
    }


    private void updateHerstellerView() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Hersteller hersteller : this.herstellerMap.keySet()) {
           items.add(hersteller.getName() + ", Kuchen: " + this.herstellerMap.get(hersteller));
        }
        this.herstellerView.setItems(items);
    }

    private void updateKuchenView() {
        if (kuchenList != null ) {
            sortKuchenList();
            ObservableList<String> items = FXCollections.observableArrayList();
            for (VerkaufsKuchen verkaufsKuchen : this.kuchenList) {
                items.add(verkaufsKuchen.toString());
            }
            this.kuchenView.setItems(items);
        }
    }

    private void updateAllergeneView() {
        if (allergeneVorhanden != null) {
            if(vorhandeneAllergeneAnzeigen) {
                this.allergeneView.setText(this.allergeneVorhanden[1].toString());
            } else {
                this.allergeneView.setText(this.allergeneVorhanden[0].toString());
            }
        }
    }


    public void fachnummerSortButtonHandler(ActionEvent actionEvent) {
        actionEvent.consume();
        this.selectedSort = SortType.fachnummer;
        updateKuchenView();

        fachnummerSortButton.setDisable(true);
        herstellerSortButton.setDisable(false);
        inspectSortButton.setDisable(false);
        haltbarkeitSortButton.setDisable(false);

        fachnummerSortButton.setSelected(true);
        herstellerSortButton.setSelected(false);
        inspectSortButton.setSelected(false);
        haltbarkeitSortButton.setSelected(false);

    }

    public void herstellerSortButtonHandler(ActionEvent actionEvent) {
        actionEvent.consume();
        this.selectedSort = SortType.hersteller;
        updateKuchenView();

        herstellerSortButton.setDisable(true);
        fachnummerSortButton.setDisable(false);
        inspectSortButton.setDisable(false);
        haltbarkeitSortButton.setDisable(false);

        herstellerSortButton.setSelected(true);
        fachnummerSortButton.setSelected(false);
        inspectSortButton.setSelected(false);
        haltbarkeitSortButton.setSelected(false);
    }

    public void inspectSortButtonHandler(ActionEvent actionEvent) {
        actionEvent.consume();
        this.selectedSort = SortType.inspect;

        updateKuchenView();

        inspectSortButton.setDisable(true);
        herstellerSortButton.setDisable(false);
        fachnummerSortButton.setDisable(false);
        haltbarkeitSortButton.setDisable(false);

        inspectSortButton.setSelected(true);
        herstellerSortButton.setSelected(false);
        fachnummerSortButton.setSelected(false);
        haltbarkeitSortButton.setSelected(false);
    }

    public void haltbarkeitSortButtonHandler(ActionEvent actionEvent) {
        actionEvent.consume();
        this.selectedSort = SortType.haltbarkeit;
        updateKuchenView();
        haltbarkeitSortButton.setDisable(true);
        herstellerSortButton.setDisable(false);
        inspectSortButton.setDisable(false);
        fachnummerSortButton.setDisable(false);

        haltbarkeitSortButton.setSelected(true);
        herstellerSortButton.setSelected(false);
        inspectSortButton.setSelected(false);
        fachnummerSortButton.setSelected(false);
    }

    public void allergeneSwitchButtonHandler(ActionEvent actionEvent) {
        actionEvent.consume();
        this.vorhandeneAllergeneAnzeigen = !this.vorhandeneAllergeneAnzeigen;
            if(vorhandeneAllergeneAnzeigen) {
                if(this.allergeneVorhanden == null ) {
                    this.allergeneView.setText("[]");
                } else {
                    this.allergeneView.setText(this.allergeneVorhanden[1].toString());
                }
            } else {
                if(this.allergeneVorhanden == null ) {
                    this.allergeneView.setText("[Sesamsamen, Erdnuss, Haselnuss]");
                } else {
                    this.allergeneView.setText(this.allergeneVorhanden[0].toString());
                }
            }
    }


    private void sortKuchenList() {
            switch(selectedSort){
                case fachnummer:
                    Collections.sort(kuchenList, new KuchenComparators.FachnummerComparator());
                    break;
                case hersteller:
                    Collections.sort(kuchenList, new KuchenComparators.HerstellerComparator());
                    break;
                case inspect:
                    Collections.sort(kuchenList, new KuchenComparators.InspektionsDatumComparator());
                    break;
                case haltbarkeit:
                    Collections.sort(kuchenList, new KuchenComparators.HaltbarkeitComparator());
                    break;
            }
    }
}
