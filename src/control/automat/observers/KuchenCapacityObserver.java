package control.automat.observers;

import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

public class KuchenCapacityObserver implements Observer {

    private AutomatSubject automatSubject;
    private OutputEventHandler outputEventHandler;
    private Double lastCapacity;
    private boolean over90;

    public KuchenCapacityObserver(AutomatSubject automatSubject, OutputEventHandler outputEventHandler) {
        this.automatSubject = automatSubject;
        this.outputEventHandler = outputEventHandler;
        automatSubject.meldeAn(this);
        this.lastCapacity = Double.valueOf(0);
        this.over90 = false;
    }

    @Override
    public void aktualisiere() {
            if (automatSubject.getCapacity()!=lastCapacity) {
                checkCapacity();
                updateCapacity();
            }
    }

    private void checkCapacity() {
        if(automatSubject.getCapacity() > 0.9) {
            if(!over90) {
                Output.print(this, "Kapazität über 90%!", MessageType.warning, outputEventHandler);
                this.over90 = true;
            }
        } else if (automatSubject.getCapacity() <= 0.5) {
            this.over90 = false;
        }
    }

    private void updateCapacity() {
        lastCapacity = automatSubject.getCapacity();
    }
}
