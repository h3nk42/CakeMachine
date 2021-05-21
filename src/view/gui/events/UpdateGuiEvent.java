package view.gui.events;

import control.automat.events.DataType;

import java.util.EventObject;
import java.util.Map;

public class UpdateGuiEvent extends EventObject {
    private Map<DataType, Object> data;
    private GuiEventType guiEventType;

    public UpdateGuiEvent(Object source, Map<DataType, Object> data, GuiEventType guiEventType) {
        super(source);
        this.data = data;
        this.guiEventType = guiEventType;
    }

    public Map<DataType, Object> getData() {
        return this.data;
    }

    public GuiEventType getGuiEventType() {
        return guiEventType;
    }
}
