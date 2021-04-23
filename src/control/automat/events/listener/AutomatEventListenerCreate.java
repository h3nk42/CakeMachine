package control.automat.events.listener;

import control.automat.Automat;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import control.automat.hersteller.Hersteller;
import control.automat.verkaufsobjekte.Allergen;
import control.automat.verkaufsobjekte.kuchen.KuchenArt;
import view.output.MessageType;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

import java.math.BigDecimal;
import java.util.Map;

public class AutomatEventListenerCreate implements AutomatEventListener {

    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerCreate(Automat automat, OutputEventHandler outputEventHandler) {
        this.automat = automat;
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
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
            OutputEvent outputEvent = new OutputEvent(this, "erfolg: " + automat.getHersteller().toString(), MessageType.success);
            outputEventHandler.handle(outputEvent);
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
                    kremObstData = new String[]{obstsorte};
                    break;
                case Kremkuchen:
                    kremsorte = (String) eventData.get(DataType.kremsorte);
                    kremObstData = new String[]{kremsorte};
                    break;
                case Obsttorte:
                    obstsorte = (String) eventData.get(DataType.obstsorte);
                    kremsorte = (String) eventData.get(DataType.kremsorte);
                    kremObstData = new String[]{obstsorte,kremsorte};
                    break;
                default:
                    throw new Exception("Kuchenart nicht erkannt");
            }
            automat.createKuchen(kuchenArt, hersteller,preis,naehrwert,allergene,kremObstData,haltbarkeit );
            OutputEvent outputEvent = new OutputEvent(this, "erfolg: " + automat.getKuchen().toString(), MessageType.success);
            outputEventHandler.handle(outputEvent);
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
