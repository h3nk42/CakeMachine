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
        if (null != event.getOperationType() && null != event.getData() ) {
            switch(event.getOperationType()) {
                case pJOS:
                    boolean isTest = false;
                    if(event.getData().get(CakeDataType.bool)!=null){
                        if ((boolean) event.getData().get(CakeDataType.bool) == true)
                            isTest = true;
                    }
                    handleSaveJos(isTest);
                    break;
                case lJOS:
                    handleLoadJos();
                    break;
            }
        }
    }

    private void handleSaveJos(boolean isTest) {
        writeAutomatToFileJOS("automat.ser", automat, isTest);
    }

    private boolean handleLoadJos() {
        if(!PersistLib.loadAutomatAndRehydrateController(automatC)) return false;
        return true;
    }

}
