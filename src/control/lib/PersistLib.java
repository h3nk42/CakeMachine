package control.lib;

import control.automat.Observer;
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

    public static void writeAutomatToFileJOS(String fileName, Automat automatToWrite, boolean isTest) {
        if(isTest) {
            automatToWrite.wasPersisted();
            return;
        }
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
            try {
                Automat automat = (Automat) in.readObject();
                in.close();
                return automat;
            } catch(InvalidClassException e) {

            }
            return null;
    }

    public static boolean loadAutomatAndRehydrateController(AutomatController automatController) {
        Automat justReadAutomat = null;
        try {
            justReadAutomat = readAutomatFromFileJOS("automat.ser");
            if(justReadAutomat == null) return false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rehydrateAutomat(justReadAutomat, automatController);
        return true;
    }
}
