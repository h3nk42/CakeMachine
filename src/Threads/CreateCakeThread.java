package Threads;

import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.OperationType;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;

import java.math.BigDecimal;
import java.util.*;

public class CreateCakeThread extends Thread{

    private AutomatSimWrapper a;
    private int sleep;
    private String[] herstellerArr = new String[]{"rewe","lidl","frodo"};
    private KuchenArt[] kuchenArtArr = new KuchenArt[]{KuchenArt.Kremkuchen,KuchenArt.Obsttorte, KuchenArt.Obstkuchen};
    private String[] kremsorterArr = new String[]{"vanille","schoko","sahne"};
    private String[] obstsorteArr = new String[]{"apfel","birne","kirsch"};
    private BigDecimal[] preisArr =new BigDecimal[]{BigDecimal.valueOf(1.25),BigDecimal.valueOf(1.5),BigDecimal.valueOf(2.25),BigDecimal.valueOf(3.9)};
    private Integer[] naehrwertArr = new Integer[]{250,325,410,550};
    private Integer[] haltbarkeitArr = new Integer[]{24,32,36,42};
    private Allergen[][] allergenArr = new Allergen[][]{new Allergen[] {Allergen.Gluten},new Allergen[] {Allergen.Gluten,Allergen.Erdnuss}, new Allergen[] {Allergen.Erdnuss, Allergen.Haselnuss,Allergen.Sesamsamen},new Allergen[] {Allergen.Gluten,Allergen.Sesamsamen}};
    private static Random r = new Random(Long.parseLong("0123456789"));

    public CreateCakeThread(AutomatSimWrapper a, int sleep) {
        this.a = a;
        this.sleep = sleep;
    }

    private Map<DataType, Object> rollCake() throws Exception {
        Map<DataType, Object> cakeData = new HashMap<>();
        KuchenArt kuchenArt = kuchenArtArr[rollIndex(kuchenArtArr.length)];
        cakeData.put(DataType.kuchenart, kuchenArt);
        cakeData.put(DataType.hersteller, herstellerArr[rollIndex(herstellerArr.length)]);
        cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(kremsorterArr.length)]);
        cakeData.put(DataType.preis,preisArr[rollIndex(preisArr.length)]);
        cakeData.put(DataType.naehrwert,naehrwertArr[rollIndex(naehrwertArr.length)]);
        cakeData.put(DataType.haltbarkeit,haltbarkeitArr[rollIndex(haltbarkeitArr.length)]);
        cakeData.put(DataType.allergene,allergenArr[rollIndex(allergenArr.length)]);

        switch (kuchenArt) {
            case Kremkuchen:
                cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(kremsorterArr.length)]);
                a.addCake(cakeData);
                break;
            case Obstkuchen:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(obstsorteArr.length)]);
                a.addCake(cakeData);
                break;
            case Obsttorte:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(obstsorteArr.length)]);
                cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(kremsorterArr.length)]);
                a.addCake(cakeData);
                break;
        }
        System.out.println("Threads/CreateCakeThread.java: lineNumber: 58: " + "try to create cake!");
        sleep(sleep);
        return cakeData;
    }

    public Integer rollIndex(int maxIndex) {
        return r.nextInt(maxIndex);
    }

    public void run(){
        while(true) {
            try {
                rollCake();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
