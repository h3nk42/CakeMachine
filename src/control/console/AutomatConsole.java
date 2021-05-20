package control.console;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.OperationType;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import control.console.input.Input;
import control.console.input.InputEvent;
import control.console.input.InputEventListener;
import view.output.MessageType;
import view.output.OutputEvent;
import view.output.OutputEventHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;


public class AutomatConsole implements InputEventListener {
    private final Input input;
    private Stack<ConsoleState> modeStack;
    private OutputEventHandler outputEventHandler;
    private AutomatEventHandler automatEventHandler;


    public AutomatConsole(OutputEventHandler outputEventHandler, AutomatEventHandler automatEventHandler) {
        this.outputEventHandler = outputEventHandler;
        this.automatEventHandler = automatEventHandler;
        modeStack = new Stack<>();
        modeStack.push(ConsoleState.none);
        this.input = new Input();
    }


    private boolean sendOutPutEvent(String textToSend, MessageType messageType) {
        OutputEvent oEventPrintText = new OutputEvent(this, textToSend, messageType);
        outputEventHandler.handle(oEventPrintText);
        return true;
    }

    @Override
    public void onInputEvent(InputEvent event) {
            switch(event.getInputEventType()) {
                case print:
            /* PRINT CONSOLE STATUS */
            String messageToPrint = "";
            switch (this.getState()) {
                case none:
                    messageToPrint = "\u001B[33m" + "Modus wählen:" + "\u001B[0m" + " \n c - Einfügen \n r - Anzeigen\n u - Ändern\n d - Löschen\n p - Speichern \n conf - Konfiguration \n exit - Programm beenden";
                    break;
                case r:
                    messageToPrint = "\u001B[33m" + "Anzeigen: " + "\u001B[0m" + " \n h - Hersteller mit Kuchenanzahl \n k [typ] - Kuchen ggf nur angegebener Typ  \n a [i/e] - Allergene [included/excluded] \n b - Zurück \n exit - Programm beenden";
                    break;
                case c:
                    messageToPrint = "\u001B[33m" + "Einfügen: " + "\u001B[0m" + " \n h - Hersteller \n k - Kuchen \n b - Zurück \n exit - Programm beenden";
                    break;
                case ch:
                    messageToPrint = "\u001B[33m" + "Hersteller Einfügen: " + "\u001B[0m" + " \n <herstellerName> - fügt Hersteller hinzu \n b - Zurück \n exit - Programm beenden";
                    break;
                case ck:
                    messageToPrint = "\u001B[33m" + "Kuchen Einfügen: " + "\u001B[0m" + " \n <kuchenTyp> <herstellername> <preis> <nährwert> <haltbarkeit> <allergen,..> oder <,> <kremsorte> oder/und <obstsorte> \n b - Zurück \n exit - Programm beenden";
                    break;
                case d:
                    messageToPrint = "\u001B[33m" + "Löschen: " + "\u001B[0m" + " \n h - Hersteller \n k - Kuchen \n b - Zurück \n exit - Programm beenden";
                    break;
                case dh:
                    messageToPrint = "\u001B[33m" + "Hersteller Löschen: " + "\u001B[0m" + " \n <herstellerName> - löscht Hersteller \n b - Zurück \n exit - Programm beenden";
                    break;
                case dk:
                    messageToPrint = "\u001B[33m" + "Kuchen Löschen: " + "\u001B[0m" + " \n <fachnummer> - löscht Kuchen \n b - Zurück \n exit - Programm beenden";
                    break;
                case p:
                    messageToPrint = "\u001B[33m" + "Speichern: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
                case config:
                    messageToPrint = "\u001B[33m" + "Konfiguration: " + "\u001B[0m" + " \n b - Zurück \n exit - Programm beenden";
                    break;
            }
            this.sendOutPutEvent(messageToPrint, MessageType.normal);
                    break;

                /* HANDLE INPUT */
                case read:
                    handleInput(event.getText(), getState());
                    break;
            }
    }

