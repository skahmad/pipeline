package forgesystem.tasks;

import forgesystem.workflow.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTask extends Task<Connection> {

	@Override
	protected Connection process() throws Exception {
        String URL = (String) context.getParameter("URL");
        String USER = (String) context.getParameter("USER");
        String PASSWORD = (String) context.getParameter("PASSWORD");

        // Establish the connection
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        if (connection != null) {
            System.out.println("Connected to the database!");
            context.setParameter("connection", connection);
            return connection;
        }

        throw new Exception();
	}
}
