package control.gui;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.CakeDataType;
import control.automat.events.AutomatOperationType;
import control.gui.event.GuiEventType;
import control.gui.event.UpdateGuiEvent;
import control.gui.event.UpdateGuiEventListener;
import javafx.beans.property.SimpleStringProperty;
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
import control.lib.ConsoleLib;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;
import control.lib.KuchenComparators;
import model.verkaufsobjekte.kuchen.KuchenArt;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;
import control.lib.CakeSortType;

import java.math.BigDecimal;
import java.util.*;

import static control.lib.ConsoleLib.extractAllergene;


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

    private CakeSortType selectedSort = CakeSortType.fachnummer;

    private boolean vorhandeneAllergeneAnzeigen = false;

    private int dragStartFachnummer;

    @FXML
    private void createHerstellerButtonHandler(ActionEvent event) throws Exception {
        event.consume();
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        tempMap.put(CakeDataType.hersteller, this.herstellerName.getValue());
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
    }

    @FXML
    private void deleteHerstellerButtonHandler(ActionEvent event) throws Exception {
        event.consume();
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        tempMap.put(CakeDataType.hersteller, this.selectedHerstellerName);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dHersteller);
        automatEventHandler.handle(automatEvent);
    }

    @FXML
    private void createKuchenButtonHandler(ActionEvent event) {
        event.consume();
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        tempMap.put(CakeDataType.kuchenart, this.kuchenArt);
        tempMap.put(CakeDataType.preis, preis);
        tempMap.put(CakeDataType.naehrwert, naehrwert);
        tempMap.put(CakeDataType.haltbarkeit, haltbarkeit);
        tempMap.put(CakeDataType.hersteller, selectedHerstellerName);
        AutomatEvent cKuchenEvent;
        try {
            Allergen[] allergene = extractAllergene(this.allergene);
            tempMap.put(CakeDataType.allergene, allergene);
            switch (this.kuchenArt) {
                case Kremkuchen:
                    tempMap.put(CakeDataType.kremsorte, this.kremsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
                case Obstkuchen:
                    tempMap.put(CakeDataType.obstsorte, this.obstsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
                case Obsttorte:
                    tempMap.put(CakeDataType.kremsorte, this.kremsorte);
                    tempMap.put(CakeDataType.obstsorte, this.obstsorte);
                    cKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.cKuchen);
                    automatEventHandler.handle(cKuchenEvent);
                    break;
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void deleteKuchenButtonHandler(ActionEvent event) throws Exception {
        event.consume();
        if (selectedKuchenFachnummer != null) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer, selectedKuchenFachnummer);
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
    private void inspectKuchenButtonHandler(ActionEvent event) throws Exception {
        event.consume();
        if (selectedKuchenFachnummer != null) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer, selectedKuchenFachnummer);
            AutomatEvent dKuchenEvent = new AutomatEvent(this, tempMap, AutomatOperationType.inspectKuchen);
            automatEventHandler.handle(dKuchenEvent);
        }
    }

    @FXML
    private void saveJosButtonHandler(ActionEvent event) throws Exception {
        event.consume();
        ConsoleLib.handleSaveJOS(automatEventHandler, this);
    }

    @FXML
    private void loadJosButtonHandler(ActionEvent event) throws Exception {
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
                        Map<CakeDataType, Object> tempMap = new HashMap<>();
                        tempMap.put(CakeDataType.fachnummer, new int[]{dragStartFachnummer, droppedOnFachnummer});
                        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.swapKuchen);
                        try {
                            automatEventHandler.handle(automatEvent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                this.herstellerMap = (Map<Hersteller, Integer>) event.getData().get(CakeDataType.hersteller);
                this.updateHerstellerView();
                break;
            case kuchenData:
                this.kuchenList = (ArrayList<VerkaufsKuchen>) event.getData().get(CakeDataType.kuchenListe);
                updateKuchenView();
                break;
            case allergenData:
                this.allergeneVorhanden = (Set<Allergen>[]) event.getData().get(CakeDataType.allergene);
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
        this.selectedSort = CakeSortType.fachnummer;
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
        this.selectedSort = CakeSortType.hersteller;
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
        this.selectedSort = CakeSortType.inspect;

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
        this.selectedSort = CakeSortType.haltbarkeit;
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
