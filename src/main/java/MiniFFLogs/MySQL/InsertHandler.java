package MiniFFLogs.MySQL;

import MiniFFLogs.MySQL.Commands.Insert;
import MiniFFLogs.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertHandler {

    private String table1 = "INSERT INTO players " +
            "VALUES " +
            "(DEFAULT," +
            "'%s'" + "," +
            "'%s'" + "," +
            "%s" +
            ");";

    private String table2 = "INSERT INTO damage " +
            "VALUES " +
            "(DEFAULT," +
            "%d," +
            "%f," +
            "%f," +
            "%f," +
            "%f," +
            "%f," +
            "%d," +
            "%d," +
            "'%s'" +
            ");";

    private String table3 = "INSERT INTO healing " +
            "VALUES " +
            "(DEFAULT," +
            "%d," +
            "%f," +
            "%f," +
            "%f," +
            "%f," +
            "%d," +
            "%d," +
            "'%s'" +
            ");";

    public void handleInserts(Connection connection, Player player) {
        insertTable1(connection, player);
        insertTable2(connection, player);
        if (player.getJob().equalsIgnoreCase("Sch") || player.getJob().equalsIgnoreCase("Whm")
            || player.getJob().equalsIgnoreCase("Ast")) {
            insertTable3(connection, player);
        }
    }

    private void insertTable1(Connection connection, Player player) {
        if (tableHasPlayer(connection, player)) {
            return;
        }
        Insert insert = new Insert();
        insert.run(connection, String.format(table1, player.getName(), player.getJob(), "true"));
    }

    private void insertTable2(Connection connection, Player player) {
        Insert insert = new Insert();
        insert.run(connection, String.format(table2, playerID(connection, player), player.getDps(), player.getDmg_perc(), player.getCrit_hit_perc(),
                                                player.getDh_perc(), player.getCrit_dh_perc(), player.getDeaths(),
                                                player.getDuration(), player.getDate()));
    }

    private void insertTable3(Connection connection, Player player) {
        Insert insert = new Insert();
        insert.run(connection, String.format(table3, playerID(connection, player), player.getHps(), player.getHealing(), player.getCrit_heal_perc(),
                player.getOverheal_perc(), player.getDeaths(), player.getDuration(), player.getDate()));
    }

    private boolean tableHasPlayer(Connection connection, Player player) {
        try {
            String query = "SELECT name, job " +
                            "FROM players " +
                            "WHERE name = '" + player.getName() + "' " +
                            "AND job = '" + player.getJob() + "'";
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int playerID(Connection connection, Player player) {
        try {
            String query = "SELECT player_id " +
                    "FROM players " +
                    "WHERE name = '" + player.getName() + "' " +
                    "AND job = '" + player.getJob() + "'";
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()) {
                return 0;
            }
            return result.getInt("player_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
