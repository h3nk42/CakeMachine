import control.automat.AutomatController;
import control.automat.events.AutomatEventHandler;
import control.automat.observers.AllergeneObserver;
import control.automat.observers.KuchenCapacityObserver;
import control.console.input.InputEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.gui.FeController;
import view.output.Output;
import view.output.OutputEventHandler;
import view.output.OutputEventListener;
import view.output.OutputEventListenerPrint;

import java.util.Objects;

public class MainWithGUI extends Application {

    private static AutomatEventHandler automatEventHandler;

    public static void main(String[] args) throws Exception {

        /* ------- AUTOMAT SETTINGS ------- */
        final int FACHANZAHL = 6;

        /* ------- HANDLER SETUP ------- */
        OutputEventHandler outputEventHandler = new OutputEventHandler();
        automatEventHandler = new AutomatEventHandler();
        /* ------- OUTPUT SETUP ------- */
        Output out = new Output();
        OutputEventListener outputEventListener = new OutputEventListenerPrint(out);
        outputEventHandler.add(outputEventListener, true);

        /* ------- AUTOMAT SETUP ------- */
        AutomatController automatController = new AutomatController(FACHANZAHL,automatEventHandler, outputEventHandler);

        /* ------- OBSERVER SETUP ------- */
        KuchenCapacityObserver kuchenCapacityObserver = new KuchenCapacityObserver(automatController, outputEventHandler);
        AllergeneObserver allergeneObserver = new AllergeneObserver(automatController, outputEventHandler);

        /* ------- GUI SETUP ------- */
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws  Exception {
        FXMLLoader loader = new FXMLLoader( Objects.requireNonNull(getClass().getClassLoader().getResource("view/gui/kuchenFrontend.fxml")) );
        Parent root = loader.load();
        primaryStage.setTitle("Kuchenautomat");
        Scene scene = new Scene(root, 800, 500);
        FeController feController = loader.getController();
        feController.setAutomatEventHandler(automatEventHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

