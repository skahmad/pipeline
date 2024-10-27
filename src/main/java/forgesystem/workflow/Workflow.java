package forgesystem.workflow;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of execution entities( Task {@link Task})
 * created by : ahmad hossain
 * created at : 27-10-2024 03:35 AM
 */
public final class Workflow {
    private final List<Task<?>> tasks;
    private final boolean isAsync;

    ExecutionHook beforeEach, afterEach;

    public Workflow() {
        this.tasks = new ArrayList<>();
        this.isAsync = false;
    }

    public Workflow(boolean isAsync) {
        this.isAsync = isAsync;
        this.tasks = new ArrayList<>();
    }

    public void setBeforeEach(ExecutionHook beforeEach) {
        this.beforeEach = beforeEach;
    }

    public void setAfterEach(ExecutionHook afterEach) {
        this.afterEach = afterEach;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void addTask(Task<?> task) {
        tasks.add(task);
    }

    public List<Task<?>> getTasks() {
        return tasks;
    }
}

