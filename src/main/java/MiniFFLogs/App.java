package MiniFFLogs;

import MiniFFLogs.MySQL.InsertHandler;
import MiniFFLogs.MySQL.MySQLConnect;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        double time = System.currentTimeMillis();
        System.out.println(time);
        File folder = new File("C:\\Users\\Chaotic\\Documents\\RaidXML");
        File[] filesInFolder = folder.listFiles();
        List<Thread> threadsList = new ArrayList<>();
        for (int i = 0; i < filesInFolder.length; i++) {
            Thread thread = new Thread(new NewFile(filesInFolder[i].getPath()));
            thread.start();
            threadsList.add(thread);
        }
        for (Thread thread : threadsList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - time);
    }
}
