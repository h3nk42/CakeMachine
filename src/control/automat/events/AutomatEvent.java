package control.automat.events;

import java.util.EventObject;
import java.util.Map;

public class AutomatEvent extends EventObject {
    private Map<CakeDataType, Object> data;
    private AutomatOperationType automatOperationType;

    public AutomatEvent(Object source, Map<CakeDataType, Object> data, AutomatOperationType automatOperationType) {
        super(source);
        this.data = data;
        this.automatOperationType = automatOperationType;
    }

    public Map<CakeDataType, Object> getData() {
        return this.data;
    }

    public AutomatOperationType getOperationType() {
        return automatOperationType;
    }
}
