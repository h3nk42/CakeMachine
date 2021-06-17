package lib;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.*;
import view.gui.events.UpdateGuiEventHandler;
import view.output.OutputEventHandler;

import java.io.*;
import java.util.ArrayList;

public class StaticHelperMethods {

    public static void rehydrateAutomat(AutomatController automatController, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler, ArrayList<Observer> observers) {
        automatController.rehydrate(automatEventHandler,outputEventHandler,updateGuiEventHandler);
        for (Observer observer:
                observers) {
            automatController.meldeAn(observer);
        }
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

    public static void writeAutomatToFileJOS(String fileName, AutomatController automatControllerToWrite) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(automatControllerToWrite);
            objectOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static AutomatController readAutomatFromFileJOS(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream in = null;
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            AutomatController automatController = (AutomatController) in.readObject();
            in.close();
            return automatController;
    }
}
