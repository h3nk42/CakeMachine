package control.lib;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatOperationType;
import control.automat.events.CakeDataType;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ConsoleLib {
    public static String argumentCountWrong = "zu wenig / zu viele Anweisungen!";

    public static long getActualBBF(VerkaufsKuchen verkaufsKuchen){
        long timePassed = new Date().getTime() - verkaufsKuchen.getInsertionDate().getTime();
        timePassed = timePassed / 1000;
        long remainingTime = (verkaufsKuchen.getHaltbarkeit().getSeconds() - timePassed) / 60 / 60;
        return (remainingTime);
    }

    /* CREATE ----------------------------------------------- */
    public static boolean handleCreateHersteller(String input, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source ) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error, outputEventHandler, source);
        else if (!checkCharSize(input, 3, 10)) {
            return sendOutPutEvent("3 - 10 Zeichen erlaubt", MessageType.error, outputEventHandler, source);
        }
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        tempMap.put(CakeDataType.hersteller, splitText[0]);
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static boolean handleCreateKuchen(String input, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source ) {
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
                sendOutPutEvent("Kuchenart passt nicht zur ArgumentAnzahl", MessageType.error, outputEventHandler, source);
                return false;
            }
            String hersteller = extractString(splitText[1], 10);
            BigDecimal preis = extractPreis(splitText[2]);
            int naehrwert = extractInt(splitText[3], 5, "Nährwert nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 5 Zahlen");
            int haltbarkeit = extractInt(splitText[4], 3, "Haltbarkeit nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 3 Zahlen");
            Allergen[] allergene = extractAllergene(splitText[5]);
            String obstsorte;
            String kremsorte;
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.kuchenart, kuchenArt);
            tempMap.put(CakeDataType.hersteller, hersteller);
            tempMap.put(CakeDataType.preis, preis);
            tempMap.put(CakeDataType.naehrwert, naehrwert);
            tempMap.put(CakeDataType.haltbarkeit, haltbarkeit);
            tempMap.put(CakeDataType.allergene, allergene);
            switch (kuchenArt) {
                case Kremkuchen:
                    kremsorte = extractString(splitText[6], 10);
                    tempMap.put(CakeDataType.kremsorte, kremsorte);
                    return sendAutomatEvent(tempMap, AutomatOperationType.cKuchen, automatEventHandler, source);
                case Obstkuchen:
                    obstsorte = extractString(splitText[6], 10);
                    tempMap.put(CakeDataType.obstsorte, obstsorte);
                    return sendAutomatEvent(tempMap, AutomatOperationType.cKuchen, automatEventHandler, source);
                case Obsttorte:
                    obstsorte = extractString(splitText[6], 10);
                    kremsorte = extractString(splitText[7], 10);
                    tempMap.put(CakeDataType.obstsorte, obstsorte);
                    tempMap.put(CakeDataType.kremsorte, kremsorte);
                    return sendAutomatEvent(tempMap, AutomatOperationType.cKuchen, automatEventHandler, source);
            }
        } catch (Exception e) {
            sendOutPutEvent(e.getMessage(), MessageType.error, outputEventHandler, source);
            return false;
        }
        return true;
    }

    /* ----------------------------------------------- */

    /* READ ----------------------------------------------- */

    public static boolean readHersteller (AutomatEventHandler automatEventHandler, Object source) {
        AutomatEvent automatEvent = new AutomatEvent(source, new HashMap<>(), AutomatOperationType.rHersteller);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static boolean handleReadAllergene(String[] splitText, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        HashMap<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent;
        if (splitText.length>1) {
            try {
                String enthalten = extractString(splitText[1],1);
                switch(enthalten) {
                    case "i":
                        tempMap.put(CakeDataType.bool, true);
                        automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.rAllergene);
                        automatEventHandler.handle(automatEvent);
                        return true;
                    case "e":
                        tempMap.put(CakeDataType.bool, false);
                        automatEvent = new AutomatEvent(source, tempMap,  AutomatOperationType.rAllergene);
                        automatEventHandler.handle(automatEvent);
                        return true;
                    default:
                        sendOutPutEvent("2. Anweisung nicht erkannt", MessageType.error, outputEventHandler, source);
                        return false;
                }
            } catch (Exception e) {
                sendOutPutEvent(e.getMessage(),MessageType.error, outputEventHandler, source);
                return false;
            }
        } else {
            sendOutPutEvent("2. Anweisung fehlt", MessageType.error, outputEventHandler, source);
            return false;
        }
    }

    public static boolean handleReadKuchen(String[] splitText, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        AutomatEvent automatEvent;
        HashMap<CakeDataType, Object> tempMap = new HashMap<>();
        if (splitText.length>1) {
            try {
                KuchenArt kuchenArt = extractKuchenArt(splitText[1]);
                tempMap.put(CakeDataType.kuchenart, kuchenArt);
            } catch (Exception e) {
                sendOutPutEvent(e.getMessage(),MessageType.error, outputEventHandler, source);
                return false;
            }
        }
        automatEvent = new AutomatEvent(source, tempMap,  AutomatOperationType.rKuchen);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    /* ----------------------------------------------- */

    /* DELETE ----------------------------------------------- */

    public static boolean handleDeleteHersteller(String input, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error, outputEventHandler, source);
        try {
            String herstellerName = extractString(splitText[0],10);
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.hersteller, herstellerName);
            AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.dHersteller);
            automatEventHandler.handle(automatEvent);
            return true;
        }catch (Exception e) {
            return sendOutPutEvent(e.getMessage() ,MessageType.error, outputEventHandler, source);
        }
    }


    public static boolean handleDeleteKuchen(String input, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        String[] splitText = input.split("\\s+");
        if (splitText.length > 1)
            return sendOutPutEvent("Zu viele Anweisungen! / kein Leerzeichen zu beginn erlaubt ", MessageType.error, outputEventHandler, source);
        else if (!checkCharSize(input, 1, 3)) {
            return sendOutPutEvent("1 - 3 Zeichen erlaubt", MessageType.error, outputEventHandler, source);
        }
        try {
            int fachnummer = Integer.parseInt(splitText[0]);
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer,fachnummer);
            AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.dKuchen);
            automatEventHandler.handle(automatEvent);
            return true;
        }catch (Exception e) {
            return sendOutPutEvent("keine nummer angegeben!",MessageType.error, outputEventHandler, source);
        }
    }

    /* ----------------------------------------------- */

    /* UPDATE ----------------------------------------------- */

    public static boolean handleUpdateKuchen(String input, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        String[] splitText = input.split("\\s+");
        if (!checkCharSize(input, 1, 3)) {
            return sendOutPutEvent("1 - 3 Zeichen erlaubt", MessageType.error, outputEventHandler, source);
        }
        try {
            int fachnummer = Integer.parseInt(splitText[0]);
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer,fachnummer);
            AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.inspectKuchen);
            automatEventHandler.handle(automatEvent);
            return true;
        }catch (Exception e) {
            return sendOutPutEvent("keine nummer angegeben!",MessageType.error, outputEventHandler, source);
        }
    }

    /* ----------------------------------------------- */

    /* PERSIST ----------------------------------------------- */

    public static boolean handleSaveJOS(AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.pJOS);
        automatEventHandler.handle(automatEvent);
        sendOutPutEvent("State saved via JOS", MessageType.success, outputEventHandler, source);
        return true;
    }
    public static boolean handleSaveJOS(AutomatEventHandler automatEventHandler, Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.pJOS);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static boolean handleLoadJOS(AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.lJOS);
        automatEventHandler.handle(automatEvent);
        sendOutPutEvent("State loaded via JOS", MessageType.success, outputEventHandler, source);
        return true;
    }
    public static boolean handleLoadJOS(AutomatEventHandler automatEventHandler,  Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.lJOS);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static boolean handleSaveJBP(AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.pJOS);
        automatEventHandler.handle(automatEvent);
        sendOutPutEvent("State saved via JOS", MessageType.success, outputEventHandler, source);
        return true;
    }

    public static boolean handleLoadJBP(AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, Object source) {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.pJOS);
        automatEventHandler.handle(automatEvent);
        sendOutPutEvent("State saved via JOS", MessageType.success, outputEventHandler, source);
        return true;
    }

    /* HELPERS */
    public static boolean checkCharSize(String input, int minSize, int maxSize) {
        return (input.length() >= minSize && input.length() <= maxSize);
    }

    public static boolean sendOutPutEvent(String textToSend, MessageType messageType, OutputEventHandler outputEventHandler, Object source ) {
        OutputEvent oEventPrintText = new OutputEvent(source, textToSend, messageType);
        outputEventHandler.handle(oEventPrintText);
        return true;
    }

    public static boolean sendAutomatEvent(Map<CakeDataType, Object> tempMap, AutomatOperationType automatOperationType, AutomatEventHandler automatEventHandler, Object source) {
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, automatOperationType);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static String[] extractArguments(String input) {
        return input.split("\\s+");
    }

    public static KuchenArt extractKuchenArt(String input) throws Exception {
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

    private static String extractString(String input, int maxChars) throws Exception {
        input = input.toLowerCase();
        if (input.length() > 10) throw new Exception("maximal 10 Buchstaben pro text-argument");
        return input;
    }

    private static BigDecimal extractPreis(String input) throws Exception {
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

    private static void checkSize(int sizeAllowed, String input, String errorMsg) throws Exception {
        if (input.length() > sizeAllowed) {
            throw new Exception(errorMsg);
        }
    }

    private static int extractInt(String input, int maxChars, String errorMsg) throws Exception {
        input = input.toLowerCase();
        try {
            checkSize(maxChars, input, errorMsg);
            int tempN = Integer.parseInt(input);
            return tempN;
        } catch (Exception e) {
            throw new Exception(errorMsg);
        }
    }

    public static boolean isNumeric(String word) {
        return word.chars().allMatch( Character::isDigit );
    }

    public static Allergen[] extractAllergene(String input) throws Exception {
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
                case "":
                    break;
                default:
                    throw new Exception("Allergen nicht erkannt. [gluten, erdnuss, haselnuss, sesamsamen]");
            }
        }
        return allergenSet.toArray(new Allergen[allergenSet.size()]);
    }
}
