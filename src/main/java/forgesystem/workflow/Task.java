package forgesystem.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Executable entity
 * created by : ahmad hossain
 * created at : 27-10-2024 03:06 AM
 */
public abstract class Task<T> implements Callable<TaskResult<T>> {
    protected TaskContext context;
    private final UUID id;
    List<ExecutionHook> beforeHooks = new ArrayList<>();
    List<ExecutionHook> afterHooks = new ArrayList<>();

    public Task() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    @Override
    public TaskResult<T> call() {
        TaskResult<T> result = new TaskResult<>();
        try {
            result.setTask(this);

            // execute before hooks
            beforeHooks.forEach(h-> h.on(context));

            result.setStatus(TaskExecutionStatus.RUNNING);

            // execute process
            T taskResult = process();

            result.setStatus(TaskExecutionStatus.FINISHED);

            result.setResult(taskResult);

            // execute after hooks
            afterHooks.forEach(h-> h.on(context));

            return result;
        } catch (Exception e) {
            result.setError(e.getMessage());
            result.setStatus(TaskExecutionStatus.FAILED);
            return result;
        }
    }

    // Abstract method for subclasses to implement specific logic
    protected abstract T process() throws Exception;

    public void addBeforeHook(ExecutionHook beforeEach) {
        if (beforeEach != null) {
            beforeHooks.add(beforeEach);
        }
    }

    public void addAfterHook(ExecutionHook afterEach) {
        if (afterEach != null) {
            afterHooks.add(afterEach);
        }
    }
}