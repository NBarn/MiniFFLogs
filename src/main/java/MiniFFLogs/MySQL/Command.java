package MiniFFLogs.MySQL;

import java.sql.Connection;

public interface Command {
    void execute(Connection connection, String query);
}
