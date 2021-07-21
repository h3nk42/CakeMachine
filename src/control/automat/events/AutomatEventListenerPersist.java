package control.automat.events;

import control.automat.AutomatController;
import model.Automat;
import control.lib.PersistLib;
import control.console.output.OutputEventHandler;

import java.io.Serializable;

import static control.lib.PersistLib.writeAutomatToFileJOS;

public class AutomatEventListenerPersist implements AutomatEventListener, Serializable {

    private AutomatController automatC;
    private Automat automat;
    private OutputEventHandler outputEventHandler;

    public AutomatEventListenerPersist(OutputEventHandler outputEventHandler, AutomatController automatController) {
        this.automatC = automatController;
        this.automat = automatController.getAutomat();
        this.outputEventHandler = outputEventHandler;
    }

    @Override
    public void onAutomatEvent(AutomatEvent event) {
        automat = automatC.getAutomat();
        if (null != event.getData()) {
            switch(event.getOperationType()) {
                case pJOS:
                    handleSaveJos(event);
                    break;
                case lJOS:
                    handleLoadJos(event);
                    break;
            }
        }
    }

    private void handleSaveJos(AutomatEvent event) {
        writeAutomatToFileJOS("automat.ser", automat);
    }

    private void handleLoadJos(AutomatEvent event) {
        PersistLib.loadAutomatAndRehydrateController(automatC);
    }

}
