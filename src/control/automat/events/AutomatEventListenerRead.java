package control.automat.events;

import control.automat.AutomatController;
import control.lib.ConsoleLib;
import model.Automat;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class AutomatEventListenerRead implements AutomatEventListener, Serializable {

    private OutputEventHandler outputEventHandler;
    private AutomatController automatController;
    private Automat automat;

    public AutomatEventListenerRead(OutputEventHandler outputEventHandler, AutomatController automatController) {
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        automat = automatController.getAutomat();
        if (null != event.getData()) {
            switch (event.getOperationType()) {
                case rHersteller:
                    handleReadHersteller(event);
                    break;
                case rKuchen:
                    handleReadKuchen(event);
                    break;
                case rAllergene:
                    handleReadAllergene(event);
                    break;
            }
        }
    }

    private void handleReadAllergene(AutomatEvent event) {
        StringBuilder sb = new StringBuilder();
        boolean included = (boolean) event.getData().get(CakeDataType.bool);
        Set<Allergen> tempset = automat.getAllergene(included);
        sb.append("[");
        tempset.forEach(allergen -> sb.append(""
                + allergen
                + ","
        ));
        sb.append("]");
        String allergeneTitle = "";
        if (!included) allergeneTitle = "nicht ";
        OutputEvent outputEvent = new OutputEvent(this, allergeneTitle + "vorhandene Allergene:" + sb, MessageType.success);
        outputEventHandler.handle(outputEvent);
    }

    private void handleReadKuchen(AutomatEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        List<VerkaufsKuchen> tempList;
        if (event.getData().get(CakeDataType.kuchenart) != null) {
            tempList = automat.getKuchen((KuchenArt) event.getData().get(CakeDataType.kuchenart));
            if (tempList.isEmpty()) {
                OutputEvent outputEvent = new OutputEvent(this, "keine Kuchen dieses Typs vorhanden", MessageType.error);
                outputEventHandler.handle(outputEvent);
                return;
            } else {
                sb.append((KuchenArt) event.getData().get(CakeDataType.kuchenart) + ":\n");
                tempList.forEach(verkaufsKuchen -> sb.append("{"
                        + "Fach: "
                        + verkaufsKuchen.getFachnummer()
                        + ", Insp: "
                        + verkaufsKuchen.getInspektionsdatum().toString()
                        + ", Hltbr verbleibend (h): "
                        + ConsoleLib.getActualBBF(verkaufsKuchen)
                        + "}, \n"));
            }

        } else {
            if (automat.getKuchen().isEmpty()) {
                OutputEvent outputEvent = new OutputEvent(this, "keine Kuchen vorhanden", MessageType.error);
                outputEventHandler.handle(outputEvent);
                return;
            }
            tempList = automat.getKuchen();
            tempList.forEach(verkaufsKuchen -> sb.append("{"
                    + "Typ: "
                    + verkaufsKuchen.getKuchenArt()
                    + ", Fnr: "
                    + verkaufsKuchen.getFachnummer()
                    + ", Insp: "
                    + verkaufsKuchen.getInspektionsdatum().toString()
                    + ", Hltbr verbleibend (h): "
                    + ConsoleLib.getActualBBF(verkaufsKuchen)
                    + "}, \n"));
        }
        sb.append("]");
        OutputEvent outputEvent = new OutputEvent(this, "Kuchen:" + sb, MessageType.success);
        outputEventHandler.handle(outputEvent);
    }

    private void handleReadHersteller(AutomatEvent event) {
        if (automat.getHersteller().size() < 1) {
            OutputEvent outputEvent = new OutputEvent(this, "keine Hersteller gefunden", MessageType.error);
            outputEventHandler.handle(outputEvent);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        automat.getHersteller().forEach(hersteller -> {
            try {
                sb.append("{");
                sb.append(hersteller.getName()
                        + ", Kuchenanzahl: "
                        + automat.getKuchenCounter(hersteller) +
                        "}, \n");
            } catch (Exception e) {
                OutputEvent outputEvent = new OutputEvent(this, e.getMessage(), MessageType.error);
                outputEventHandler.handle(outputEvent);
            }
        });
        sb.append("]");
        OutputEvent outputEvent = new OutputEvent(this, "Hersteller:" + sb, MessageType.success);
        outputEventHandler.handle(outputEvent);
    }

}
