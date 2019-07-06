package MiniFFLogs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import MiniFFLogs.MySQL.Command;
import MiniFFLogs.MySQL.Commands.CreateTable;
import MiniFFLogs.MySQL.Commands.Insert;
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
        mySQLConnect.CheckDB(readXML.getBoss());
        Connection dbConnection  = mySQLConnect.ConnectToDB(readXML.getBoss());
        if (dbConnection == null) {
            return;
        }
        Command createTable = new CreateTable();
        String query = "CREATE TABLE IF NOT EXISTS players (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "first_name VARCHAR(50) NOT NULL," +
                "last_name VARCHAR(50) NOT NULL," +
                "job VARCHAR(4) NOT NULL," +
                "static BOOLEAN," +
                "PRIMARY KEY(id)" +
                ");";
        createTable.Run(dbConnection, query);
        Command insert = new Insert();
        for (Player player : readXML.getList()) {
            if (player.getFirstName().equalsIgnoreCase("Limit")) {
                continue;
            }
            StringBuilder str = new StringBuilder("INSERT INTO players " +
                                                    "VALUES " +
                                                    "(DEFAULT," +
                                                    "'" + player.getFirstName() + "'" + "," +
                                                    "'" + player.getLastName() + "'" + "," +
                                                    "'" + player.getJob() + "'" + "," +
                                                    "true" +
                                                    ");");
            insert.Run(dbConnection, str.toString());
        }
//        query = "INSERT INTO players " +
//                "VALUES " +
//                    "(DEFAULT," +
//                    "'test'," +
//                    "'test1'," +
//                    "'tes'," +
//                    "true" +
//                    ");";
        mySQLConnect.Disconnect(dbConnection);
//        for (Player player : readXML.getList()) {
//            System.out.println(player.toString());
//        }
    }
}
