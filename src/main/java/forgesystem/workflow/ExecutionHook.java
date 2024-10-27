package forgesystem.workflow;

@FunctionalInterface
public interface ExecutionHook {
    void on(TaskContext context);
}
