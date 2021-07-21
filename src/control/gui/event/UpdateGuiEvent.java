package control.gui.event;

import control.automat.events.CakeDataType;

import java.util.EventObject;
import java.util.Map;

public class UpdateGuiEvent extends EventObject {
    private Map<CakeDataType, Object> data;
    private GuiEventType guiEventType;

    public UpdateGuiEvent(Object source, Map<CakeDataType, Object> data, GuiEventType guiEventType) {
        super(source);
        this.data = data;
        this.guiEventType = guiEventType;
    }

    public Map<CakeDataType, Object> getData() {
        return this.data;
    }

    public GuiEventType getGuiEventType() {
        return guiEventType;
    }
}
