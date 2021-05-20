import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.AutomatConsole;
import control.console.input.Reader;
import control.console.input.InputEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;

import view.output.OutputEventListenerPrint;

import java.util.Random;


public class MainWithCLI {
    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 6;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();
        InputEventHandler inputEventHandler = new InputEventHandler();

        /* ------- OUTPUT SETUP ------- */
        Output out = new Output();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);

        /* ------- AUTOMAT SETUP ------- */
        AutomatController automatController = new AutomatController(FACHANZAHL,automatEventHandler, outputEventHandler);

        /* ------- OBSERVER SETUP ------- */
        KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler);

        /* ------- CONSOLE SETUP ------- */
        AutomatConsole console = new AutomatConsole(outputEventHandler, automatEventHandler);
        inputEventHandler.add(console);

        /*  ------- READER SETUP & CLI START ------- */
        Reader reader = new Reader(inputEventHandler);
        reader.start();
    }



}

