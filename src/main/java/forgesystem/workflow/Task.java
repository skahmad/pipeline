package forgesystem.workflow;

import java.util.concurrent.CompletableFuture;

public interface Task<T> {
    T executeSync(TaskContext context) throws Exception;
    CompletableFuture<T> executeAsync(TaskContext context);
    String getName();
}