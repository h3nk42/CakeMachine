import control.automat.events.*;
import model.Automat;
import control.automat.AutomatController;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.CreateDeleteCakeObserver;
import control.automat.observers.CreateDeleteHerstellerObserver;
import control.automat.observers.KuchenCapacityObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import control.gui.FeController;
import control.gui.event.UpdateGuiEventHandler;
import view.console.Printer;
import control.console.output.OutputEventHandler;
import control.console.output.OutputEventListener;
import control.console.output.OutputEventListenerPrint;

import java.util.Objects;

public class MainWithGUI extends Application {

    private static AutomatEventHandler automatEventHandler;
    private static UpdateGuiEventHandler updateGuiEventHandler;

    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 10;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        automatEventHandler = new AutomatEventHandler();
        updateGuiEventHandler = new UpdateGuiEventHandler();

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
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler, updateGuiEventHandler);
        CreateDeleteCakeObserver createDeleteCakeObserver = new CreateDeleteCakeObserver(automatController,outputEventHandler,updateGuiEventHandler);
        CreateDeleteHerstellerObserver createDeleteHerstellerObserver = new CreateDeleteHerstellerObserver(automatController,outputEventHandler,updateGuiEventHandler);

        /* ------- GUI SETUP ------- */
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws  Exception {
        FXMLLoader loader = new FXMLLoader( Objects.requireNonNull(getClass().getClassLoader().getResource("view/gui/kuchenFrontend.fxml")) );
        Parent root = loader.load();
        primaryStage.setTitle("Kuchenautomat");
        Scene scene = new Scene(root, 1100, 550);
        FeController feController = loader.getController();
        feController.setAutomatEventHandler(automatEventHandler);
        updateGuiEventHandler.add(feController);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

