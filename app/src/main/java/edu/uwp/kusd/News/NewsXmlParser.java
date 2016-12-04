package edu.uwp.kusd.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uwp.kusd.calendar.Event;
import io.realm.Realm;

/**
 * A class to parse XML for events.
 */
public class NewsXmlParser {

    /**
     * An input stream used to parse the XML from a string.
     */
    private InputStream xmlInputStream;

    /**
     * Used in parsing the XML.
     */
    private XmlPullParserFactory xmlPullParserFactory;

    /**
     * Used in parsing the XML.
     */
    private XmlPullParser parser;

    private String xml;


    /**
     * Constructs an EventXmlParser with a string of XML data as a parameter.
     *
     * @param xmlData string of XML data
     */
    public NewsXmlParser(String xmlData) {
        try {
            xml = xmlData;
            xmlInputStream = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            parser = xmlPullParserFactory.newPullParser();
            parser.setInput(xmlInputStream, null);
        } catch (UnsupportedEncodingException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Events are contained in nodes in the XML. Parses the individual nodes for events.
     *
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParseException
     */
    public List<NewsItems> parseNodes() throws XmlPullParserException, IOException, ParseException {
        //Parse each event node
        List<NewsItems> newsItems = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            NewsItems item = parseNewsItem();
            newsItems.add(item);
        }
        closeXmlSteam();
        return newsItems;
    }

    /**
     * Parses event data - including title, date, school, and details for the event.
     *
     * @return a new Event with the parsed data.
     * @throws IOException
     * @throws XmlPullParserException
     */
    private NewsItems parseNewsItem() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "node");
        String title;
        String date;
        String desc;
        String text;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle();
            } else if (name.equals("date")) {
                date = readSchool();
            } else if (name.equals("story")) {
                desc = readDetails();
            } else if (name.equals("p")) {
                skip();
            } else if (name.equals("br")) {
                skip();
            } else if (name.equals("image")) {
                skip();
            }
        }
        return null;
    }

    /**
     * Helper method to read the title of an event.
     *
     * @return the title of an event
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readTitle() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        String title = readText();
        parser.require(XmlPullParser.END_TAG, null, "title");
        return title;
    }


    /**
     * Helper method to read the school name for an event.
     *
     * @return the name of an event
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readSchool() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "date");
        String date = readText();
        parser.require(XmlPullParser.END_TAG, null, "date");
        return date;
    }

    /**
     * Helper method to read the details of an event.
     *
     * @return the details of an event
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readDetails() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "details");
        String details = readText();
        parser.require(XmlPullParser.END_TAG, null, "details");
        return details;
    }

    /**
     * Helper method to read the text contained in a tag.
     *
     * @return the text contained within a given tag
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readText() throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Closes the XML stream for the object
     */
    private void closeXmlSteam() {
        try {
            xmlInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void skip() throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}