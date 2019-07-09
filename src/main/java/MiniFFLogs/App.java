package MiniFFLogs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import MiniFFLogs.MySQL.InsertHandler;
import MiniFFLogs.MySQL.MySQLConnect;
import org.xml.sax.SAXException;

//TODO: Multi-thread, consume folders of ACT xml files and add them to tables
public class App 
{
    public static void main( String[] args )
    {
        double time = System.currentTimeMillis();
        System.out.println(time);
        String filePath = "C:\\Users\\Chaotic\\Documents\\RaidXML\\Titania_1.xml";
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
        List<Thread> threadList = new ArrayList<>();
        for (Player player : readXML.getList()) {
            if (player.getName().equalsIgnoreCase("Limit Break")) {
                continue;
            }
            Thread thread = new Thread(new InsertHandler(dbConnection, player));
            thread.start();
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mySQLConnect.disconnect(dbConnection);
        System.out.println(System.currentTimeMillis() - time);
    }
}
