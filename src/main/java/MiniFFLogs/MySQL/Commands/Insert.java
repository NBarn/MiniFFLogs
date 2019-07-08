package MiniFFLogs.MySQL.Commands;

import MiniFFLogs.MySQL.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

//TODO: Logic for no duplicates in Table 1 and 4, requires comparison to name AND job, as a player can be added with different jobs
//TODO: Only healers in table 3, requires a comparison for jobs
//TODO: Unique insert class that takes the entry, puts it into threads then inserts it into classes with ligc handled or just logic in separate functions in this class?
    /*
    Example:
        InsertLogic
            Grabs list of players, has 3 separate functions. Function for checking duplicates. Function for the disregard of duplicates. Each function coming back to this class
     */
public class Insert implements Command {

    @Override
    public void run(Connection connection, String query) {
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
