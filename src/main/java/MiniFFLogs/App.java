package MiniFFLogs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import MiniFFLogs.MySQL.InsertHandler;
import MiniFFLogs.MySQL.MySQLConnect;
import org.xml.sax.SAXException;

//TODO: Create a NewFight class that creates all default tables
//TODO: Multi-thread, consume folders of ACT xml files and add them to tables
public class App 
{
    public static void main( String[] args )
    {
        String filePath = "C:\\Users\\Chaotic\\Documents\\RaidXML\\Titania_2.xml";
        File xmlFile = new File(filePath);
        ReadXML readXML = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            readXML = new ReadXML();
            saxParser.parse(xmlFile, readXML);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MySQLConnect mySQLConnect = new MySQLConnect();
        mySQLConnect.checkDB(readXML.getBoss());
        Connection dbConnection  = mySQLConnect.connectToDB(readXML.getBoss());
        if (dbConnection == null) {
            return;
        }
//        Command createTable = new CreateTable();
//        String query = "CREATE TABLE IF NOT EXISTS players (" +
//                "id INT NOT NULL AUTO_INCREMENT," +
//                "first_name VARCHAR(50) NOT NULL UNIQUE," +
//                "job VARCHAR(4) NOT NULL," +
//                "static BOOLEAN," +
//                "PRIMARY KEY(id)" +
//                ");";
//        createTable.run(dbConnection, query);
//        Command insert = new Insert();
        InsertHandler insertHandler = new InsertHandler();
        for (Player player : readXML.getList()) {
            if (player.getName().equalsIgnoreCase("Limit Break")) {
                continue;
            }
            //System.out.println(dbConnection);
            insertHandler.handleInserts(dbConnection, player);
//            System.out.println(String.format(table1, player.getFirstName(), player.getJob(), "true"));
//            StringBuilder str = new StringBuilder("INSERT INTO players " +
//                                                    "VALUES " +
//                                                    "(DEFAULT," +
//                                                    "'" + player.getFirstName() + "'" + "," +
//                                                    "'" + player.getJob() + "'" + "," +
//                                                    "true" +
//                                                    ");");
//            insert.run(dbConnection, str.toString());
        }

//        Select select = new Select();
//        query = "SELECT *" +
//                "FROM players;";
//        ResultSet set = select.run(dbConnection, query);
//        try {
//            while (set.next()) {
//                System.out.println(set.getString("first_name"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        query = "INSERT INTO players " +
//                "VALUES " +
//                    "(DEFAULT," +
//                    "'test'," +
//                    "'test1'," +
//                    "'tes'," +
//                    "true" +
//                    ");";
        mySQLConnect.disconnect(dbConnection);
//        for (Player player : readXML.getList()) {
//            System.out.println(player.toString());
//        }
    }
}
