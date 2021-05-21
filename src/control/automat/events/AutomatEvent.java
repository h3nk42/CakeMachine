package control.automat.events;

import java.util.EventObject;
import java.util.Map;

public class AutomatEvent extends EventObject {
    private Map<DataType, Object> data;
    private AutomatOperationType automatOperationType;

    public AutomatEvent(Object source, Map<DataType, Object> data, AutomatOperationType automatOperationType) {
        super(source);
        this.data = data;
        this.automatOperationType = automatOperationType;
    }

    public Map<DataType, Object> getData() {
        return this.data;
    }

    public AutomatOperationType getOperationType() {
        return automatOperationType;
    }
}
