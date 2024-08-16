package forgesystem;

import forgesystem.tasks.DatabaseConnectionTask;
import forgesystem.tasks.GetAllTableTask;
import forgesystem.workflow.*;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class WorkflowTest extends TestCase {
	public void test_workflow() {
		Workflow workflow = new Workflow();
		TaskContext context = new TaskContext(new HashMap<>());
		WorkflowExecutor executor = new WorkflowExecutor(false);
		ExecutionHistory history = new ExecutionHistory();

		workflow.addTask(new DatabaseConnectionTask());
        workflow.addTask(new GetAllTableTask());

		// Execute tasks
        List<Object> results = executor.execute(workflow, context);
		List<String> tables  = (List) results.get(1);

		System.out.println(tables.size());

		// Record results
        for (Task<?> task : workflow.getTasks()) {
            int index = workflow.getTasks().indexOf(task);
            history.addRecord(new ExecutionRecord(task.getName(), results.get(index)));
        }

        // Print history
        history.getRecords().forEach(record ->
                System.out.println("Task: " + record.getTaskName() + ", Result: " + record.getResult() + ", Timestamp: " + record.getTimestamp())
        );
	}

}
