package automat;

import automat.hersteller.Hersteller;
import automat.hersteller.HerstellerFactory;
import automat.hersteller.HerstellerFactoryImpl;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.kuchen.KuchenArt;
import automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.lang.reflect.Array;
import java.util.*;


public class Automat {

    private List<VerkaufsKuchen> faecher;
    private Map<Hersteller, Integer> kuchenCounter;
    private Map<KuchenArt, ArrayList<VerkaufsKuchen>> kuchenMap;
    private Map<Allergen, Integer> allergeneVorhanden;
    private HerstellerFactory herstellerFactory;

    public Automat(Integer fachAnzahl) {
        this.faecher = new ArrayList<>(fachAnzahl);
        this.kuchenCounter = new HashMap<>();
        this.kuchenMap = new HashMap<>();
        this.allergeneVorhanden = new HashMap<>();
        this.herstellerFactory = new HerstellerFactoryImpl();
    }

    public Hersteller getHersteller(String herstellerName) {
        return herstellerFactory.getHerstellerListe().get(herstellerName.toLowerCase());
    }
    public HashMap<String, Hersteller> getHersteller() {
        return herstellerFactory.getHerstellerListe();
    }

    public Hersteller addHersteller(String herstellerName ) throws Exception {
        return herstellerFactory.produceHersteller(herstellerName);
    }

    public void removeHersteller(String herstellerName ) {
        faecher.forEach(kuchen -> {
            if (kuchen != null) {
                    if ( kuchen.getHersteller().getName().equalsIgnoreCase(herstellerName)){
                        try {
                            this.removeKuchen(kuchen.getFachnummer());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
        herstellerFactory.deleteHersteller(herstellerName);
    }

    public List<VerkaufsKuchen> getFaecher() {
        return faecher;
    }

    public Integer getKuchenCounter(Hersteller hersteller) {
        return kuchenCounter.get(hersteller);
    }

    public Map<KuchenArt, ArrayList<VerkaufsKuchen>> getKuchenMap() {
        return kuchenMap;
    }

    public Map<Allergen, Integer> getAllergeneVorhanden() {
        return allergeneVorhanden;
    }

    public List<VerkaufsKuchen> getKuchen() {
        List<VerkaufsKuchen> tempList = new ArrayList<VerkaufsKuchen>();
        for (KuchenArt kuchenArt : KuchenArt.values()) {
            if (this.kuchenMap.get(kuchenArt) != null) {
                tempList.addAll(this.kuchenMap.get(kuchenArt));
            }
        }
        return tempList;
    }
    public List<VerkaufsKuchen> getKuchen(KuchenArt kuchenArt) {
        return this.kuchenMap.get(kuchenArt);
    }

    public void addKuchen(ArrayList<VerkaufsKuchen> kuchenArr ) throws Exception {
        for (int i = 0; i < kuchenArr.size(); i++) {
            addKuchen(kuchenArr.get(i));
        }
    }

    public  void  addKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (kuchen.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        } else if (this.getHersteller(kuchen.getHersteller().getName()) == null) {
            throw new Exception("Hersteller des Kuchens nicht existent");
        }
        if (this.kuchenMap.get(kuchen.getKuchenArt()) != null && this.kuchenMap.get(kuchen.getKuchenArt()).contains(kuchen) ) {
            throw new Exception("Kuchen bereits im Automaten");
        }
            for (int i = 0; i < this.faecher.size(); i++) {
                if (this.faecher.get(i) == null) {
                    this.faecher.set(i, kuchen);
                    this.kuchenSetup(i, kuchen);
                    return;
                }
            }
            this.faecher.add(kuchen);
            this.kuchenSetup(this.faecher.size()-1, kuchen);
    }

    private void kuchenSetup(Integer index, VerkaufsKuchen kuchen) {
        kuchen.setFachnummer(index);
        incKuchenCounter(kuchen.getHersteller());
        addToKuchenMap(kuchen);
        kuchen.getAllergene().forEach((this::incAllergen));
    }

    private void incKuchenCounter(Hersteller hersteller) {
        this.kuchenCounter.merge(hersteller, 1, Integer::sum);
    }

    private void decKuchenCounter(Hersteller hersteller) throws Exception {
        if (this.kuchenCounter.get(hersteller) == 0) {
            throw new Exception("Hersteller besitzt keinen Kuchen im Automaten!");
        } else {
            this.kuchenCounter.put(hersteller, this.kuchenCounter.get(hersteller)-1);
        }
    }

    private void incAllergen(Allergen allergen) {
        this.allergeneVorhanden.merge(allergen, 1, Integer::sum);
    }

    private void decAllergen(Allergen allergen) throws Exception {
        if (this.allergeneVorhanden.get(allergen) == null) {
            throw new Exception("Allergen nicht vorhanden");
        } else {
            if (this.allergeneVorhanden.get(allergen) == 1 ) {
                this.allergeneVorhanden.remove(allergen);
            } else {
                this.allergeneVorhanden.put(allergen, this.allergeneVorhanden.get(allergen) - 1);
            }
        }
    }

    private void addToKuchenMap(VerkaufsKuchen kuchen) {
        KuchenArt tempKuchenArt = kuchen.getKuchenArt();
        ArrayList<VerkaufsKuchen> tempList = this.kuchenMap.get(tempKuchenArt);
        if (tempList == null) {
            ArrayList<VerkaufsKuchen> newList = new ArrayList<>();
            newList.add(kuchen);
            this.kuchenMap.put(tempKuchenArt, newList);
        } else {
            if(!tempList.contains(kuchen)) {
                tempList.add(kuchen);
            }
        }
    }

    public void removeKuchen(int index) throws Exception {
        if (index >= this.faecher.size() || index < 0){
            throw new Exception("index out of bounds: " + index);
        } else if ( this.faecher.get(index) == null) {
            throw new Exception("Fach bereits leer");
        }
        VerkaufsKuchen tempKuchen = this.faecher.get(index);
        setUpDeleteKuchen(tempKuchen,index);
    }

    public void setUpDeleteKuchen(VerkaufsKuchen kuchen, Integer i) throws Exception {
        kuchen.setFachnummer(-1);
        kuchen.getAllergene().forEach(allergen -> {
            try {
                decAllergen(allergen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        decKuchenCounter(kuchen.getHersteller());
        this.kuchenMap.get(kuchen.getKuchenArt()).remove(kuchen);
        this.faecher.set(i, null);
    }

    public void removeKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (this.kuchenMap.get(kuchen.getKuchenArt()) == null || !this.kuchenMap.get(kuchen.getKuchenArt()).contains(kuchen) ) {
            throw new Exception("Kuchen nicht im Automaten");
        }
        for (int i = 0; i < faecher.size() ; i++) {
            if ( faecher.get(i) != null && faecher.get(i) == kuchen) {
                setUpDeleteKuchen(kuchen,i);
            }
        }

    }
}
