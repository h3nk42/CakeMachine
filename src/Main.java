import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.console.Console;
import view.Reader;
import view.input.InputEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;

import view.output.OutputEventListenerPrint;

public class Main {
    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 3;

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

        /* ------- CONSOLE SETUP ------- */
        Console console = new Console(outputEventHandler, automatEventHandler);
        inputEventHandler.add(console);

        /* ------- READER SETUP & CLI START ------- */
        Reader reader = new Reader(inputEventHandler);
        reader.start();
    }
}

