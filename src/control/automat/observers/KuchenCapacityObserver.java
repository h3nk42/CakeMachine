package control.automat.observers;

import control.automat.AutomatController;
import control.automat.Observer;
import control.console.output.MessageType;
import control.console.output.OutputEvent;
import control.console.output.OutputEventHandler;

public class KuchenCapacityObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private Double lastCapacity;
    private boolean over90;

    public KuchenCapacityObserver(AutomatController automatController, OutputEventHandler outputEventHandler) {
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        automatController.meldeAn(this);
        this.lastCapacity = Double.valueOf(0);
        this.over90 = false;
    }

    @Override
    public void aktualisiere() {
            if (automatController.getCapacity()!=lastCapacity) {
                checkCapacity();
                updateCapacity();
            }
    }

    private void checkCapacity() {
        if(automatController.getCapacity() > 0.9) {
            if(!over90) {
                OutputEvent outputEvent = new OutputEvent(this, "Kapazität über 90%!", MessageType.warning);
                outputEventHandler.handle(outputEvent);
                this.over90 = true;
            }
        } else if (automatController.getCapacity() <= 0.9) {
            this.over90 = false;
        }
    }

    private void updateCapacity() {
        lastCapacity = automatController.getCapacity();
    }
}
