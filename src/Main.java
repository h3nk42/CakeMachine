import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatEventListener;
import control.console.Console;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

public class Main {
    public static void main(String[] args) throws Exception {
        /*Automat automat = new Automat(5);
        AutomatConsole c = new AutomatConsole(automat);
        c.initiate();*/
        final int FACHANZAHL = 10;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();

        /* ------- OUTPUT SETUP ------- */
        Output out = new Output();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);

        /* ------- AUTOMAT SETUP ------- */
        AutomatController automatController = new AutomatController(FACHANZAHL,automatEventHandler, outputEventHandler);

        /* ------- CONSOLE SETUP ------- */
        Console console = new Console(outputEventHandler, automatEventHandler);
        console.initiateLoop();
    }
}

