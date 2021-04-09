package automat;

import automat.hersteller.Hersteller;
import automat.hersteller.HerstellerFactory;
import automat.hersteller.HerstellerFactoryImpl;
import automat.verkaufsobjekte.Allergen;
import automat.verkaufsobjekte.kuchen.KuchenArt;
import automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.*;


public class Automat {
    private List<VerkaufsKuchen> fächer;
    private Map<Hersteller, Integer> kuchenCounter;
    private Map<KuchenArt, ArrayList<VerkaufsKuchen>> kuchenMap;
    private Map<Allergen, Integer> allergeneVorhanden;
    private HerstellerFactory herstellerFactory;

    public Automat(Integer fachAnzahl) {
        this.fächer = new ArrayList<>(fachAnzahl);
        this.kuchenCounter = new HashMap<>();
        this.kuchenMap = new HashMap<>();
        this.allergeneVorhanden = new HashMap<>();
        this.herstellerFactory = new HerstellerFactoryImpl();
    }

    public Hersteller getHersteller(String herstellerName) {
        return herstellerFactory.getHerstellerListe().get(herstellerName.toLowerCase());
    }

    public Hersteller addHersteller(String herstellerName ) throws Exception {
        return herstellerFactory.produceHersteller(herstellerName);
    }

    public void removeHersteller(String herstellerName ) {
        fächer.forEach(kuchen -> {
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
        return fächer;
    }

    public Map<Hersteller, Integer> getKuchenCounter() {
        return kuchenCounter;
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

    public  void  addKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (kuchen.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        }
            for (int i = 0; i < this.fächer.size(); i++) {
                if (this.fächer.get(i) == null) {
                    this.fächer.set(i, kuchen);
                    this.kuchenSetup(i, kuchen);
                    return;
                }
            }
            this.fächer.add(kuchen);
            this.kuchenSetup(this.fächer.size()-1, kuchen);
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
            ArrayList<VerkaufsKuchen> newList = new ArrayList<VerkaufsKuchen>();
            newList.add(kuchen);
            this.kuchenMap.put(tempKuchenArt, newList);
        } else {
            if(!tempList.contains(kuchen)) {
                tempList.add(kuchen);
            }
        }
    }

    public void removeKuchen(int index) throws Exception {
        if (index >= this.fächer.size() || index < 0){
            throw new Exception("index out of bounds: " + index);
        } else if ( this.fächer.get(index) == null) {
            throw new Exception("Fach bereits leer");
        }
        VerkaufsKuchen tempKuchen = this.fächer.get(index);
        tempKuchen.setFachnummer(-1);
        tempKuchen.getAllergene().forEach(allergen -> {
            try {
                decAllergen(allergen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        decKuchenCounter(tempKuchen.getHersteller());
        this.kuchenMap.get(tempKuchen.getKuchenArt()).remove(tempKuchen);
        this.fächer.set(index, null);
    }
}
