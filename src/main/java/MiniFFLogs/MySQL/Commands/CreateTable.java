package MiniFFLogs.MySQL.Commands;

import MiniFFLogs.MySQL.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable extends Thread implements Command {

    Connection connection;
    String query;

    public CreateTable(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    @Override
    public void run() {
        execute(connection, query);
    }

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
