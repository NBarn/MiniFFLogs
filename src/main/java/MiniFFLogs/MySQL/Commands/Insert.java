package MiniFFLogs.MySQL.Commands;

import MiniFFLogs.MySQL.Command;
import MiniFFLogs.Player;

import java.sql.*;

public class Insert implements Command {

    @Override
    public void execute(Connection connection, String query) {
        try {
            Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate(query);
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Duplicate entry attempting to be added to table");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
