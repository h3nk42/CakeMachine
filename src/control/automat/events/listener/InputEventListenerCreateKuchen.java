package control.automat.events.listener;

import control.automat.hersteller.Hersteller;
import control.automat.verkaufsobjekte.Allergen;
import control.automat.verkaufsobjekte.kuchen.KuchenArt;
import control.console.Console;
import view.consoleReader.InputEvent;
import view.consoleReader.InputEventListener;

import java.math.BigDecimal;
import java.util.HashSet;

public class InputEventListenerCreateKuchen implements InputEventListener {

    private Console c;

    public InputEventListenerCreateKuchen(Console c) {
        this.c = c;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String text = event.getText();
           /* StringBuilder sb = new StringBuilder(text);
            sb.*/
            String[] splitText = text.split("\\s+");
            int expectedArguments;
            try {
                KuchenArt kuchenArt = extractArt(splitText[0]);
                switch(kuchenArt){
                    case Obsttorte:
                        expectedArguments = 8-1;
                        break;
                    default:
                        expectedArguments = 7-1;
                        break;
                }
                if (splitText.length<expectedArguments) {
                    throw new Exception("zu wenig Argumente!");
                }
                Hersteller hersteller = extractHersteller(splitText[1]);
                BigDecimal preis = extractPreis(splitText[2]);
                int naehrwert = extractInt(splitText[3], 5, "Nährwert nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 5 Zahlen");
                int haltbarkeit = extractInt(splitText[4], 3, "Haltbarkeit nicht erkannt - erlaubtes Format: <ZAHL> keine zeichen, maximal 3 Zahlen");
                Allergen[] allergene = extractAllergene(splitText[5]);
                String obstsorte;
                String kremsorte;
                switch(kuchenArt){
                    /*case Kremkuchen:
                        obstsorte = extractString(splitText[6]);
                        c.getAutomat().createKuchen(kuchenArt,hersteller,preis, naehrwert, allergene, new String[]{obstsorte}, haltbarkeit);
                        break;
                    case Obstkuchen:
                        kremsorte = extractString(splitText[6]);
                        c.getAutomat().createKuchen(kuchenArt,hersteller,preis, naehrwert, allergene, new String[]{kremsorte}, haltbarkeit);
                        break;
                    case Obsttorte:
                        obstsorte = extractString(splitText[6]);
                        kremsorte = extractString(splitText[7]);
                        c.getAutomat().createKuchen(kuchenArt,hersteller,preis, naehrwert, allergene, new String[]{obstsorte,kremsorte}, haltbarkeit);
                        break;*/
                }
            } catch (Exception e) {
                System.out.println("\u001B[31m \n --- " + e.getMessage() + " --- \n \u001B[0m");
            }
        }
    }

    private String extractString(String input) {
        input = input.toLowerCase();
        return input;
    }

    private Allergen[] extractAllergene(String input) throws Exception {
        input = input.toLowerCase();
        String[] inputArr = input.split("\\s*,\\s*");
        HashSet<Allergen> allergenSet = new HashSet<>();
        for (int i = 0; i < inputArr.length; i++) {
            switch(inputArr[i]) {
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
                    throw new Exception("Allergen nicht erkannt");
            }

        }
      return allergenSet.toArray(new Allergen[allergenSet.size()]);
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

    private KuchenArt extractArt(String input) throws Exception {
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

    private Hersteller extractHersteller(String input) throws Exception {
       /* input = input.toLowerCase();
        Hersteller tempH = c.getAutomat().getHersteller(input);
        if (tempH != null) {
            return tempH;
        } else {
            throw new Exception("Hersteller nicht erkannt");
        }*/
        return null;
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

    private void checkSize(int sizeAllowed, String input, String errorMsg) throws Exception {
        if (input.length() > sizeAllowed) {
            throw new Exception(errorMsg);
        }
    }


    @Override
    public String toString() {
        return "create";
    }

    /*Kremkuchen r 4,50 386 36 Gluten,Erdnuss Butter*/

    // b b b b b  haselnuss,gluten
}
