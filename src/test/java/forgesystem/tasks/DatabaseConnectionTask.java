package forgesystem.tasks;

import forgesystem.workflow.Task;
import forgesystem.workflow.TaskContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DatabaseConnectionTask implements Task<Connection> {
	private static final String URL = "jdbc:postgresql://192.168.44.36:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "GIDs@4437";

	@Override
	public Connection executeSync(TaskContext context) throws Exception {
		Connection connection = null;
		try {
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("Connected to the database!");
				context.setParameter("connection", connection);
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }

		return connection;
	}

	@Override
	public CompletableFuture<Connection> executeAsync(TaskContext context) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return this.executeSync(context);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
