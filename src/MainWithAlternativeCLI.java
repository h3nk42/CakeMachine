import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.*;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.CreateDeleteCakeObserver;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.AutomatConsoleNew;
import control.console.input.InputEventHandler;
import control.console.input.Reader;
import view.gui.events.UpdateGuiEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;


public class MainWithAlternativeCLI {
    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 6;

        /* ------- EVENT_HANDLER SETUP ------- */
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

        /* EVENT_LISTENER SETUP */
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(outputEventHandler, automatController);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(outputEventHandler, automatController);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(outputEventHandler,automatController);
        AutomatEventListenerUpdate automatEventListenerUpdate = new AutomatEventListenerUpdate(outputEventHandler, updateGuiEventHandler, automatController);
        AutomatEventListenerPersist automatEventListenerPersist = new AutomatEventListenerPersist(outputEventHandler, automatController);
        automatEventHandler.add(automatEventListenerRead);
        automatEventHandler.add(automatEventListenerCreate);
        //automatEventHandler.add(automatEventListenerDelete);
        automatEventHandler.add(automatEventListenerUpdate);
        automatEventHandler.add(automatEventListenerPersist);

        /* ------- OBSERVER SETUP ------- */
        KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler,updateGuiEventHandler);
        CreateDeleteCakeObserver createDeleteCakeObserver = new CreateDeleteCakeObserver(automatController,outputEventHandler,updateGuiEventHandler);
        CreateDeleteHerstellerObserver createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(automatController,outputEventHandler,updateGuiEventHandler);

        /* ------- CONSOLE SETUP ------- */
        AutomatConsoleNew console = new AutomatConsoleNew(outputEventHandler, automatEventHandler);
        inputEventHandler.add(console);

        /*  ------- READER SETUP & CLI START ------- */
        Reader reader = new Reader(inputEventHandler);
        reader.start();
    }



}

