package forgesystem.workflow;

import java.util.HashMap;
import java.util.Map;

/**
 * Execution parameters
 * created by : ahmad hossain
 * created at : 27-10-2024 03:21 AM
 */
public final class TaskContext {
    private final Map<String, Object> parameters;

    public TaskContext(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public TaskContext() {
        this.parameters = new HashMap<>();
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }
}

