package forgesystem.workflow;

import java.util.ArrayList;
import java.util.List;

public class ExecutionHistory {
    private final List<ExecutionRecord> records;

    public ExecutionHistory() {
        this.records = new ArrayList<>();
    }

    public void addRecord(ExecutionRecord record) {
        records.add(record);
    }

    public List<ExecutionRecord> getRecords() {
        return records;
    }
}