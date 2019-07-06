package MiniFFLogs.MySQL;

import java.sql.Connection;

public interface Command {
    void run(Connection connection, String query);
}
