package ConsoleTests;

import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.console.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.input.InputEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

public class ConsoleTests {

    private InputEventHandler inputEventHandler;

    @BeforeEach
    void setUp() throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 3;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();
        inputEventHandler = new InputEventHandler();

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
        //Reader reader = new Reader(inputEventHandler);
       // reader.start();
    }

    /* HERSTELLER ---------------------------------------------------------------------------------------------------------  */
    // HERSTELLER --- CREATE
    @Test
    void testConsole() throws Exception {



     /*   provideInput(testVal_bytes);
        userInput = new UserInput();
        int userInput2 = userInput.getUserInput();
        assertEquals(Integer.parseInt(testVal), userInput2);*/
    }
}
