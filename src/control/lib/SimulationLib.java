package control.lib;

import control.automat.events.CakeDataType;
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import model.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.math.BigDecimal;
import java.util.*;

public class SimulationLib {

    private static String[] herstellerArr = new String[]{"rewe","lidl","frodo"};
    private static KuchenArt[] kuchenArtArr = new KuchenArt[]{KuchenArt.Kremkuchen,KuchenArt.Obsttorte, KuchenArt.Obstkuchen};
    private static String[] kremsorterArr = new String[]{"vanille","schoko","sahne"};
    private static String[] obstsorteArr = new String[]{"apfel","birne","kirsch"};
    private static BigDecimal[] preisArr =new BigDecimal[]{BigDecimal.valueOf(1.25),BigDecimal.valueOf(1.5),BigDecimal.valueOf(2.25),BigDecimal.valueOf(3.9)};
    private static Integer[] naehrwertArr = new Integer[]{250,325,410,550};
    private static Integer[] haltbarkeitArr = new Integer[]{24,32,36,42};
    private static Allergen[][] allergenArr = new Allergen[][]{new Allergen[] {Allergen.Gluten},new Allergen[] {Allergen.Gluten,Allergen.Erdnuss}, new Allergen[] {Allergen.Erdnuss, Allergen.Haselnuss,Allergen.Sesamsamen},new Allergen[] {Allergen.Gluten,Allergen.Sesamsamen}};

    public static Map<CakeDataType, Object> rollCake(Random r){
        Map<CakeDataType, Object> cakeData = new HashMap<>();
        KuchenArt kuchenArt = kuchenArtArr[rollIndex(r,kuchenArtArr.length)];
        cakeData.put(CakeDataType.kuchenart, kuchenArt);
        cakeData.put(CakeDataType.hersteller, herstellerArr[rollIndex(r,herstellerArr.length)]);
        cakeData.put(CakeDataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
        cakeData.put(CakeDataType.preis,preisArr[rollIndex(r,preisArr.length)]);
        cakeData.put(CakeDataType.naehrwert,naehrwertArr[rollIndex(r,naehrwertArr.length)]);
        cakeData.put(CakeDataType.haltbarkeit,haltbarkeitArr[rollIndex(r,haltbarkeitArr.length)]);
        cakeData.put(CakeDataType.allergene,allergenArr[rollIndex(r,allergenArr.length)]);
        switch (kuchenArt) {
            case Kremkuchen:
                cakeData.put(CakeDataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
            case Obstkuchen:
                cakeData.put(CakeDataType.obstsorte,obstsorteArr[rollIndex(r,obstsorteArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
            case Obsttorte:
                cakeData.put(CakeDataType.obstsorte,obstsorteArr[rollIndex(r,obstsorteArr.length)]);
                cakeData.put(CakeDataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
        }
        return cakeData;
    }

    public static Map<CakeDataType, Object> rollFachnummer(Random r, List<VerkaufsKuchen> kuchen){
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Integer random = rollIndex(r, kuchen.size());
            VerkaufsKuchen vk = kuchen.get(random);
            tempMap.put(CakeDataType.fachnummer, vk.getFachnummer());
            return tempMap;
        }
        else return null;
    }

    public static Map<CakeDataType, Object> getOldestCake(Random r, List<VerkaufsKuchen> kuchen){
        Map<CakeDataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Collections.sort(kuchen, new KuchenComparators.InspektionsDatumComparator());
            VerkaufsKuchen vk = kuchen.get(0);
            tempMap.put(CakeDataType.fachnummer, vk.getFachnummer());
            return tempMap;
        }
        else return null;
    }

    public static Integer rollIndex(Random r, int maxIndex) {
        return r.nextInt(maxIndex);
    }
}
