package control.automat.events.listener;

import control.automat.Automat;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventListener;
import control.automat.events.DataType;
import control.automat.verkaufsobjekte.Allergen;
import control.automat.verkaufsobjekte.kuchen.KuchenArt;
import control.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;
import view.output.MessageType;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

import java.util.List;

public class AutomatEventListenerRead implements AutomatEventListener {

    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerRead(Automat automat, OutputEventHandler outputEventHandler) {
        this.automat = automat;
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
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
        List<Allergen> tempList = automat.getAllergene(included);
        sb.append("[");
        tempList.forEach(allergen -> sb.append(""
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
