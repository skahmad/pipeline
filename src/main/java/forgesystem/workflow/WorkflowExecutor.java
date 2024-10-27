package forgesystem.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Execute workflow
 * created by : ahmad hossain
 * created at : 27-10-2024 03:35 AM
 */
public final class WorkflowExecutor {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final boolean isAsync;
    List<TaskResult<?>> results = new ArrayList<>();
    private List<Future<?>> futureTasks = new ArrayList<>();

    public WorkflowExecutor(boolean isAsync) {
        this.isAsync = isAsync;
    }

    private void asyncExecute(Workflow workflow, TaskContext context) {
        for (Task<?> task : workflow.getTasks()) {
            task.setContext(context);
            task.addBeforeHook(workflow.beforeEach);
            task.addAfterHook(workflow.afterEach);
            Future<?> future = executorService.submit(task);
            futureTasks.add(future);
        }
    }
    private void syncExecute(Workflow workflow, TaskContext context) {
        for (Task<?> task : workflow.getTasks()) {
            task.setContext(context);
            task.addBeforeHook(workflow.beforeEach);
            task.addAfterHook(workflow.afterEach);
            TaskResult<?> taskResult = task.call();
            results.add(taskResult);
        }
    }


    public void execute(Workflow workflow, TaskContext context) {
        if (isAsync) {
            asyncExecute(workflow, context);
        } else {
            syncExecute(workflow, context);
        }
    }

    public boolean areTasksFinished() {
        return futureTasks != null && futureTasks.stream().allMatch(Future::isDone);
    }

    public List<TaskResult<?>> getResults() {
        if (isAsync) {
            results.clear();
            futureTasks.forEach(f-> {
                if(f.isDone()) {
                    try {
                        results.add((TaskResult<?>) f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("Error");
                    }
                }
            });
        }
        return results;
    }

    public void waitForFinish() {
        if (!futureTasks.isEmpty())
            futureTasks.forEach(future -> {
                try {
                    future.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
