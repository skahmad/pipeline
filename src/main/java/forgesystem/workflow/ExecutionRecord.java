package forgesystem.workflow;


public class ExecutionRecord {
    private final String taskName;
    private final Object result;
    private final long timestamp;

    public ExecutionRecord(String taskName, Object result) {
        this.taskName = taskName;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }

    public String getTaskName() {
        return taskName;
    }

    public Object getResult() {
        return result;
    }

    public long getTimestamp() {
        return timestamp;
    }
}


