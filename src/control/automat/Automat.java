package control.automat;

import model.automat.hersteller.Hersteller;
import model.automat.hersteller.HerstellerFactory;
import model.automat.hersteller.HerstellerFactoryImpl;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.Verkaufsobjekt;
import model.automat.verkaufsobjekte.kuchen.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


public class Automat implements Serializable {

    private volatile List<VerkaufsKuchen> faecher;
    private volatile Map<Hersteller, Integer> kuchenCounter;
    private volatile Map<KuchenArt, ArrayList<VerkaufsKuchen>> kuchenMap;
    private volatile Map<Allergen, Integer> allergeneVorhanden;
    private volatile HerstellerFactory herstellerFactory;
    private volatile Map<VerkaufsKuchen, Date> inspektionsDaten;
    private volatile Integer fachAnzahl;

    public Automat(Integer fachAnzahl) {
        this.fachAnzahl = fachAnzahl;
        this.faecher = new ArrayList<>(fachAnzahl);
        this.kuchenCounter = new HashMap<>();
        this.kuchenMap = new HashMap<>();
        this.allergeneVorhanden = new HashMap<>();
        this.herstellerFactory = new HerstellerFactoryImpl();
        this.inspektionsDaten = new HashMap<>();
        for (KuchenArt kuchenArt : KuchenArt.values()) {
           this.kuchenMap.put(kuchenArt, new ArrayList<VerkaufsKuchen>() );
        }
        for (int i = 0; i < fachAnzahl; i++) {
            this.faecher.add(null);
        }
    }

    public Hersteller getHersteller(String herstellerName) {
        return herstellerFactory.getHerstellerListe().get(herstellerName.toLowerCase());
    }
    public Collection<Hersteller> getHersteller() {
        Collection<Hersteller> returnCopy = new ArrayList<>(herstellerFactory.getHerstellerListe().values());
        return returnCopy;
    }

    public synchronized Hersteller createHersteller(String herstellerName ) throws Exception {
        Hersteller newHersteller =  herstellerFactory.produceHersteller(herstellerName);
        this.kuchenCounter.put(newHersteller, getKuchenCounter(newHersteller));
        return newHersteller;
    }

