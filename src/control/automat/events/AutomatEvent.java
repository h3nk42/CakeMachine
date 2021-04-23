package control.automat.events;

import control.console.ConsoleState;

import java.util.EventObject;
import java.util.Map;

public class AutomatEvent extends EventObject {
    private Map<DataType, Object> data;
    private ConsoleState consoleState;
    private OperationType operationType;

    public AutomatEvent(Object source, Map<DataType, Object> data, ConsoleState consoleState, OperationType operationType) {
        super(source);
        this.data = data;
        this.consoleState = consoleState;
        this.operationType = operationType;
    }

    public Map<DataType, Object> getData() {
        return this.data;
    }

    public ConsoleState getState() {
        return this.consoleState;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