    private boolean handleInput(String input, ConsoleState consoleState) {
        input = input.toLowerCase();
        if (!this.checkArgumentSize(input, 1, 8))
            return this.sendOutPutEvent("zu wenig / zu viele Anweisungen!", MessageType.error);
        switch (input) {
            case "exit":
                System.exit(0);
            case "b":
                return this.handleBack();
        }
        switch (consoleState) {
            case none:
                return handleNoneState(input);
            case c:
                return handleCreateState(input);
            case r:
                return handleReadState(input);
            case u:
                break;
            case d:
                return handleDeleteState(input);
            case p:

                break;
            case ch:
                handleCreateHersteller(input);
                break;
            case ck:
                this.handleCreateKuchen(input);
                break;
            case dh:
                handleDeleteHersteller(input);
                break;
            case dk:
                this.handleDeleteKuchen(input);
                break;
            case config:

                break;
        }
        return false;
    }

    private boolean handleDeleteKuchen(String input) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error);
        else if (!checkCharSize(input, 1, 3)) {
            return sendOutPutEvent("1 - 3 Zeichen erlaubt", MessageType.error);
        }
        try {
            int fachnummer = Integer.parseInt(splitText[0]);
            Map<DataType, Object> tempMap = new HashMap<>();
            tempMap.put(DataType.fachnummer,fachnummer);
            AutomatEvent automatEvent = new AutomatEvent(this, tempMap, OperationType.dKuchen);
            automatEventHandler.handle(automatEvent);
            return true;
        }catch (Exception e) {
            return sendOutPutEvent("keine nummer angegeben!",MessageType.error);
        }
    }

    private boolean handleDeleteHersteller(String input) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error);
        try {
            String herstellerName = extractString(splitText[0],10);
            Map<DataType, Object> tempMap = new HashMap<>();
            tempMap.put(DataType.hersteller, herstellerName);
            AutomatEvent automatEvent = new AutomatEvent(this, tempMap, OperationType.dHersteller);
            automatEventHandler.handle(automatEvent);
            return true;
        }catch (Exception e) {
            return sendOutPutEvent(e.getMessage() ,MessageType.error);
        }
    }

    private boolean handleDeleteState(String input) {
        switch(input) {
            case "h":
                return this.changeState(ConsoleState.dh);
            case "k":
                return this.changeState(ConsoleState.dk);
        }
        return false;
    }

    private boolean handleReadState(String input) {
        if (!this.checkArgumentSize(input, 1, 2))
            return this.sendOutPutEvent("zu wenig / zu viele Anweisungen!", MessageType.error);
        String[] splitText = input.split("\\s+");
        switch (splitText[0]) {
            case "h":
                AutomatEvent automatEvent = new AutomatEvent(this, new HashMap<>(), OperationType.rHersteller);
                automatEventHandler.handle(automatEvent);
                return true;
            case "k":
                return handleReadKuchen(splitText);
            case "a":
                return handleReadAllergene(splitText);
            default:
                sendOutPutEvent("Anweisung nicht erkannt", MessageType.error);
                return false;
        }
    }

    private boolean handleReadAllergene(String[] splitText) {
        HashMap<DataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent;
        if (splitText.length>1) {
            try {
                String enthalten = extractString(splitText[1],1);
                switch(enthalten) {
                    case "i":
                        tempMap.put(DataType.bool, true);
                        automatEvent = new AutomatEvent(this, tempMap, OperationType.rAllergene);
                        automatEventHandler.handle(automatEvent);
                        return true;
                    case "e":
                        tempMap.put(DataType.bool, false);
                        automatEvent = new AutomatEvent(this, tempMap,  OperationType.rAllergene);
                        automatEventHandler.handle(automatEvent);
                        return true;
                    default:
                        sendOutPutEvent("2. Anweisung nicht erkannt", MessageType.error);
                        return false;
                }
            } catch (Exception e) {
                sendOutPutEvent(e.getMessage(),MessageType.error);
                return false;
            }
        } else {
            sendOutPutEvent("2. Anweisung fehlt", MessageType.error);
            return false;
        }
    }

    private boolean handleReadKuchen(String[] splitText) {
        AutomatEvent automatEvent;
        HashMap<DataType, Object> tempMap = new HashMap<>();
        if (splitText.length>1) {
            try {
                KuchenArt kuchenArt = extractKuchenArt(splitText[1]);
                tempMap.put(DataType.kuchenart, kuchenArt);
            } catch (Exception e) {
                sendOutPutEvent(e.getMessage(),MessageType.error);
                return false;
            }
        }
        automatEvent = new AutomatEvent(this, tempMap,  OperationType.rKuchen);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    private boolean handleNoneState(String input) {
        switch (input) {
            case "c":
                return this.changeState(ConsoleState.c);
            case "r":
                return this.changeState(ConsoleState.r);
            case "u":
                return this.changeState(ConsoleState.u);
            case "d":
                return this.changeState(ConsoleState.d);
            case "p":
                return this.changeState(ConsoleState.p);
            case "conf":
                return this.changeState(ConsoleState.config);
            default:
                sendOutPutEvent("Anweisung nicht erkannt", MessageType.error);
                return false;
        }
    }

    private boolean handleCreateState(String input) {
        switch (input) {
            case "h":
                return this.changeState(ConsoleState.ch);
            case "k":
                return this.changeState(ConsoleState.ck);
            default:
                sendOutPutEvent("Anweisung nicht erkannt", MessageType.error);
                return false;
        }
    }

    private boolean handleCreateHersteller(String input) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error);
        else if (!checkCharSize(input, 3, 10)) {
            return sendOutPutEvent("3 - 10 Zeichen erlaubt", MessageType.error);
        }
        ;
        Map<DataType, Object> tempMap = new HashMap<>();
        tempMap.put(DataType.hersteller, splitText[0]);
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, OperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    private boolean handleCreateKuchen(String input) {
        String[] splitText = input.split("\\s+");
        int expectedArguments;
        KuchenArt kuchenArt = null;
        try {
            kuchenArt = extractKuchenArt(splitText[0]);
            switch (kuchenArt) {
                case Obsttorte:
                    expectedArguments = 8;
                    break;
                default:
                    expectedArguments = 7;
                    break;
            }

            if (splitText.length != expectedArguments) {
                sendOutPutEvent("zu wenig/zu viele Argumente", MessageType.error);
                return false;
            }
            String hersteller = extractString(splitText[1], 10);
            BigDecimal preis = extractPreis(splitText[2]);
            int naehrwert = extractInt(splitText[3], 5, "Nährwert nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 5 Zahlen");
            int haltbarkeit = extractInt(splitText[4], 3, "Haltbarkeit nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 3 Zahlen");
            Allergen[] allergene = extractAllergene(splitText[5]);
            String obstsorte;
            String kremsorte;
            Map<DataType, Object> tempMap = new HashMap<>();
            tempMap.put(DataType.kuchenart, kuchenArt);
            tempMap.put(DataType.hersteller, hersteller);
            tempMap.put(DataType.preis, preis);
            tempMap.put(DataType.naehrwert, naehrwert);
            tempMap.put(DataType.haltbarkeit, haltbarkeit);
            tempMap.put(DataType.allergene, allergene);
            switch (kuchenArt) {
                case Kremkuchen:
                    obstsorte = extractString(splitText[6], 10);
                    tempMap.put(DataType.obstsorte, obstsorte);
                    return sendAutomatEvent(tempMap, OperationType.cKuchen);
                case Obstkuchen:
                    kremsorte = extractString(splitText[6], 10);
                    tempMap.put(DataType.kremsorte, kremsorte);
                    return sendAutomatEvent(tempMap, OperationType.cKuchen);
                case Obsttorte:
                    obstsorte = extractString(splitText[6], 10);
                    kremsorte = extractString(splitText[7], 10);
                    tempMap.put(DataType.obstsorte, obstsorte);
                    tempMap.put(DataType.kremsorte, kremsorte);
                    return sendAutomatEvent(tempMap, OperationType.cKuchen);
            }
        } catch (Exception e) {
            sendOutPutEvent(e.getMessage(), MessageType.error);
            return false;
        }
        return true;
    }

    private boolean sendAutomatEvent(Map<DataType, Object> tempMap, OperationType operationType) {
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  operationType);
        automatEventHandler.handle(automatEvent);
        return true;
    }


    private String extractString(String input, int maxChars) throws Exception {
        input = input.toLowerCase();
        if (input.length() > 10) throw new Exception("maximal 10 Buchstaben pro text-argument");
        return input;
    }

    private KuchenArt extractKuchenArt(String input) throws Exception {
        input = input.toLowerCase();
        switch (input) {
            case "kremkuchen":
                return KuchenArt.Kremkuchen;
            case "obstkuchen":
                return KuchenArt.Obstkuchen;
            case "obsttorte":
                return KuchenArt.Obsttorte;
        }
        throw new Exception("Kuchenart nicht erkannt");
    }

    private BigDecimal extractPreis(String input) throws Exception {
        input = input.toLowerCase();
        input = input.replaceAll(",", ".");
        String errorMsg = "Preis nicht erkannt - erlaubtes Format: <ZAHL> oder <ZAHL,ZAHL> oder <ZAHL.ZAHL>, maximal 5 Zeichen";
        try {
            checkSize(5, input, errorMsg);
            Float f = Float.parseFloat(input);
            return BigDecimal.valueOf(f);
        } catch (Exception e) {
            throw new Exception(errorMsg);
        }
    }

    private int extractInt(String input, int maxChars, String errorMsg) throws Exception {
        input = input.toLowerCase();
        try {
            checkSize(maxChars, input, errorMsg);
            int tempN = Integer.parseInt(input);
            return tempN;
        } catch (Exception e) {
            throw new Exception(errorMsg);
        }
    }

    private Allergen[] extractAllergene(String input) throws Exception {
        input = input.toLowerCase();
        String[] inputArr = input.split("\\s*,\\s*");
        HashSet<Allergen> allergenSet = new HashSet<>();
        for (int i = 0; i < inputArr.length; i++) {
            switch (inputArr[i]) {
                case "gluten":
                    allergenSet.add(Allergen.Gluten);
                    break;
                case "erdnuss":
                    allergenSet.add(Allergen.Erdnuss);
                    break;
                case "haselnuss":
                    allergenSet.add(Allergen.Haselnuss);
                    break;
                case "sesamsamen":
                    allergenSet.add(Allergen.Sesamsamen);
                    break;
                default:
                    throw new Exception("Allergen nicht erkannt. [gluten, erdnuss, haselnuss, sesamsamen]");
            }
        }
        return allergenSet.toArray(new Allergen[allergenSet.size()]);
    }

    private void checkSize(int sizeAllowed, String input, String errorMsg) throws Exception {
        if (input.length() > sizeAllowed) {
            throw new Exception(errorMsg);
        }
    }


    private boolean checkArgumentSize(String input, int minSize, int maxSize) {
        String[] splitText = input.split("\\s+");
        return (splitText.length >= minSize && splitText.length <= maxSize);
    }

    private boolean checkCharSize(String input, int minSize, int maxSize) {
        return (input.length() >= minSize && input.length() <= maxSize);
    }


    private boolean handleBack() {
        if (this.getState() == ConsoleState.none) {
            sendOutPutEvent("es geht nicht weiter zurück!", MessageType.error);
            return false;
        } else {
            return this.changeState();
        }
    }

    private boolean changeState() {
        this.modeStack.pop();
        return true;
    }

    private boolean changeState(ConsoleState newCState) {
        this.modeStack.push(newCState);
        return true;
    }

    private ConsoleState getState() {
        return this.modeStack.peek();
    }
}


// Kremkuchen rewe 4,50 386 36 Gluten,Erdnuss Butter
// obstkuchen lidl 4,50 386 36 Gluten,Erdnuss Apfel
// obsttorte rewe 4,50 386 36 Gluten,Erdnuss Kirsche Butter