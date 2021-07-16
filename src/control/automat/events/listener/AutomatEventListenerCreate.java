package control.automat.events.listener;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class AutomatEventListenerCreate implements AutomatEventListener, Serializable {

    private AutomatController automatC;
    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerCreate(OutputEventHandler outputEventHandler, AutomatController automatController) {
        this.automatC = automatController;
        this.automat = automatController.getAutomat();
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        automat = automatC.getAutomat();
        if (null != event.getData()) {
            switch(event.getOperationType()) {
                case cKuchen:
                    handleCreateKuchen(event);
                    break;
                case cHersteller:
                    handleCreateHersteller(event);
                    break;
            }
        }
    }

    private void handleCreateHersteller(AutomatEvent event) {
        String name = (String) event.getData().get(DataType.hersteller);
        try {
            automat.createHersteller(name);
            //Output.print(this, "erfolg", MessageType.success, outputEventHandler);
            automatC.aktualisiereHersteller();
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void handleCreateKuchen(AutomatEvent event) {
        Map<DataType, Object> eventData = event.getData();
        try {
            Hersteller hersteller = findHersteller((String) eventData.get(DataType.hersteller));
            BigDecimal preis = (BigDecimal) eventData.get(DataType.preis);
            int naehrwert = (int) eventData.get(DataType.naehrwert);
            int haltbarkeit= (int) eventData.get(DataType.haltbarkeit);
            Allergen[] allergene = (Allergen[]) eventData.get(DataType.allergene);
            KuchenArt kuchenArt = (KuchenArt) eventData.get(DataType.kuchenart);
            String obstsorte;
            String kremsorte;
            String[] kremObstData;
            switch(kuchenArt) {
                case Obstkuchen:
                    obstsorte = (String) eventData.get(DataType.obstsorte);
                    if( obstsorte.equals("")) {
                        Output.print(this, "Obstsorte leer", MessageType.error, outputEventHandler);
                        throw new Exception("Obstsorte leer");
                    }
                    kremObstData = new String[]{obstsorte};
                    break;
                case Kremkuchen:
                    kremsorte = (String) eventData.get(DataType.kremsorte);
                    if(kremsorte == null || kremsorte.equals("")) {
                        Output.print(this, "Kremsorte leer", MessageType.error, outputEventHandler);
                        throw new Exception("Kremsorte leer");
                    }
                    kremObstData = new String[]{kremsorte};
                    break;
                case Obsttorte:
                    obstsorte = (String) eventData.get(DataType.obstsorte);
                    kremsorte = (String) eventData.get(DataType.kremsorte);
                    if( kremsorte.equals("") || obstsorte.equals("")) {
                        Output.print(this, "Obstsorte | Kremsorte leer", MessageType.error, outputEventHandler);
                        throw new Exception("Obstsorte | Kremsorte leer");
                    }
                    kremObstData = new String[]{obstsorte,kremsorte};
                    break;
                default:
                    throw new Exception("Kuchenart nicht erkannt");
            }
            automat.createKuchen(kuchenArt, hersteller,preis,naehrwert,allergene,kremObstData,haltbarkeit );
            //Output.print(this, "erfolg", MessageType.success, outputEventHandler);
            automatC.aktualisiereAllergene();
            automatC.aktualisiereKuchenCapacity();
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }

    }

    private Hersteller findHersteller(String name) throws Exception {
        name = name.toLowerCase();
        Hersteller tempH = automat.getHersteller(name);
        if (tempH != null) {
            return tempH;
        } else {
            throw new Exception("Hersteller nicht erkannt");
        }
    }

    @Override
    public String toString() {
        return "create";
    }

}
