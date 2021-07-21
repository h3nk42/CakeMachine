package lib;

import control.automat.events.DataType;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import model.automat.verkaufsobjekte.kuchen.KuchenComparators;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

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

    public static Map<DataType, Object> rollCake(Random r){
        Map<DataType, Object> cakeData = new HashMap<>();
        KuchenArt kuchenArt = kuchenArtArr[rollIndex(r,kuchenArtArr.length)];
        cakeData.put(DataType.kuchenart, kuchenArt);
        cakeData.put(DataType.hersteller, herstellerArr[rollIndex(r,herstellerArr.length)]);
        cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
        cakeData.put(DataType.preis,preisArr[rollIndex(r,preisArr.length)]);
        cakeData.put(DataType.naehrwert,naehrwertArr[rollIndex(r,naehrwertArr.length)]);
        cakeData.put(DataType.haltbarkeit,haltbarkeitArr[rollIndex(r,haltbarkeitArr.length)]);
        cakeData.put(DataType.allergene,allergenArr[rollIndex(r,allergenArr.length)]);
        switch (kuchenArt) {
            case Kremkuchen:
                cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
            case Obstkuchen:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(r,obstsorteArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
            case Obsttorte:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(r,obstsorteArr.length)]);
                cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(r,kremsorterArr.length)]);
                //createCakeSimSpecific(cakeData);
                break;
        }
        return cakeData;
    }

    public static Map<DataType, Object> rollFachnummer(Random r, List<VerkaufsKuchen> kuchen){
        Map<DataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Integer random = rollIndex(r, kuchen.size());
            VerkaufsKuchen vk = kuchen.get(random);
            tempMap.put(DataType.fachnummer, vk.getFachnummer());
            return tempMap;
        }
        else return null;
    }

    public static Map<DataType, Object> getOldestCake(Random r, List<VerkaufsKuchen> kuchen){
        Map<DataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Collections.sort(kuchen, new KuchenComparators.InspektionsDatumComparator());
            VerkaufsKuchen vk = kuchen.get(0);
            tempMap.put(DataType.fachnummer, vk.getFachnummer());
            return tempMap;
        }
        else return null;
    }

    public static Integer rollIndex(Random r, int maxIndex) {
        return r.nextInt(maxIndex);
    }
}
