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

    public static boolean handleSaveJOS(AutomatEventHandler automatEventHandler, Object source) throws Exception {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.pJOS);
        automatEventHandler.handle(automatEvent);
        return true;
    }

    public static boolean handleLoadJOS(AutomatEventHandler automatEventHandler,  Object source) throws Exception {
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent = new AutomatEvent(source, tempMap, AutomatOperationType.lJOS);
        automatEventHandler.handle(automatEvent);
        return true;
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
