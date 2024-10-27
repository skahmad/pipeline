package forgesystem.workflow;

import forgesystem.util.TimeConverter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class TaskResult<T> {
    private T result;
    private TaskExecutionStatus status;
    private String error;
    private LocalDateTime timestamp;
    private long s =0 ,e = 0;
    private Task<T> task;

    public TaskResult() {
        status = TaskExecutionStatus.NOT_STARTED;
        this.timestamp = LocalDateTime.now();
    }

    public Task<T> getTask() {
        return task;
    }

    void setTask(Task<T> task) {
        this.task = task;
    }

    void setStatus(TaskExecutionStatus status) {
        this.status = status;
        if (status == TaskExecutionStatus.RUNNING) {
            s = System.currentTimeMillis();
        }

        if (status == TaskExecutionStatus.FAILED || status == TaskExecutionStatus.FINISHED) {
            e = System.currentTimeMillis();
        }
    }

    void setResult(T result) {
        this.result = result;
    }

    void setError(String error) {
        this.error = error;
    }

    // Getters
    public Optional<T> getResult() {
        return Optional.ofNullable(result);
    }

    public String getStatus() {
        return status.name();
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTimeTaken() {
        return TimeConverter.convertMillisToReadableFormat((e-s));
    }
}
