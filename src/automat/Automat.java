package automat;

import automat.hersteller.Hersteller;
import automat.verkaufsobjekte.kuchen.Kuchen;
import automat.verkaufsobjekte.kuchen.KuchenArt;
import automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Automat {

    public List<VerkaufsKuchen> fächer;
    public HashMap<Hersteller, Integer> kuchenCounter;
    public HashMap<KuchenArt, ArrayList<VerkaufsKuchen>> kuchenMap;

    public Automat() {
        this.fächer = new ArrayList<>();
        this.kuchenCounter = new HashMap<Hersteller, Integer>();
        this.kuchenMap = new HashMap<KuchenArt,ArrayList<VerkaufsKuchen>>();
    }

    public List<VerkaufsKuchen> getKuchen() {
        List<VerkaufsKuchen> tempList = new ArrayList<VerkaufsKuchen>();
        for (KuchenArt kuchenArt : KuchenArt.values()) {
           tempList.addAll(this.kuchenMap.get(kuchenArt));
        }
        return tempList;
    }
    public List<VerkaufsKuchen> getKuchen(KuchenArt kuchenArt) {
        return this.kuchenMap.get(kuchenArt);
    }

    public Integer addObject(VerkaufsKuchen object) throws Exception {
        if (object.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        }
        if (!this.fächer.isEmpty()) {
            for (int i = 0; i < this.fächer.size(); i++) {
                if (this.fächer.get(i) == null) {
                    this.fächer.set(i, object);
                    object.setFachnummer(i);
                    incKuchenCounter(object.getHersteller());
                    addToKuchenMap(object);
                    return i;
                }
            }
        }
        this.fächer.add(object);
        object.setFachnummer(this.fächer.size()-1);
        incKuchenCounter(object.getHersteller());
        addToKuchenMap(object);
        return this.fächer.size()-1;
    }

    private void incKuchenCounter(Hersteller hersteller) {
        if (this.kuchenCounter.get(hersteller) == null) {
            this.kuchenCounter.put(hersteller, 1);
        } else {
            this.kuchenCounter.put(hersteller, this.kuchenCounter.get(hersteller)+1);
        }
    }

    private void decKuchenCounter(Hersteller hersteller) {
            this.kuchenCounter.put(hersteller, this.kuchenCounter.get(hersteller)-1);
    }

    private void addToKuchenMap(VerkaufsKuchen object) {
        KuchenArt tempKuchenArt = object.getKuchenArt();
        ArrayList<VerkaufsKuchen> tempList = this.kuchenMap.get(tempKuchenArt);
        if (tempList == null) {
            ArrayList<VerkaufsKuchen> newList = new ArrayList<VerkaufsKuchen>();
            newList.add(object);
            this.kuchenMap.put(tempKuchenArt, newList);
            return;
        } else {
            if(!tempList.contains(object)) {
                tempList.add(object);
                return;
            }
        }
    }

    public void removeObject(int index) throws Exception {
        if (index >= this.fächer.size()){
            throw new Exception("index out of bounds");
        }
        VerkaufsKuchen tempKuchen = this.fächer.get(index);
        tempKuchen.setFachnummer(-1);
        decKuchenCounter(tempKuchen.getHersteller());
        this.kuchenMap.get(tempKuchen.getKuchenArt()).remove(tempKuchen);
        this.fächer.set(index, null);
    }
}
