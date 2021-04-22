package control.automat;

import control.automat.hersteller.Hersteller;
import control.automat.hersteller.HerstellerFactory;
import control.automat.hersteller.HerstellerFactoryImpl;
import control.automat.verkaufsobjekte.Allergen;
import control.automat.verkaufsobjekte.Verkaufsobjekt;
import control.automat.verkaufsobjekte.kuchen.*;

import java.math.BigDecimal;
import java.util.*;


public class Automat {

    private List<VerkaufsKuchen> faecher;
    private Map<Hersteller, Integer> kuchenCounter;
    private Map<KuchenArt, ArrayList<VerkaufsKuchen>> kuchenMap;
    private Map<Allergen, Integer> allergeneVorhanden;
    private HerstellerFactory herstellerFactory;
    private Map<VerkaufsKuchen, Date> inspektionsDaten;
    private Integer fachAnzahl;

    public Automat(Integer fachAnzahl) {
        this.fachAnzahl = fachAnzahl;
        this.faecher = new ArrayList<>(fachAnzahl);
        this.kuchenCounter = new HashMap<>();
        this.kuchenMap = new HashMap<>();
        this.allergeneVorhanden = new HashMap<>();
        this.herstellerFactory = new HerstellerFactoryImpl();
        this.inspektionsDaten = new HashMap<>();
    }

    public Hersteller getHersteller(String herstellerName) {
        return herstellerFactory.getHerstellerListe().get(herstellerName.toLowerCase());
    }
    public Collection<Hersteller> getHersteller() {
        return herstellerFactory.getHerstellerListe().values();
    }

    public Hersteller createHersteller(String herstellerName ) throws Exception {
        return herstellerFactory.produceHersteller(herstellerName);
    }

    public void deleteHersteller(String herstellerName ) {
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
        herstellerFactory.deleteHersteller(herstellerName);
    }

    public List<VerkaufsKuchen> getFaecher() {
        return faecher;
    }

    public Integer getKuchenCounter(Hersteller hersteller) throws Exception {
        if(!(getHersteller().contains(hersteller))) {
            throw new Exception("Hersteller unbekannt");
        }
        return kuchenCounter.get(hersteller);
    }

    public Map<KuchenArt, ArrayList<VerkaufsKuchen>> getKuchenMap() {
        return kuchenMap;
    }

    public List<Allergen> getAllergene(boolean showVorhandeneAllergene) {
        ArrayList<Allergen> tempList = new ArrayList<>();
        if (showVorhandeneAllergene) {
            for ( Allergen a : allergeneVorhanden.keySet()) {
                tempList.add(a);
            }
            return tempList;
        } else {
            Allergen[] allergene = Allergen.values();
            for (int i = 0; i < allergene.length; i++) {
                tempList.add(allergene[i]);
            }
            for ( Allergen a : allergeneVorhanden.keySet()) {
                tempList.remove(a);
            }
            return tempList;
        }
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

    public VerkaufsKuchen createKuchen(KuchenArt kuchenArt, Hersteller hersteller, BigDecimal preis, int naehrwert, Allergen[] allergene, String[] extraData, Integer haltbarkeitInStunden ) throws Exception {
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

    private  void  addKuchen(VerkaufsKuchen kuchen) throws Exception {
        if (kuchen.getHersteller() == null) {
            throw new Exception("Kuchen benoetigt Hersteller");
        } else if (this.getHersteller(kuchen.getHersteller().getName()) == null) {
            throw new Exception("Hersteller des Kuchens nicht existent");
        } else if (this.getKuchen().size() == fachAnzahl ) {
            throw new Exception("Alle Fächer voll");
        }
        if (this.kuchenMap.get(kuchen.getKuchenArt()) != null && this.kuchenMap.get(kuchen.getKuchenArt()).contains(kuchen) ) {
            throw new Exception("Kuchen bereits im Automaten");
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

    private void kuchenSetup( VerkaufsKuchen kuchen) {
        this.inspektionsDaten.put(kuchen, new Date());
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

    public void deleteKuchen(int index) throws Exception {
        if (index >= this.faecher.size() || index < 0){
            throw new Exception("index out of bounds: " + index);
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

    public Date getInspektionsdatum(Verkaufsobjekt verkaufsobjekt) {
        return inspektionsDaten.get((VerkaufsKuchen)verkaufsobjekt);
    }

    public Integer getFachnummer(Verkaufsobjekt verkaufsobjekt) {
        return this.faecher.indexOf((VerkaufsKuchen) verkaufsobjekt);
    }

    public void setInspektionsdatum(Integer fachnummer, Date inspektionsdatumNeu) {
        VerkaufsKuchen tempKuchen = this.faecher.get(fachnummer);
        this.inspektionsDaten.put(tempKuchen,inspektionsdatumNeu);
    }
}