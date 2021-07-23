package control.automat.events;

import control.automat.AutomatController;
import model.Automat;
import model.hersteller.Hersteller;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

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
        String name = (String) event.getData().get(CakeDataType.hersteller);
        try {
            automat.createHersteller(name);
            automatC.aktualisiereHersteller();
        } catch (Exception e) {
            OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
            outputEventHandler.handle(outputEvent);
        }
    }

    private void handleCreateKuchen(AutomatEvent event) {
        Map<CakeDataType, Object> eventData = event.getData();
        try {
            Hersteller hersteller = findHersteller((String) eventData.get(CakeDataType.hersteller));
            BigDecimal preis = (BigDecimal) eventData.get(CakeDataType.preis);
            int naehrwert = (int) eventData.get(CakeDataType.naehrwert);
            int haltbarkeit= (int) eventData.get(CakeDataType.haltbarkeit);
            Allergen[] allergene = (Allergen[]) eventData.get(CakeDataType.allergene);
            KuchenArt kuchenArt = (KuchenArt) eventData.get(CakeDataType.kuchenart);
            String obstsorte;
            String kremsorte;
            String[] kremObstData;
            switch(kuchenArt) {
                case Obstkuchen:
                    obstsorte = (String) eventData.get(CakeDataType.obstsorte);
                    if( obstsorte == null || obstsorte.equals("")) {

                        throw new Exception("Obstsorte leer");
                    }
                    kremObstData = new String[]{obstsorte};
                    break;
                case Kremkuchen:
                    kremsorte = (String) eventData.get(CakeDataType.kremsorte);
                    if(kremsorte == null || kremsorte.equals("")) {
                        throw new Exception("Kremsorte leer");
                    }
                    kremObstData = new String[]{kremsorte};
                    break;
                case Obsttorte:
                    obstsorte = (String) eventData.get(CakeDataType.obstsorte);
                    kremsorte = (String) eventData.get(CakeDataType.kremsorte);
                    if( kremsorte == null || obstsorte == null ||kremsorte.equals("") || obstsorte.equals("")) {

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
