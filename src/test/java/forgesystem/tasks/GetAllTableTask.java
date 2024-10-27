package forgesystem.tasks;

import forgesystem.workflow.Task;
import forgesystem.workflow.TaskContext;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetAllTableTask extends Task<List<String>> {
	@Override
	public List<String> process() throws Exception {
		Connection connection = (Connection) context.getParameter("connection");
		List<String> tableNames = new ArrayList<>();

		// Retrieve the metadata
		DatabaseMetaData metaData = connection.getMetaData();

		// Get tables from the metadata
		ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

		while (tables.next()) {
			String tableName = tables.getString("TABLE_NAME");
			tableNames.add(tableName);
		}
		System.out.println("All table collected");

		context.setParameter("all_tables", tableNames);
		return tableNames;
	}
}
