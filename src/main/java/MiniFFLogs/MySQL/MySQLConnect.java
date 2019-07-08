package MiniFFLogs.MySQL;

import java.sql.*;

public class MySQLConnect {

    private String server = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "fakepass";

    //TODO: Maybe move this somewhere else
    public void checkDB(String db) {
        try {
            Connection connection = DriverManager.getConnection(server, user, pass);
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
                NewDB newDB = new NewDB();
                newDB.createDB(connection, db);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connectToDB(String db) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(server + db, user, pass);
            System.out.println("Connection with " + db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void disconnect(Connection connection) {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