    public synchronized void deleteHersteller(String herstellerName ) throws Exception {
        Hersteller tempHersteller = getHersteller(herstellerName);
        if(tempHersteller== null) {
            throw new Exception("Hersteller nicht gefunden");
        }
        faecher.forEach(kuchen -> {
            if (kuchen != null) {
                    if ( kuchen.getHersteller().getName().equalsIgnoreCase(herstellerName)){
                        try {
                            this.deleteKuchen(kuchen.getFachnummer());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
        this.kuchenCounter.remove(tempHersteller);
        herstellerFactory.deleteHersteller(herstellerName);
    }

    public List<VerkaufsKuchen> getFaecher() {
        List<VerkaufsKuchen> returnCopy = new ArrayList<>(this.faecher);
        return returnCopy;
    }

    public Integer getKuchenCounter(Hersteller hersteller) throws Exception {
        if(!(getHersteller().contains(hersteller))) {
            throw new Exception("Hersteller unbekannt");
        }
        if (kuchenCounter.get(hersteller) != null) {
            return kuchenCounter.get(hersteller);
        }  else {
            return 0;
        }
    }

    public Map<Hersteller, Integer>  getKuchenCounter() {
        Map<Hersteller, Integer> returnCopy = new HashMap<>(kuchenCounter);
        return returnCopy;
    }

    public Set<Allergen> getAllergene(boolean showVorhandeneAllergene) {
        HashSet<Allergen> tempSet = new HashSet<>();
        if (showVorhandeneAllergene) {
            tempSet.addAll(allergeneVorhanden.keySet());
            return tempSet;
        } else {
            Allergen[] allergene = Allergen.values();
            tempSet.addAll(Arrays.asList(allergene));
            for ( Allergen a : allergeneVorhanden.keySet()) {
                tempSet.remove(a);
            }
            return tempSet;
        }
    }

    public synchronized List<VerkaufsKuchen> getKuchen() {
        List<VerkaufsKuchen> tempList = new ArrayList<VerkaufsKuchen>();
        for (KuchenArt kuchenArt : KuchenArt.values()) {
            if (this.kuchenMap.get(kuchenArt) != null) {
                tempList.addAll(this.kuchenMap.get(kuchenArt));
            }
        }
        return tempList;
    }

    public List<VerkaufsKuchen> getKuchen(KuchenArt kuchenArt) {
        List<VerkaufsKuchen> returnCopy = new ArrayList<>(this.kuchenMap.get(kuchenArt));
        return returnCopy;
    }

    public synchronized VerkaufsKuchen createKuchen(KuchenArt kuchenArt, Hersteller hersteller, BigDecimal preis, int naehrwert, Allergen[] allergene, String[] extraData, Integer haltbarkeitInStunden) throws Exception {
        switch (kuchenArt) {
            case Kremkuchen:
                if(extraData.length != 1) {
                    throw new Exception("only one String in extraData allowed");
                }
                VerkaufsKuchen tempKuchen = new KremkuchenImpl(hersteller,extraData[0], allergene, preis, naehrwert, this,haltbarkeitInStunden);
                this.addKuchen(tempKuchen);
                return tempKuchen;
            case Obstkuchen:
                if(extraData.length != 1) {
                    throw new Exception("only one String in extraData allowed");
                }
                tempKuchen = new ObstkuchenImpl(hersteller,extraData[0], allergene, preis, naehrwert, this,haltbarkeitInStunden);
                this.addKuchen(tempKuchen);
                return tempKuchen;
            case Obsttorte:
                if(extraData.length != 2) {
                    throw new Exception("only one String in extraData allowed");
                }
                tempKuchen = new ObsttorteImpl(hersteller,extraData[0], extraData[1], allergene, preis, naehrwert, this, haltbarkeitInStunden);
                this.addKuchen(tempKuchen);
                return tempKuchen;
        }
        return null;
    }

    public synchronized VerkaufsKuchen createKuchen(KuchenArt kuchenArt, Hersteller hersteller, BigDecimal preis, int naehrwert, Allergen[] allergene, String[] extraData, Integer haltbarkeitInStunden, Date inspektionsDatum ) throws Exception {
        switch (kuchenArt) {
            case Kremkuchen:
                if(extraData.length != 1) {
                    throw new Exception("only one String in extraData allowed");
                }
                VerkaufsKuchen tempKuchen = new KremkuchenImpl(hersteller,extraData[0], allergene, preis, naehrwert, this,haltbarkeitInStunden);
                this.addKuchen(tempKuchen);
                return tempKuchen;
            case Obstkuchen:
                if(extraData.length != 1) {
                    throw new Exception("only one String in extraData allowed");
                }
                tempKuchen = new ObstkuchenImpl(hersteller,extraData[0], allergene, preis, naehrwert, this,haltbarkeitInStunden);
                this.addKuchen(tempKuchen, inspektionsDatum);
                return tempKuchen;
            case Obsttorte:
                if(extraData.length != 2) {
                    throw new Exception("only one String in extraData allowed");
                }
                tempKuchen = new ObsttorteImpl(hersteller,extraData[0], extraData[1], allergene, preis, naehrwert, this, haltbarkeitInStunden);
                this.addKuchen(tempKuchen, inspektionsDatum);
                return tempKuchen;
        }
        return null;
    }

    private synchronized void addKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (kuchen.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        } else if (this.getHersteller(kuchen.getHersteller().getName()) == null) {
            throw new Exception("Hersteller des Kuchens nicht existent");
        } else if (this.getKuchen().size() == fachAnzahl ) {
            throw new Exception("Alle Fächer voll");
        }
            for (int i = 0; i < this.faecher.size(); i++) {
                if (this.faecher.get(i) == null) {
                    this.faecher.set(i, kuchen);
                    this.kuchenSetup(kuchen);
                    return;
                }
            }
            this.faecher.add(kuchen);
            this.kuchenSetup(kuchen);
    }

    private void addKuchen(VerkaufsKuchen kuchen, Date inspektionsDatum) throws Exception {
        if (kuchen.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        } else if (this.getHersteller(kuchen.getHersteller().getName()) == null) {
            throw new Exception("Hersteller des Kuchens nicht existent");
        } else if (this.getKuchen().size() == fachAnzahl ) {
            throw new Exception("Alle Fächer voll");
        }
        for (int i = 0; i < this.faecher.size(); i++) {
            if (this.faecher.get(i) == null) {
                this.faecher.set(i, kuchen);
                this.kuchenSetup(kuchen,inspektionsDatum);
                return;
            }
        }
        this.faecher.add(kuchen);
        this.kuchenSetup(kuchen,inspektionsDatum);
    }

    private void kuchenSetup( VerkaufsKuchen kuchen) {
        this.inspektionsDaten.put(kuchen, new Date());
        incKuchenCounter(kuchen.getHersteller());
        addToKuchenMap(kuchen);
        kuchen.getAllergene().forEach((this::incAllergen));
    }
    private void kuchenSetup( VerkaufsKuchen kuchen, Date Inspektionsdatum) {
        this.inspektionsDaten.put(kuchen, Inspektionsdatum);
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

    public synchronized void deleteKuchen(int index) throws Exception {
        if (index >= this.fachAnzahl || index < 0){
            throw new Exception("Fachnummer ausserhalb der Fachanzahl, möglich: 0 - " + (fachAnzahl-1) + "aktueller index: " + index);
        } else if ( this.faecher.get(index) == null) {
            throw new Exception("Fach bereits leer");
        }
        VerkaufsKuchen tempKuchen = this.faecher.get(index);
        setUpDeleteKuchen(tempKuchen,index);
    }

    private void setUpDeleteKuchen(VerkaufsKuchen kuchen, Integer i) throws Exception {
        kuchen.getAllergene().forEach(allergen -> {
            try {
                decAllergen(allergen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        decKuchenCounter(kuchen.getHersteller());
        this.kuchenMap.get(kuchen.getKuchenArt()).remove(kuchen);
        this.inspektionsDaten.remove(kuchen);
        this.faecher.set(i, null);
    }

    public void deleteKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (this.kuchenMap.get(kuchen.getKuchenArt()) == null || !this.kuchenMap.get(kuchen.getKuchenArt()).contains(kuchen) ) {
            throw new Exception("Kuchen nicht im Automaten");
        }
        for (int i = 0; i < faecher.size() ; i++) {
            if ( faecher.get(i) != null && faecher.get(i) == kuchen) {
                setUpDeleteKuchen(kuchen,i);
            }
        }
    }

    public synchronized Date getInspektionsdatum(Verkaufsobjekt verkaufsobjekt) {
        Date returnCopy = new Date();
        returnCopy.setTime(inspektionsDaten.get((VerkaufsKuchen)verkaufsobjekt).getTime());
        return returnCopy;
    }

    public Integer getFachnummer(Verkaufsobjekt verkaufsobjekt) {
        return this.faecher.indexOf((VerkaufsKuchen) verkaufsobjekt);
    }

    public boolean swapFachnummer(int fach1, int fach2) throws Exception {
        if(this.faecher.get(fach1) == null | this.faecher.get(fach2) == null ){
            throw new Exception("Index zeigt auf leeres Fach");
        }
        Collections.swap(this.faecher, fach1,fach2);
        return false;
    }

    public void aktualisiereInspektionsdatum(Integer fachnummer) throws Exception {
        this.setInspektionsdatum(fachnummer, new Date());
    }

    private synchronized void setInspektionsdatum(Integer fachnummer, Date inspektionsdatumNeu) throws Exception {
        if(this.faecher.get(fachnummer) == null ) {
            throw new Exception("Index zeigt auf leeres Fach");
        }
        VerkaufsKuchen tempKuchen = this.faecher.get(fachnummer);
        this.inspektionsDaten.put(tempKuchen,inspektionsdatumNeu);
    }

    public int getFachanzahl() {
        return fachAnzahl;
    }

    public boolean isFull() {
        return (this.getKuchen().size() == fachAnzahl);
    }
    public boolean isEmpty(){
        return (this.getKuchen().size()== 0);
    }

    @Override
    public String toString() {
        return "Automat{" +
                "faecher=" + faecher +
                ", kuchenCounter=" + kuchenCounter +
                ", kuchenMap=" + kuchenMap +
                ", allergeneVorhanden=" + allergeneVorhanden +
                ", herstellerFactory=" + herstellerFactory +
                ", inspektionsDaten=" + inspektionsDaten +
                ", fachAnzahl=" + fachAnzahl +
                '}';
    }
}
