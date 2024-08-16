package forgesystem.workflow;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class WorkflowExecutor {

    private final boolean isAsync;

    public WorkflowExecutor(boolean isAsync) {
        this.isAsync = isAsync;
    }

    public List<Object> execute(Workflow workflow, TaskContext context) {
        List<Task<?>> tasks = workflow.getTasks();
        if (isAsync) {
            return executeAsync(tasks, context);
        } else {
            return executeSync(tasks, context);
        }
    }

    private List<Object> executeSync(List<Task<?>> tasks, TaskContext context) {
        return tasks.stream().map(task -> {
            try {
                return task.executeSync(context);
            } catch (Exception e) {
                handleException(task, e);
                return null; // Or handle errors differently
            }
        }).collect(Collectors.toList());
    }

    private List<Object> executeAsync(List<Task<?>> tasks, TaskContext context) {
        List<CompletableFuture<?>> futures = tasks.stream()
                .map(task -> task.executeAsync(context)
                        .exceptionally(e -> {
                            handleException(task, e);
                            return null; // Or handle errors differently
                        }))
                .collect(Collectors.toList());

        // Wait for all tasks to complete and collect results
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private void handleException(Task<?> task, Throwable e) {
        // Replace with a logging framework if needed
        System.err.println("Error executing task " + task.getClass().getSimpleName() + ": " + e.getMessage());
        // Optionally: log to a file or monitoring system, or rethrow exception
    }
}
