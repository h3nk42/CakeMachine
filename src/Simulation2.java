import Threads.*;
import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.*;
import control.automat.observers.CreateDeleteCakeObserver;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.input.InputEventHandler;
import view.gui.events.UpdateGuiEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

public class Simulation2 {

    public static void main(String[] args) throws Exception {

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
        AutomatController automatController = new AutomatController(automat, automatEventHandler, outputEventHandler, updateGuiEventHandler);
        AutomatSimWrapper automatSimWrapper = new AutomatSimWrapper(automatController,automatEventHandler);

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

        int sleepValue = 0;
        CreateCakeThread cct = new CreateCakeThread(automatSimWrapper, sleepValue, automatController, automatEventHandler, false, SimulationType.sim2);
        cct.start();
        DeleteCakeThread dct = new DeleteCakeThread(automatSimWrapper, automatController, automatEventHandler, sleepValue, false, SimulationType.sim2);
        dct.start();
        InspectCakeThread ict = new InspectCakeThread(automatSimWrapper, automatController, automatEventHandler, sleepValue, SimulationType.sim2);
        ict.start();
    }
}
