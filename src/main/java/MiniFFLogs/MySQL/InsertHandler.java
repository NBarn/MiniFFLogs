package MiniFFLogs.MySQL;

import MiniFFLogs.MySQL.Commands.Insert;
import MiniFFLogs.MySQL.Commands.Update;
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

    private String table4 = "INSERT INTO top_dps " +
            "VALUES " +
            "(%d," +
            "%d," +
            "%f" +
            ");";

    private String uTable4 = "UPDATE top_dps " +
            "SET topdps = %f " +
            "WHERE player_id = %d";

    private String table5 = "INSERT INTO total_deaths " +
            "VALUES " +
            "(%d," +
            "%d" +
            ");";

    private String uTable5 = "UPDATE total_deaths " +
            "SET deaths = %d + %d " +
            "WHERE player_id = %d";

    //TODO: Is it worth having multiple function calls?
    public void handleInserts(Connection connection, Player player) {
        insertTable1(connection, player);
        int playerID = playerID(connection, player);
        insertTable2(connection, player, playerID);
        if (player.getJob().equalsIgnoreCase("Sch") || player.getJob().equalsIgnoreCase("Whm")
            || player.getJob().equalsIgnoreCase("Ast")) {
            insertTable3(connection, player, playerID);
        }
        insertTable4(connection, player, playerID);
        insertTable5(connection, player, playerID);
    }

    private void insertTable1(Connection connection, Player player) {
        if (tableHasPlayer(connection, player)) {
            return;
        }
        Insert insert = new Insert();
        insert.run(connection, String.format(table1, player.getName(), player.getJob(), "true"));
    }

    private void insertTable2(Connection connection, Player player, int playerID) {
        Insert insert = new Insert();
        insert.run(connection, String.format(table2, /*playerID(connection, player)*/ playerID, player.getDps(), player.getDmg_perc(), player.getCrit_hit_perc(),
                                                player.getDh_perc(), player.getCrit_dh_perc(), player.getDeaths(),
                                                player.getDuration(), player.getDate()));
    }

    private void insertTable3(Connection connection, Player player, int playerID) {
        Insert insert = new Insert();
        insert.run(connection, String.format(table3, /*playerID(connection, player)*/ playerID, player.getHps(), player.getHealing(), player.getCrit_heal_perc(),
                player.getOverheal_perc(), player.getDeaths(), player.getDuration(), player.getDate()));
    }

    private void insertTable4(Connection connection, Player player, int playerID) {
        if (!haveTopDPS(connection, player)) {
            Insert insert = new Insert();
            insert.run(connection, String.format(table4, /*playerID(connection, player)*/ playerID, dpsID(connection, player, playerID), player.getDps()));
        }
        else if (checkTopDPS(connection, player, playerID)) {
            Update update = new Update();
            update.run(connection, String.format(uTable4, player.getDps(), /*playerID(connection, player)*/ playerID));
        }
    }

    private void insertTable5 (Connection connection, Player player, int playerID) {
        if (!hasDeaths(connection, player, playerID)) {
            Insert insert = new Insert();
            insert.run(connection, String.format(table5, playerID, player.getDeaths()));
        }
        else {
            Update update = new Update();
            update.run(connection, String.format(uTable5, totalDeaths(connection, player, playerID), player.getDeaths(), playerID));
        }
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

    private boolean haveTopDPS(Connection connection, Player player) {
        try {
            String query = "SELECT player_id " +
                    "FROM top_dps " +
                    "WHERE player_id = " + playerID(connection, player);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkTopDPS(Connection connection, Player player, int playerID) {
        try {
            String query = "SELECT player_id, dps " +
                    "FROM damage " +
                    "WHERE player_id = " + playerID + " " +
                    "AND dps > " + player.getDps();
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasDeaths(Connection connection, Player player, int playerID) {
        try {
            String query = "SELECT player_id " +
                    "FROM total_deaths " +
                    "WHERE player_id = " + playerID;
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

    private int dpsID(Connection connection, Player player, int playerID) {
        try {
            String query = "SELECT id " +
                    "FROM damage " +
                    "WHERE date = '" + player.getDate() + "'" +
                    "AND player_id = " + playerID;
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()) {
                return 0;
            }
            return result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int totalDeaths(Connection connection, Player player, int playerID) {
        try {
            String query = "SELECT deaths " +
                    "FROM total_deaths " +
                    "WHERE player_id = " + playerID;
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            if (!result.next()) {
                return 0;
            }
            return result.getInt("deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
