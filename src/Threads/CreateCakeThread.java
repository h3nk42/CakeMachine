package Threads;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatOperationType;
import control.automat.events.DataType;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateCakeThread extends Thread{

    private AutomatSimWrapper automatSimWrapper;
    private int sleep;
    private AutomatController automatController;
    private AutomatEventHandler automatEventHandler;
    private String[] herstellerArr = new String[]{"rewe","lidl","frodo"};
    private KuchenArt[] kuchenArtArr = new KuchenArt[]{KuchenArt.Kremkuchen,KuchenArt.Obsttorte, KuchenArt.Obstkuchen};
    private String[] kremsorterArr = new String[]{"vanille","schoko","sahne"};
    private String[] obstsorteArr = new String[]{"apfel","birne","kirsch"};
    private BigDecimal[] preisArr =new BigDecimal[]{BigDecimal.valueOf(1.25),BigDecimal.valueOf(1.5),BigDecimal.valueOf(2.25),BigDecimal.valueOf(3.9)};
    private Integer[] naehrwertArr = new Integer[]{250,325,410,550};
    private Integer[] haltbarkeitArr = new Integer[]{24,32,36,42};
    private Allergen[][] allergenArr = new Allergen[][]{new Allergen[] {Allergen.Gluten},new Allergen[] {Allergen.Gluten,Allergen.Erdnuss}, new Allergen[] {Allergen.Erdnuss, Allergen.Haselnuss,Allergen.Sesamsamen},new Allergen[] {Allergen.Gluten,Allergen.Sesamsamen}};
    private static Random r;
    private SimulationType simulationType;
    public CreateCakeThread(AutomatSimWrapper automatSimWrapper, int sleep, AutomatController automatController, AutomatEventHandler automatEventHandler, boolean isTest, SimulationType simulationType) {
        this.simulationType = simulationType;
        this.sleep = sleep;
        this.automatController = automatController;
        this.automatEventHandler = automatEventHandler;
        this.automatSimWrapper = automatSimWrapper;
        if (isTest)  this.r = new Random(Long.parseLong("0123456789"));
        else this.r = new Random();
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
                createCakeSimSpecific(cakeData);
                break;
            case Obstkuchen:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(obstsorteArr.length)]);
                createCakeSimSpecific(cakeData);
                break;
            case Obsttorte:
                cakeData.put(DataType.obstsorte,obstsorteArr[rollIndex(obstsorteArr.length)]);
                cakeData.put(DataType.kremsorte,kremsorterArr[rollIndex(kremsorterArr.length)]);
                createCakeSimSpecific(cakeData);
                break;
        }
        sleep(sleep);
        return cakeData;
    }

    public Integer rollIndex(int maxIndex) {
        return r.nextInt(maxIndex);
    }

    private void createCakeSimSpecific(Map<DataType, Object> cakeData) throws InterruptedException {
        switch (this.simulationType){
            case sim1:
                AutomatEvent automatEvent;
                automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.cKuchen);
                automatEventHandler.handle(automatEvent);
                break;
            case sim2:
                automatSimWrapper.addCake(cakeData);
                break;
            case sim3:
                break;
        }

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
