package MiniFFLogs.MySQL;

import java.sql.*;

public class MySQLConnect {

    private String server = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "fakepass";

    public void CheckDB(String db) {
        try {
            Connection connection = DriverManager.getConnection(server, user, pass);
            // Connection connection = <your java.sql.Connection>
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            //iterate each catalog in the ResultSet
            boolean isDBThere = false;
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                if (databaseName.equalsIgnoreCase(db)) {
                    isDBThere = true;
                    break;
                }
            }
            resultSet.close();

            if (!isDBThere) {
                String sql = "CREATE DATABASE " + db;
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection ConnectToDB(String db) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(server, user, pass);
            System.out.println("Connection with " + db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void Disconnect(Connection connection) {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
