package control.automat.events.listener;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

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
        boolean included = (boolean) event.getData().get(DataType.bool);
        Set<Allergen> tempset = automat.getAllergene(included);
        sb.append("[");
        tempset.forEach(allergen -> sb.append(""
                + allergen
                + ","
        ));
        sb.append("]");
        String allergeneTitle = "";
        if (!included) allergeneTitle = "nicht ";
        Output.print(this, allergeneTitle + "vorhandene Allergene:" + sb, MessageType.success, outputEventHandler);
    }

    private void handleReadKuchen(AutomatEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        List<VerkaufsKuchen> tempList;
        if (event.getData().get(DataType.kuchenart) != null) {
            tempList = automat.getKuchen((KuchenArt) event.getData().get(DataType.kuchenart));
            if (tempList.isEmpty()) {
                OutputEvent outputEvent = new OutputEvent(this, "keine Kuchen dieses Typs vorhanden", MessageType.error);
                outputEventHandler.handle(outputEvent);
                return;
            } else {
                sb.append((KuchenArt) event.getData().get(DataType.kuchenart) + ":\n");
                tempList.forEach(verkaufsKuchen -> sb.append("{"
                        + "Fach: "
                        + verkaufsKuchen.getFachnummer()
                        + ", Insp: "
                        + verkaufsKuchen.getInspektionsdatum().toString()
                        + ", Haltbark. in Stunden: "
                        + (verkaufsKuchen.getHaltbarkeit().getSeconds() / 60 / 60)
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
                    + ", Hltbr(h): "
                    + (verkaufsKuchen.getHaltbarkeit().getSeconds() / 60 / 60)
                    + "}, \n"));
        }
        sb.append("]");
        Output.print(this, "Kuchen:" + sb, MessageType.success, outputEventHandler);
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
