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

public class GetAllTableTask implements Task<List<String>> {
	@Override
	public List<String> executeSync(TaskContext context) throws Exception {
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


		return tableNames;
	}

	@Override
	public CompletableFuture<List<String>> executeAsync(TaskContext context) {
		return CompletableFuture.supplyAsync(Collections::emptyList);
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
