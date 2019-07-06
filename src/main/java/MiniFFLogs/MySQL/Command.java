package MiniFFLogs.MySQL;

import java.sql.Connection;

public interface Command {
    void Run(Connection connection, String query);
}
