package control.console;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatOperationType;
import control.automat.events.CakeDataType;
import control.console.input.InputEvent;
import control.console.input.InputEventListener;
import control.lib.ConsoleLib;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static control.lib.ConsoleLib.*;

//Obsttorte rewe 7,50 632 24 Gluten Apfel Sahne

public class AutomatConsole implements InputEventListener {
    private ConsoleState consoleState;
    private OutputEventHandler outputEventHandler;
    private AutomatEventHandler automatEventHandler;

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    public AutomatConsole(OutputEventHandler outputEventHandler, AutomatEventHandler automatEventHandler) {
        this.outputEventHandler = outputEventHandler;
        this.automatEventHandler = automatEventHandler;
        this.consoleState = ConsoleState.none;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        switch (event.getInputEventType()) {
            case print:
                printState();
                break;
            /* HANDLE INPUT */
            case read:
                try {
                    handleInput(event.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private boolean handleInput(String input) throws Exception {
        input = input.toLowerCase();
        if (handleStateChangers(input)) return true;
        if (handleStateSpecific(input)) return true;
        sendOutPutEvent("Eingabe nicht erkannt!", MessageType.error, outputEventHandler, this);
        printState();
        return false;
    }

    private boolean handleStateChangers(String input) {
        switch (input) {
            case "exit":
                System.exit(0);
                return true;
            case ":c":
                this.changeStateChange(ConsoleState.c);
               return true;
            case ":r":
                this.changeStateChange(ConsoleState.r);
                return true;
            case ":u":
                this.changeStateChange(ConsoleState.u);
                return true;
            case ":d":
                this.changeStateChange(ConsoleState.d);
                return true;
            case ":p":
                this.changeStateChange(ConsoleState.p);
                return true;
        }
        return false;
    }

    private boolean handleStateSpecific (String input) throws Exception {
        int inputSize = returnInputSize(input);
        String[] inputWords = ConsoleLib.extractArguments(input);
        switch (this.consoleState) {
            case none:
                break;
            case r:
                switch(inputSize){
                    case 1:
                        switch(inputWords[0]){
                            case "hersteller":
                                ConsoleLib.readHersteller(automatEventHandler, this);
                                return true;
                            case "kuchen":
                                ConsoleLib.handleReadKuchen(inputWords,automatEventHandler,outputEventHandler,this);
                                return true;
                        }
                        break;
                    case 2:
                        switch(inputWords[0]){
                            case "kuchen":
                                ConsoleLib.handleReadKuchen(inputWords,automatEventHandler,outputEventHandler,this);
                                return true;
                            case "allergene":
                                ConsoleLib.handleReadAllergene(inputWords, automatEventHandler, outputEventHandler, this);
                                return true;
                        }
                        break;
                    default:
                        sendOutPutEvent(ConsoleLib.argumentCountWrong, MessageType.error, outputEventHandler, this);
                        return true;
                }
                break;
            case c:
                switch(inputSize){
                    case 1:
                        handleCreateHersteller(input, automatEventHandler, outputEventHandler, this);
                        return true;
                    case 7:
                    case 8:
                        ConsoleLib.handleCreateKuchen(input, automatEventHandler, outputEventHandler, this);
                        return true;
                    default:
                        sendOutPutEvent(ConsoleLib.argumentCountWrong, MessageType.error, outputEventHandler, this);
                        return true;
                }
            case d:
                switch(inputSize){
                    case 1:
                        if (ConsoleLib.isNumeric(inputWords[0])){
                            ConsoleLib.handleDeleteKuchen(inputWords[0], automatEventHandler, outputEventHandler, this);
                        } else {
                            ConsoleLib.handleDeleteHersteller(inputWords[0], automatEventHandler, outputEventHandler,this);
                        }
                        return true;
                    default:
                        sendOutPutEvent(ConsoleLib.argumentCountWrong, MessageType.error, outputEventHandler, this);
                        return true;
                }
            case p:
                switch(inputSize){
                    case 1:
                        switch(inputWords[0].toLowerCase()){
                            case "savejos":
                                ConsoleLib.handleSaveJOS(automatEventHandler, outputEventHandler, this);
                                return true;
                            case "loadjos":
                                ConsoleLib.handleLoadJOS(automatEventHandler, outputEventHandler, this);
                                return true;
                        }
                        break;
                    default:
                        sendOutPutEvent(ConsoleLib.argumentCountWrong, MessageType.error, outputEventHandler, this);
                        return true;
                }
                break;
            case u:
                switch(inputSize){
                    case 1:
                        if (ConsoleLib.isNumeric(inputWords[0])){
                            ConsoleLib.handleUpdateKuchen(inputWords[0], automatEventHandler, outputEventHandler, this);
                        } else {
                            sendOutPutEvent("keine Nummer eingeben", MessageType.error, outputEventHandler, this);
                        }
                        return true;
                    default:
                        sendOutPutEvent(ConsoleLib.argumentCountWrong, MessageType.error, outputEventHandler, this);
                        return true;
                }
        }
        return false;
    }

    private boolean changeStateChange(ConsoleState consoleState) {
        this.consoleState = consoleState;
        printState();
        return true;
    }

    private int returnInputSize(String input) {
        return input.split("\\s+").length;
    }

    private boolean checkArgumentSize(String input, int minSize, int maxSize) {
        int length = returnInputSize(input);
        return (length >= minSize && length <= maxSize);
    }

    private void printState() {
        String defaultMessage = "\u001B[33m" + "Modus wechseln:" + "\u001B[0m" + " "+System.lineSeparator()+" :c - Einfügen "+System.lineSeparator()+" :r - Anzeigen"+System.lineSeparator()+" :u - Ändern"+System.lineSeparator()+" :d - Löschen"+System.lineSeparator()+" :p - Speichern";
        String messageToPrint = "";
        switch (this.consoleState) {
            case none:
                messageToPrint = "";
                break;
            case r:
                messageToPrint = "\u001B[33m" + "Anzeigen: " + "\u001B[0m" + " \n hersteller - Hersteller mit Kuchenanzahl \n (kuchen [typ]) - Kuchen ggf nur angegebener Typ  \n (allergene [i/e]) - Allergene [included/excluded] \n b - Zurück";
                break;
            case c:
                messageToPrint = "\u001B[33m" + "Einfügen: " + "\u001B[0m" + " \n [Herstellername] - Hersteller erstellen \n [Kuchen-Typ] [Herstellername] [Preis] [Nährwert]" +
                        "[Haltbarkeit] [kommaseparierte Allergene, einzelnes" +
                        "Komma für keine] [[Obstsorte]] [[Kremsorte]] - Kuchen erstellen";
                break;
            case d:
                messageToPrint = "\u001B[33m" + "Löschen: " + "\u001B[0m" + " \n herstellerName - Hersteller löschen  \n fachnummer - Kuchen löschen";
                break;
            case p:
                messageToPrint = "\u001B[33m" + "Speichern: " + "\u001B[0m" + " \n saveJOS - Zustand speichern \n loadJOS - Zustand laden ";
                break;
            case u:
                messageToPrint = "\u001B[33m" + "Speichern: " + "\u001B[0m" + " \n fachnummer - kuchen inspizieren";
                break;
        }
        messageToPrint = messageToPrint + "\n" + defaultMessage+"\n exit - Programm beenden";
        sendOutPutEvent(messageToPrint, MessageType.normal, outputEventHandler, this);
    }

}
