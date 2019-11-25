import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class XMLHandler extends DefaultHandler
{
    private Voter voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private HashMap<String,Short> voterCounts;

    XMLHandler() {
        voterCounts = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"),birthDay);
            }
            else if (qName.equals("visit") && voter != null)
            {
                int count = voterCounts.getOrDefault(voter.toString(),(short)0);
                voterCounts.put(voter.toString(),(short) (count + 1));
            }

        }
        catch (ParseException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (!qName.equals("voter")) voter = null;

    }
    void printDublicatedVoters()
    {
        for (String voter : voterCounts.keySet())
        {
            int count = voterCounts.get(voter);
            if (count > 1) {
                System.out.println(voter + " - " + count);
            }
        }
    }
}
