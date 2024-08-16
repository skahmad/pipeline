package forgesystem.workflow;
import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private final List<Task<?>> tasks;

    public Workflow() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task<?> task) {
        tasks.add(task);
    }

    public List<Task<?>> getTasks() {
        return tasks;
    }
}

