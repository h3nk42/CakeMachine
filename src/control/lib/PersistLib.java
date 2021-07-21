package control.lib;

import model.Automat;
import control.automat.AutomatController;
import control.automat.observers.*;
import control.gui.event.UpdateGuiEventHandler;
import control.console.output.OutputEventHandler;

import java.io.*;
import java.util.ArrayList;

public class PersistLib {

    public static void rehydrateAutomat(Automat automat, AutomatController automatController) {
        automatController.rehydrate(automat);
        /*for (Observer observer:
                observers) {
            automatController.meldeAn(observer);
        }*/
    }

    public static ArrayList<Observer> setupObservers(AutomatController automatController, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler) {
        KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler,updateGuiEventHandler);
        CreateDeleteCakeObserver createDeleteCakeObserver = new CreateDeleteCakeObserver(automatController,outputEventHandler,updateGuiEventHandler);
        CreateDeleteHerstellerObserver createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(automatController,outputEventHandler,updateGuiEventHandler);

        ArrayList<Observer> observers = new ArrayList<>();
        observers.add(kuchenCapacityObserver);
        observers.add(allergeneObserver);
        observers.add(createDeleteCakeObserver);
        observers.add(createDeleteHerstellerObserver);
        return observers;
    }

    public static void writeAutomatToFileJOS(String fileName, Automat automatToWrite) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(automatToWrite);
            objectOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Automat readAutomatFromFileJOS(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream in = null;
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
        Automat automat = (Automat) in.readObject();
            in.close();
            return automat;
    }

    public static boolean loadAutomatAndRehydrateController(AutomatController automatController) {
        Automat justReadAutomat = null;
        try {
            justReadAutomat = readAutomatFromFileJOS("automat.ser");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rehydrateAutomat(justReadAutomat, automatController);
        return true;
    }
}
