package testLib;

import control.automat.events.CakeDataType;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.Kuchen;
import model.verkaufsobjekte.kuchen.KuchenArt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class TestStaticVariables {

    public static class TestMaps{
        public static Map<CakeDataType, Object> returnTestMap(KuchenArt kuchenArt) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();

            tempMap.put(CakeDataType.hersteller, "rewe");
            tempMap.put(CakeDataType.preis, BigDecimal.valueOf(4));
            tempMap.put(CakeDataType.naehrwert, 300);
            tempMap.put(CakeDataType.haltbarkeit, 24);
            tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
            switch(kuchenArt) {
                case Kremkuchen:
                    tempMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
                    tempMap.put(CakeDataType.kremsorte, "vanille");
                    break;
                case Obstkuchen:
                       tempMap.put(CakeDataType.kuchenart, KuchenArt.Obstkuchen);
                    tempMap.put(CakeDataType.obstsorte, "apfel");
                    break;
                case Obsttorte:
                    tempMap.put(CakeDataType.kuchenart, KuchenArt.Obsttorte);
                    tempMap.put(CakeDataType.kremsorte, "vanille");
                    tempMap.put(CakeDataType.obstsorte, "apfel");
                    break;
                case Unknown:
                    tempMap.put(CakeDataType.kuchenart, KuchenArt.Unknown);
            }
            return tempMap;
        }

        public static Map<CakeDataType, Object> returnHerstellerMap(String name) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.hersteller, name);
            return tempMap;
        }

        public static Map<CakeDataType, Object> returnFachnummerMap(int fach) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer, fach);
            return tempMap;
        }
        public static Map<CakeDataType, Object> returnFachnummerMap(int fach, int fach2) {
            Map<CakeDataType, Object> tempMap = new HashMap<>();
            tempMap.put(CakeDataType.fachnummer, new int[]{fach,fach2});
            return tempMap;
        }
    }
}
