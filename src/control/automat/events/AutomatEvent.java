package control.automat.events;

import control.console.ConsoleState;

import java.util.EventObject;
import java.util.Map;

public class AutomatEvent extends EventObject {
    private Map<DataType, Object> data;
    private OperationType operationType;

    public AutomatEvent(Object source, Map<DataType, Object> data, OperationType operationType) {
        super(source);
        this.data = data;
        this.operationType = operationType;
    }

    public Map<DataType, Object> getData() {
        return this.data;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
