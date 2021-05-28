import Threads.AutomatSimWrapper;
import Threads.CreateCakeThread;
import Threads.DeleteCakeThread;
import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.CreateDeleteCakeObserver;
import view.gui.events.UpdateGuiEventHandler;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

public class Simulation1 {

    public static void main(String[] args) throws Exception {
        final Integer FACHANZAHL = 20000;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        AutomatEventHandler automatEventHandler = new AutomatEventHandler();
        UpdateGuiEventHandler updateGuiEventHandler = new UpdateGuiEventHandler();
        /* ------- OUTPUT SETUP ------- */
        Output out = new Output();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);
        /* ------- AUTOMAT SETUP ------- */
        AutomatController automatController = new AutomatController(FACHANZAHL,automatEventHandler, outputEventHandler,updateGuiEventHandler);
        AutomatSimWrapper automatSimWrapper = new AutomatSimWrapper(automatController,automatEventHandler);


        /* ------- OBSERVER SETUP ------- */
        //KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        //AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler);
        CreateDeleteCakeObserver anyChangeObserver = new CreateDeleteCakeObserver(automatController, outputEventHandler,updateGuiEventHandler);

        String[] herstellerArr = new String[]{"rewe","lidl","frodo"};
        for (int i = 0; i < herstellerArr.length; i++) {
            automatController.createHersteller(herstellerArr[i]);
        }

        int sleepValue = 0;
        CreateCakeThread ckt = new CreateCakeThread(automatSimWrapper, sleepValue);
        ckt.start();
        DeleteCakeThread dkt = new DeleteCakeThread(automatSimWrapper, automatController, sleepValue);
        dkt.start();
    }
}
