import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.*;
import control.console.input.InputEventHandler;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import view.gui.events.UpdateGuiEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import static lib.PersistLib.*;


public class MainPersistJOS {

    public static void main(String[] args) {

        String filename = "automat.ser";

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 6;
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


        writeAutomatToFileJOS("automat.ser", automat);
        Automat justReadAutomat = null;
        try {
            justReadAutomat = readAutomatFromFileJOS("automat.ser");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rehydrateAutomat(justReadAutomat, automatController);
        System.out.println(automatController);
    }
}
