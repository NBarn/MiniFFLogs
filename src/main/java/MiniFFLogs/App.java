package MiniFFLogs;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class App 
{
    public static void main( String[] args )
    {
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
//        for (Player player : readXML.getList()) {
//            System.out.println(player.toString());
//        }
    }
}
