package MiniFFLogs.MySQL.Commands;

import MiniFFLogs.MySQL.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Update implements Command {

    @Override
    public void execute(Connection connection, String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
