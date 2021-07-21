import Simulations.CreateThread;
import Simulations.DeleteThread;
import Simulations.InspectThread;
import Simulations.LockWrapper;
import Simulations.SimulationType;
import control.automat.events.*;
import model.Automat;
import control.automat.AutomatController;
import control.automat.observers.CreateDeleteCakeObserver;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.input.InputEventHandler;
import control.gui.event.UpdateGuiEventHandler;
import view.console.Printer;
import control.console.output.OutputEventHandler;
import control.console.output.OutputEventListener;
import control.console.output.OutputEventListenerPrint;

import java.util.Random;

public class Simulation2 {

    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 100;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();
        InputEventHandler inputEventHandler = new InputEventHandler();
        UpdateGuiEventHandler updateGuiEventHandler = new UpdateGuiEventHandler();

        /* ------- OUTPUT SETUP ------- */
        Printer out = new Printer();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);

        /* ------- AUTOMAT SETUP ------- */
        Automat automat = new Automat(FACHANZAHL);
        AutomatController automatController = new AutomatController(automat);

        /* LISTENER SETUP */
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(outputEventHandler, automatController);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandler, automatController);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(outputEventHandler,automatController);
        AutomatEventListenerUpdate automatEventListenerUpdate = new AutomatEventListenerUpdate(outputEventHandler, updateGuiEventHandler, automatController);
        AutomatEventListenerPersist automatEventListenerPersist = new AutomatEventListenerPersist(outputEventHandler, automatController);
        automatEventHandler.add(automatEventListenerRead);
        automatEventHandler.add(automatEventListenerCreate);
        automatEventHandler.add(automatEventListenerDelete);
        automatEventHandler.add(automatEventListenerUpdate);
        automatEventHandler.add(automatEventListenerPersist);

        /* ------- OBSERVER SETUP ------- */
        KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        //AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler,updateGuiEventHandler);
        CreateDeleteCakeObserver createDeleteCakeObserver = new CreateDeleteCakeObserver(automatController,outputEventHandler,updateGuiEventHandler);
        CreateDeleteHerstellerObserver createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(automatController,outputEventHandler,updateGuiEventHandler);

        String[] herstellerArr = new String[]{"rewe","lidl","frodo"};
        for (int i = 0; i < herstellerArr.length; i++) {
            automat.createHersteller(herstellerArr[i]);
        }

        Random r = new Random();
        LockWrapper lockWrapper = new LockWrapper(automatController,automatEventHandler,r);

        CreateThread ckt = new CreateThread(lockWrapper, SimulationType.sim2);
        ckt.start();
        DeleteThread dkt = new DeleteThread(lockWrapper, SimulationType.sim2);
        dkt.start();
        InspectThread inspectThread = new InspectThread(lockWrapper, SimulationType.sim2);
        inspectThread.start();
    }
}

