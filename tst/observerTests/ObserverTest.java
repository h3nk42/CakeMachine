package observerTests;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.AutomatOperationType;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.AutomatConsole;
import model.automat.verkaufsobjekte.Allergen;
import model.automat.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import control.console.input.InputEventHandler;
import view.gui.events.UpdateGuiEventHandler;
import view.output.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;

public class ObserverTest {

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
            final int FACHANZAHL = 10;

            /* ------- HANDLER SETUP ------- */
            outputEventHandler = new OutputEventHandler();
            automatEventHandler = new AutomatEventHandler();
            inputEventHandler = new InputEventHandler();
             updateGuiEventHandler = new UpdateGuiEventHandler();

            /* ------- OUTPUT SETUP ------- */
            Output out = new Output();
            OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
            outputEventHandler.add(outputEventListener, true);

            /* ------- AUTOMAT SETUP ------- */

        Automat automat = new Automat(FACHANZAHL);
        AutomatController automatController = new AutomatController(automat,automatEventHandler, outputEventHandler, updateGuiEventHandler);

            /* ------- OBSERVER SETUP ------- */
            KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
            AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler,updateGuiEventHandler);

            /* ------- CONSOLE SETUP ------- */
            AutomatConsole console = new AutomatConsole(outputEventHandler, automatEventHandler);
            inputEventHandler.add(console);


            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));

        }


    /* CAPACITY OBSERVER ---------------------------------------------------------------------------------------------------------  */

    @Test
    void testCapacityObserver() {
        HashMap tempMap = new HashMap<DataType, Object>();
        tempMap.put(DataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(DataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(DataType.hersteller, "rewe");
        tempMap.put(DataType.preis, BigDecimal.valueOf(4));
        tempMap.put(DataType.naehrwert, 300);
        tempMap.put(DataType.haltbarkeit, 24);
        tempMap.put(DataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(DataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        for (int i = 0; i < 10; i++) {
            outContent.reset();
            automatEventHandler.handle(automatEvent);
        }
        Assertions.assertEquals("\u001B[36m "+System.lineSeparator() +
                " --- erfolg --- "+System.lineSeparator() +
                " \u001B[0m"+System.lineSeparator() +
                "\u001B[33m "+System.lineSeparator() +
                " --- Kapazität über 90%! --- "+System.lineSeparator() +
                " \u001B[0m" + System.lineSeparator() , outContent.toString());
    }

    /* ALLERGEN OBSERVER ---------------------------------------------------------------------------------------------------------  */

    @Test
    void testAllergenObserverAddCake() {
        HashMap tempMap = new HashMap<DataType, Object>();
        tempMap.put(DataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(DataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(DataType.hersteller, "rewe");
        tempMap.put(DataType.preis, BigDecimal.valueOf(4));
        tempMap.put(DataType.naehrwert, 300);
        tempMap.put(DataType.haltbarkeit, 24);
        tempMap.put(DataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(DataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        outContent.reset();
        automatEventHandler.handle(automatEvent);

        Assertions.assertEquals("\u001B[36m " +System.lineSeparator()  +
                " --- erfolg --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  +
                "\u001B[33m " +System.lineSeparator()  +
                " --- Allergene haben sich verändert! --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  , outContent.toString());
    }

    @Test
    void testAllergenObserverAddDifferentCake() {
        HashMap tempMap = new HashMap<DataType, Object>();
        tempMap.put(DataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(DataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(DataType.hersteller, "rewe");
        tempMap.put(DataType.preis, BigDecimal.valueOf(4));
        tempMap.put(DataType.naehrwert, 300);
        tempMap.put(DataType.haltbarkeit, 24);
        tempMap.put(DataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(DataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        automatEventHandler.handle(automatEvent);
        tempMap.put(DataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Haselnuss});
        outContent.reset();
        automatEventHandler.handle(automatEvent);
        Assertions.assertEquals("\u001B[36m " +System.lineSeparator()  +
                " --- erfolg --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  +
                "\u001B[33m " +System.lineSeparator()  +
                " --- Allergene haben sich verändert! --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  , outContent.toString());
    }

    @Test
    void testAllergenObserverAddSameCakeTwice() {
        HashMap tempMap = new HashMap<DataType, Object>();
        tempMap.put(DataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(DataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(DataType.hersteller, "rewe");
        tempMap.put(DataType.preis, BigDecimal.valueOf(4));
        tempMap.put(DataType.naehrwert, 300);
        tempMap.put(DataType.haltbarkeit, 24);
        tempMap.put(DataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(DataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        automatEventHandler.handle(automatEvent);
        outContent.reset();
        automatEventHandler.handle(automatEvent);
        Assertions.assertEquals("\u001B[36m " +System.lineSeparator()  +
                " --- erfolg --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  , outContent.toString());
    }

}
