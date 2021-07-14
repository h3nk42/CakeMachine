import lib.beans.AutomatBean;
import lib.beans.Bean;
import lib.beans.HerstellerBean;
import lib.beans.KuchenBean;
import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.Observer;
import control.console.input.InputEventHandler;
import model.automat.hersteller.Hersteller;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.*;
import view.gui.events.UpdateGuiEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import static lib.PersistLib.rehydrateAutomat;
import static lib.PersistLib.setupObservers;

public class MainPersistJBP {

    public static void main(String[] args) {
        String filename = "automat.xml";

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 20;
        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();
        InputEventHandler inputEventHandler = new InputEventHandler();
        UpdateGuiEventHandler updateGuiEventHandler = new UpdateGuiEventHandler();

        /* ------- OUTPUT SETUP ------- */
        Output out = new Output();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);

        /* ------- AUTOMAT SETUP ------- */
        Automat automat = new Automat(FACHANZAHL);
        AutomatController automatController = new AutomatController(automat,automatEventHandler, outputEventHandler, updateGuiEventHandler);

        /* ------- OBSERVER SETUP ------- */
        ArrayList<Observer> observers = setupObservers(automatController, outputEventHandler, updateGuiEventHandler);


        try {
            Automat a = automatController.getAutomat();
            a.createHersteller("test");
            a.createKuchen(KuchenArt.Obsttorte, a.getHersteller("test"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
            a.createKuchen(KuchenArt.Obsttorte, a.getHersteller("test"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
            a.createKuchen(KuchenArt.Obsttorte, a.getHersteller("test"), BigDecimal.valueOf(1.25), 300, new Allergen[] {Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen}, new String[] {"Apfel","Vanille"},24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("MainPersistJBP.java: lineNumber: 66: " + automatController);

        /* PERSIST */



        /* AUTOMAT */
        ArrayList<Bean> automatBeans = new ArrayList<>();
        automatBeans.add(new AutomatBean(automat.getFachanzahl()));


        /* HERSTELLER */
        ArrayList<Bean> herstellerBeans = new ArrayList<>();
        for (Hersteller h: automatController.getAutomat().getHersteller()
             ) {
            herstellerBeans.add(new HerstellerBean(h.getName()));
        }

        /* KUCHEN */
        ArrayList<Bean> kuchenBeans = new ArrayList<>();
        for (VerkaufsKuchen k: automatController.getAutomat().getKuchen()
             ) {
            Allergen[] allergene = new Allergen[k.getAllergene().size()];
            for (int i = 0; i < k.getAllergene().size(); i++) {
                allergene[i] = k.getAllergene().get(i);
            }
            switch (k.getKuchenArt()) {
                case Obstkuchen:
                    Obstkuchen o = (Obstkuchen) k;
                    kuchenBeans.add(new KuchenBean(k.getKuchenArt(), k.getHersteller().getName(), k.getPreis().doubleValue(), k.getNaehrwert(), allergene, new String[]{o.getObstsorte()}, k.getHaltbarkeit(), k.getInspektionsdatum()));
                    break;
                case Kremkuchen:
                    Kremkuchen kr = (Kremkuchen) k;
                    kuchenBeans.add(new KuchenBean(k.getKuchenArt(), k.getHersteller().getName(), k.getPreis().doubleValue(), k.getNaehrwert(), allergene, new String[]{kr.getKremsorte()}, k.getHaltbarkeit(), k.getInspektionsdatum()));
                    break;
                default:
                    Obsttorte kkk = (Obsttorte) k;
                    kuchenBeans.add(new KuchenBean(k.getKuchenArt(), k.getHersteller().getName(), k.getPreis().doubleValue(), k.getNaehrwert(), allergene, new String[]{kkk.getObstsorte(), kkk.getKremsorte()}, k.getHaltbarkeit(), k.getInspektionsdatum()));
                    break;
            }
        }
        ArrayList<Bean>[] objectToWrite = new ArrayList[]{automatBeans, herstellerBeans, kuchenBeans};

        try(XMLEncoder encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("beanItems.xml")));){
            encoder.writeObject(objectToWrite);
        }catch(Exception e){
            e.printStackTrace();
        }

        try (XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("beanItems.xml")));){
            ArrayList<Bean>[] loadedData=(ArrayList<Bean>[])decoder.readObject();
            AutomatBean ab = (AutomatBean) loadedData[0].get(0);
            Automat newAutomat = new Automat(ab.getFachAnzahl());
            for (Bean h: loadedData[1]
            ) {
                HerstellerBean hb = (HerstellerBean) h;
                newAutomat.createHersteller(hb.getName());
            }
            for (Bean h: loadedData[2]
            ) {
                KuchenBean kb = (KuchenBean) h;
                newAutomat.createKuchen(kb.getKuchenArt(), newAutomat.getHersteller(kb.getHersteller()), BigDecimal.valueOf(kb.getPreis()), kb.getNaehrwert(), kb.getAllergene(), kb.getExtraData(), kb.getHaltbarkeit().intValue(), kb.getInspektionsDatum() );
            }
            rehydrateAutomat(newAutomat, automatController);
            System.out.println("MainPersistJBP.java: lineNumber: 129: " + automatController);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
