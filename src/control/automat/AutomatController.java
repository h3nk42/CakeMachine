package control.automat;

import control.automat.Automat;
import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.AutomatEventListenerCreate;
import control.automat.events.listener.AutomatEventListenerDelete;
import control.automat.events.listener.AutomatEventListenerRead;
import view.output.OutputEventHandler;

public class AutomatController extends Automat {

    private final Automat automat;
    private AutomatEventHandler automatEventHandler;
    private OutputEventHandler outputEventHandler;


    public AutomatController(Integer fachAnzahl, AutomatEventHandler automatEventHandler, OutputEventHandler outputEventHandler) {
        super(fachAnzahl);
        this.automat = new Automat(fachAnzahl);
        this.automatEventHandler = automatEventHandler;
        this.outputEventHandler = outputEventHandler;
        AutomatEventListenerRead automatEventListenerRead = new AutomatEventListenerRead(automat, outputEventHandler);
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(automat, outputEventHandler);
        AutomatEventListenerDelete automatEventListenerDelete = new AutomatEventListenerDelete(automat, outputEventHandler);
        automatEventHandler.add(automatEventListenerCreate);
        automatEventHandler.add(automatEventListenerRead);
        automatEventHandler.add(automatEventListenerDelete);

    }

}
