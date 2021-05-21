package control.automat.observers;

import control.automat.AutomatController;
import view.gui.events.UpdateGuiEventHandler;
import view.output.MessageType;
import view.output.Output;
import view.output.OutputEventHandler;

public class CreateDeleteCakeObserver implements Observer {

    private AutomatController automatController;
    private OutputEventHandler outputEventHandler;
    private UpdateGuiEventHandler updateGuiEventHandler;
    private Integer kuchenAnzahl = 0;

    public CreateDeleteCakeObserver(AutomatController automatController, OutputEventHandler outputEventHandler, UpdateGuiEventHandler updateGuiEventHandler){
        this.automatController = automatController;
        this.outputEventHandler = outputEventHandler;
        this.updateGuiEventHandler = updateGuiEventHandler;
        automatController.meldeAn(this);
        updateKuchenAnzahl();
    }

    @Override
    public void aktualisiere() {
        switch(updateKuchenAnzahl()) {
            case -1:
                Output.print(this, "Kuchen entfernt", MessageType.warning, outputEventHandler);
                break;
            case 0:
                //Output.print(this, "Kuchen gleich", MessageType.warning, outputEventHandler);
                break;
            case 1:
                Output.print(this, "Kuchen hinzugefügt", MessageType.warning, outputEventHandler);
                break;
        }
    }

    private int updateKuchenAnzahl() {
        int tempCount = automatController.getKuchen().size();
        if(kuchenAnzahl<tempCount) {
            setCount();
            return 1;
        } else if (kuchenAnzahl>tempCount) {
            setCount();
            return -1;
        }
        else {
            setCount();
            return 0;
        }
    }

    private void setCount() {
        this.kuchenAnzahl = automatController.getKuchen().size();
    }


}
