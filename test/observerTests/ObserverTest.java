package observerTests;

import control.console.AutomatConsole;
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
import model.verkaufsobjekte.Allergen;
import model.verkaufsobjekte.kuchen.KuchenArt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import control.console.input.InputEventHandler;
import control.gui.event.UpdateGuiEventHandler;
import view.console.*;

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
            Printer out = new Printer();
            OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
            outputEventHandler.add(outputEventListener, true);

            /* ------- AUTOMAT SETUP ------- */

        Automat automat = new Automat(FACHANZAHL);
        AutomatController automatController = new AutomatController(automat);

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

   /* @Test
    void testCapacityObserver() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(CakeDataType.hersteller, "rewe");
        tempMap.put(CakeDataType.preis, BigDecimal.valueOf(4));
        tempMap.put(CakeDataType.naehrwert, 300);
        tempMap.put(CakeDataType.haltbarkeit, 24);
        tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(CakeDataType.kremsorte, "vanille");
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
*/
    /* ALLERGEN OBSERVER ---------------------------------------------------------------------------------------------------------  */

  /*  @Test
    void testAllergenObserverAddCake() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(CakeDataType.hersteller, "rewe");
        tempMap.put(CakeDataType.preis, BigDecimal.valueOf(4));
        tempMap.put(CakeDataType.naehrwert, 300);
        tempMap.put(CakeDataType.haltbarkeit, 24);
        tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(CakeDataType.kremsorte, "vanille");
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
*/
  /*  @Test
    void testAllergenObserverAddDifferentCake() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(CakeDataType.hersteller, "rewe");
        tempMap.put(CakeDataType.preis, BigDecimal.valueOf(4));
        tempMap.put(CakeDataType.naehrwert, 300);
        tempMap.put(CakeDataType.haltbarkeit, 24);
        tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(CakeDataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        automatEventHandler.handle(automatEvent);
        tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Haselnuss});
        outContent.reset();
        automatEventHandler.handle(automatEvent);
        Assertions.assertEquals("\u001B[36m " +System.lineSeparator()  +
                " --- erfolg --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  +
                "\u001B[33m " +System.lineSeparator()  +
                " --- Allergene haben sich verändert! --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  , outContent.toString());
    }
*/
   /* @Test
    void testAllergenObserverAddSameCakeTwice() {
        HashMap tempMap = new HashMap<CakeDataType, Object>();
        tempMap.put(CakeDataType.hersteller, "rewe");
        AutomatEvent automatEventCreate = new AutomatEvent(this, tempMap, AutomatOperationType.cHersteller);
        automatEventHandler.handle(automatEventCreate);

        tempMap.put(CakeDataType.kuchenart, KuchenArt.Kremkuchen);
        tempMap.put(CakeDataType.hersteller, "rewe");
        tempMap.put(CakeDataType.preis, BigDecimal.valueOf(4));
        tempMap.put(CakeDataType.naehrwert, 300);
        tempMap.put(CakeDataType.haltbarkeit, 24);
        tempMap.put(CakeDataType.allergene, new Allergen[]{Allergen.Gluten,Allergen.Gluten});
        tempMap.put(CakeDataType.kremsorte, "vanille");
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap,  AutomatOperationType.cKuchen);
        automatEventHandler.handle(automatEvent);
        outContent.reset();
        automatEventHandler.handle(automatEvent);
        Assertions.assertEquals("\u001B[36m " +System.lineSeparator()  +
                " --- erfolg --- " +System.lineSeparator()  +
                " \u001B[0m" +System.lineSeparator()  , outContent.toString());
    }*/

}
