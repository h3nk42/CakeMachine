package listenerTests;

import control.console.AutomatConsole;
import control.console.output.MessageType;
import control.console.output.OutputEventHandler;
import control.console.output.OutputEventListener;
import control.console.output.OutputEventListenerPrint;
import model.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.CakeDataType;
import control.automat.events.AutomatOperationType;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.KuchenCapacityObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import control.console.input.InputEvent;
import control.console.input.InputEventHandler;
import control.console.input.InputEventType;
import control.gui.event.UpdateGuiEventHandler;
import view.console.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class ListenerTest {

        private InputEventHandler inputEventHandler;
        private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private AutomatEventHandler automatEventHandler;

    @BeforeEach
        void setUp() throws Exception {

            /* ------- AUTOMAT SETTINGS ------- */
            final int FACHANZAHL = 6;

            /* ------- HANDLER SETUP ------- */
            outputEventHandler = new OutputEventHandler();
            automatEventHandler = new AutomatEventHandler();
            inputEventHandler = new InputEventHandler();
            updateGuiEventHandler = new UpdateGuiEventHandler();

            /* ------- OUTPUT SETUP ------- */
            Printer out = new Printer();
            OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
            outputEventHandler.add(outputEventListener, true);

            /* ------- AUTOMAT SETUP ------- */
        Automat automat = new Automat(FACHANZAHL);
        AutomatController automatController = new AutomatController(automat);

            /* ------- OBSERVER SETUP ------- */
            KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
            AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler, updateGuiEventHandler);

            /* ------- CONSOLE SETUP ------- */
            AutomatConsole console = new AutomatConsole(outputEventHandler, automatEventHandler);
            inputEventHandler.add(console);


            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));

        }

        /* OUTPUT LISTENER ---------------------------------------------------------------------------------------------------------  */
    @Test
    void testErrorMessage() {
        Printer.print(this,"error message", MessageType.error, outputEventHandler);
        Assertions.assertEquals("\u001B[31m "+ System.lineSeparator() +
                " --- error message --- "+ System.lineSeparator() +
                " \u001B[0m"+ System.lineSeparator(), outContent.toString());
    }

    @Test
    void testNormalMessage() {
        Printer.print(this,"normal message", MessageType.normal, outputEventHandler);
        Assertions.assertEquals("normal message"+ System.lineSeparator(), outContent.toString());
    }

    @Test
    void testSuccessMessage() {
        Printer.print(this,"success message", MessageType.success, outputEventHandler);
        Assertions.assertEquals("\u001B[36m "+ System.lineSeparator() +
                " --- success message --- "+ System.lineSeparator() +
                " \u001B[0m"+ System.lineSeparator(), outContent.toString());
    }

    @Test
    void testWarningMessage() {
        Printer.print(this,"warning message", MessageType.warning, outputEventHandler);
        Assertions.assertEquals("\u001B[33m "+ System.lineSeparator() +
                " --- warning message --- "+ System.lineSeparator() +
                " \u001B[0m"+ System.lineSeparator(), outContent.toString());
    }

    /* INPUT LISTENER ---------------------------------------------------------------------------------------------------------  */

    @Test
    void testInputListenerPrint() {
        InputEvent inputEvent = new InputEvent(this, "casdasdsad", InputEventType.print);
        inputEventHandler.handle(inputEvent);
        Assertions.assertEquals("\u001B[33mModus wählen:\u001B[0m "+ System.lineSeparator() +
                " c - Einfügen "+ System.lineSeparator() +
                " r - Anzeigen"+ System.lineSeparator() +
                " u - Ändern"+ System.lineSeparator() +
                " d - Löschen"+ System.lineSeparator() +
                " p - Speichern "+ System.lineSeparator() +
                " conf - Konfiguration "+ System.lineSeparator() +
                " exit - Programm beenden"+ System.lineSeparator(), outContent.toString());
    }

   @Test
    void testInputListenerRead() {
        InputEvent inputEventRead = new InputEvent(this, "c", InputEventType.read);
        inputEventHandler.handle(inputEventRead);
       InputEvent inputEventPrint = new InputEvent(this, "casdasdsad", InputEventType.print);
       inputEventHandler.handle(inputEventPrint);
        Assertions.assertEquals("\u001B[33mEinfügen: \u001B[0m "+ System.lineSeparator() +
                " h - Hersteller "+ System.lineSeparator() +
                " k - Kuchen "+ System.lineSeparator() +
                " b - Zurück "+ System.lineSeparator() +
                " exit - Programm beenden"+ System.lineSeparator(), outContent.toString());
    }

    /* AUTOMAT LISTENER ---------------------------------------------------------------------------------------------------------  */

    @Test
    void testCreateReadEvent() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);
        outContent.toString();
        AutomatEvent automatEventRead = new AutomatEvent(this, new HashMap<CakeDataType, Object>(), AutomatOperationType.rHersteller);
        automatEventHandler.handle(automatEventRead);
        Assertions.assertEquals("\u001B[36m "+ System.lineSeparator() +
                " --- erfolg --- "+ System.lineSeparator() +
                " \u001B[0m"+ System.lineSeparator() +
                "\u001B[36m "+ System.lineSeparator() +
                " --- Hersteller:["+ System.lineSeparator() +
                "{rewe, Kuchenanzahl: 0}, "+ System.lineSeparator() +
                "] --- "+ System.lineSeparator() +
                " \u001B[0m"+ System.lineSeparator() , outContent.toString());
    }

    @Test
    void testReadDeleteEvent() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);
        outContent.reset();
        AutomatEvent automatEventDelete = new AutomatEvent(this, tempMap, AutomatOperationType.dHersteller);
        automatEventHandler.handle(automatEventDelete);
        AutomatEvent automatEventRead = new AutomatEvent(this, new HashMap<CakeDataType, Object>(), AutomatOperationType.rHersteller);
        automatEventHandler.handle(automatEventRead);
        Assertions.assertEquals("\u001B[36m " +System.lineSeparator() +
                " --- erfolg --- " +System.lineSeparator() +
                " \u001B[0m" +System.lineSeparator() +
                "\u001B[31m " +System.lineSeparator() +
                " --- keine Hersteller gefunden --- " +System.lineSeparator() +
                " \u001B[0m" +System.lineSeparator() , outContent.toString());
    }
}
