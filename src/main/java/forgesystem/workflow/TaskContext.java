package forgesystem.workflow;

import java.util.Map;

public class TaskContext {
    private final Map<String, Object> parameters;

    public TaskContext(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }
}

