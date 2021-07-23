import control.automat.events.*;
import model.Automat;
import control.automat.AutomatController;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.CreateDeleteCakeObserver;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.AutomatConsole;
import view.console.Reader;
import control.console.input.InputEventHandler;
import control.gui.event.UpdateGuiEventHandler;
import view.console.Printer;
import control.console.output.OutputEventHandler;
import control.console.output.OutputEventListener;

import control.console.output.OutputEventListenerPrint;


public class MainWithCLI {
    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 6;

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
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler,updateGuiEventHandler);
        CreateDeleteCakeObserver createDeleteCakeObserver = new CreateDeleteCakeObserver(automatController,outputEventHandler,updateGuiEventHandler);
        CreateDeleteHerstellerObserver createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(automatController,outputEventHandler,updateGuiEventHandler);

        /* ------- CONSOLE SETUP ------- */
        AutomatConsole console = new AutomatConsole(outputEventHandler, automatEventHandler);
        inputEventHandler.add(console);

        /*  ------- READER SETUP & CLI START ------- */
        Reader reader = new Reader(inputEventHandler, true);
        reader.start();
    }



}

