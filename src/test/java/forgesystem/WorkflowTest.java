package forgesystem;

import forgesystem.tasks.DatabaseConnectionTask;
import forgesystem.tasks.FilterTableNameTask;
import forgesystem.tasks.GetAllTableTask;
import forgesystem.workflow.*;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WorkflowTest extends TestCase {
	public void test_workflow() {
		Workflow workflow = new Workflow();
		TaskContext context = new TaskContext();
		context.setParameter("URL", "jdbc:postgresql://localhost:5432/lunar_loom_api_analyzer");
		context.setParameter("USER", "postgres");
		context.setParameter("PASSWORD", "moon@light");
		WorkflowExecutor executor = new WorkflowExecutor(false);

		workflow.addTask(new DatabaseConnectionTask());
        workflow.addTask(new GetAllTableTask());

		// Execute tasks
		long s = System.currentTimeMillis();
        executor.execute(workflow, context);
		long e = System.currentTimeMillis();
		System.out.println("time taken - " + (e-s));

		List<TaskResult<?>> results = executor.getResults();
		TaskResult<?> dbConnectionR = results.get(0);
		System.out.println("Time taken db connection: " + dbConnectionR.getTimeTaken());

		TaskResult<?> taskResult = results.get(1);
		System.out.println("Status: " + taskResult.getStatus());
		System.out.println("Result at: " + taskResult.getTimestamp());
		System.out.println("Time taken: " + taskResult.getTimeTaken());
		List<String> tables = (List<String>) taskResult.getResult().get();
		System.out.println("Tables found: " + tables.size());

		// Record results
        for (Task<?> task : workflow.getTasks()) {
            int index = workflow.getTasks().indexOf(task);
			System.out.println("Task: " + task.getName() + ", Id: " + task.getId() +", Result: " + results.get(index) );
        }
	}

	public void test_workflow_async() throws InterruptedException {
		Workflow workflow = new Workflow();
		TaskContext context = new TaskContext();
		context.setParameter("URL", "jdbc:postgresql://localhost:5432/lunar_loom_api_analyzer");
		context.setParameter("USER", "postgres");
		context.setParameter("PASSWORD", "moon@light");
		WorkflowExecutor executor = new WorkflowExecutor(true);

		DatabaseConnectionTask d = new DatabaseConnectionTask();
		GetAllTableTask t = new GetAllTableTask();
		workflow.addTask(d);
		workflow.addTask(t);

		// Execute tasks
		executor.execute(workflow, context);

		executor.waitForFinish();

		for (TaskResult<?> result : executor.getResults()) {
			System.out.println("Task name: " + result.getTask().getName());
			System.out.println("Task id: " + result.getTask().getId());
			System.out.println("Time taken: " + result.getTimeTaken());
			System.out.println("Time status: " + result.getStatus());
			System.out.println("Time stamp:" + result.getTimestamp());
			if (result.getStatus().equals("FAILED")) {
				System.out.println("Task Error:" + result.getError());
			}
		}
	}

	public void test_workflow_async_hook() throws InterruptedException {
		Workflow workflow = new Workflow();
		TaskContext context = new TaskContext();
		context.setParameter("URL", "jdbc:postgresql://localhost:5432/lunar_loom_api_analyzer");
		context.setParameter("USER", "postgres");
		context.setParameter("PASSWORD", "moon@light");
		context.setParameter("filter_name", "forge");
		WorkflowExecutor executor = new WorkflowExecutor(true);

		DatabaseConnectionTask db = new DatabaseConnectionTask();
		GetAllTableTask t = new GetAllTableTask();
		FilterTableNameTask f = new FilterTableNameTask();
		workflow.addTask(db);
		workflow.addTask(t);
		workflow.addTask(f);

		workflow.setBeforeEach((c)->{
			System.out.println("-- Start executing task");
		});

		workflow.setAfterEach((c)->{
			System.out.println("-- End executing task");
		});

		db.addBeforeHook((c)->{
			c.setParameter("USER", "postgres");
			System.out.println("database before");
		});

		db.addAfterHook((c)->{
			System.out.println("database connected after");
		});

		// Execute tasks
		executor.execute(workflow, context);

		executor.waitForFinish();

		for (TaskResult<?> result : executor.getResults()) {
			System.out.println("Task name: " + result.getTask().getName());
			System.out.println("Task id: " + result.getTask().getId());
			System.out.println("Time taken: " + result.getTimeTaken());
			System.out.println("Time status: " + result.getStatus());
			System.out.println("Time stamp:" + result.getTimestamp());
			if (result.getStatus().equals("FAILED")) {
				System.out.println("Task Error:" + result.getError());
			}
		}

		Optional<TaskResult<?>> r = executor.getResults().stream().filter(result -> result.getTask().getId().equals(f.getId())).findFirst();

		if (r.isPresent()) {
			List<String> filterTable = (List<String>) r.get().getResult().get();
			System.out.println(filterTable);
		}
	}

}
