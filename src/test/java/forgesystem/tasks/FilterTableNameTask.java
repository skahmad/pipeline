package forgesystem.tasks;

import forgesystem.workflow.Task;

import java.util.List;
import java.util.stream.Collectors;

public class FilterTableNameTask extends Task<List<String>> {
    @Override
    protected List<String> process() throws Exception {
        List<String> tables = (List<String>) context.getParameter("all_tables");
        String filter = (String) context.getParameter("filter_name");
        if (tables.isEmpty()) {
            return tables;
        }

        return tables
                .stream()
                .filter(t->t.toLowerCase().contains(filter))
                .collect(Collectors.toList());
    }
}
