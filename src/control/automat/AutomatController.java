package control.automat;

import control.automat.events.AutomatEventHandler;
import control.automat.events.listener.AutomatEventListenerCreate;
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
        AutomatEventListenerCreate automatEventListenerCreate = new AutomatEventListenerCreate(automat, outputEventHandler);
        automatEventHandler.add(automatEventListenerCreate);
    }

}
