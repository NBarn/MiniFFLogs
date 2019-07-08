package MiniFFLogs.MySQL;

import MiniFFLogs.MySQL.Commands.CreateTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NewDB {

    private String createDB = "CREATE DATABASE %s";

    private String createTable1 = "CREATE TABLE IF NOT EXISTS players " +
            "(player_id INT(255) NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(50) NOT NULL UNIQUE," +
            "job VARCHAR(4) NOT NULL," +
            "static BOOLEAN," +
            "PRIMARY KEY(player_id)" +
            ");";

    private String createTable2 = "CREATE TABLE IF NOT EXISTS damage " +
            "(id INT(255) NOT NULL AUTO_INCREMENT," +
            "player_id INT(255) NOT NULL," +
            "dps DECIMAL(65, 2) NOT NULL," +
            "dmg_perc DECIMAL(65, 2) NOT NULL," +
            "critical_hit_perc DECIMAL(65, 2) NOT NULL," +
            "direct_hit_perc DECIMAL(65, 2) NOT NULL," +
            "crit_dh_perc DECIMAL(65, 2) NOT NULL," +
            "deaths INT(255) NOT NULL," +
            "duration INT(255)," +
            "date DATETIME NOT NULL," +
            "PRIMARY KEY(id)" +
            ");";

    private String createTable3 = "CREATE TABLE IF NOT EXISTS healing " +
            "(id INT(255) NOT NULL AUTO_INCREMENT," +
            "player_id INT(255) NOT NULL," +
            "hps DECIMAL(65, 2) NOT NULL," +
            "healing_perc DECIMAL(65, 2) NOT NULL," +
            "critical_heal_perc DECIMAL(65, 2) NOT NULL," +
            "overheal_perc DECIMAL(65, 2) NOT NULL," +
            "deaths INT(255) NOT NULL," +
            "duration INT(255)," +
            "date DATETIME NOT NULL," +
            "PRIMARY KEY(id)" +
            ");";

    private String createTable4 = "CREATE TABLE IF NOT EXISTS topdps " +
            "(player_id INT(255) NOT NULL," +
            "dps_id INT(255) NOT NULL," +
            "top_dps DECIMAL(65, 2) NOT NULL," +
            "PRIMARY KEY(player_id)" +
            ");";

    private String createTable5 = "CREATE TABLE IF NOT EXISTS total_deaths " +
            "(player_id INT(255) NOT NULL AUTO_INCREMENT," +
            "deaths INT(255)," +
            "PRIMARY KEY(player_id)" +
            ");";

    public void createDB(Connection connection, String dbName) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(String.format(createDB, dbName));
            connection.close();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "fakepass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CreateTable createTable = new CreateTable();
        createTable.run(connection, createTable1);
        createTable.run(connection, createTable2);
        createTable.run(connection, createTable3);
        createTable.run(connection, createTable4);
        createTable.run(connection, createTable5);
    }
}
