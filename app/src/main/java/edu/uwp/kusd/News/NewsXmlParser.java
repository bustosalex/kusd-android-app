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

    /**
     * The xml as a string
     */
    private String xml;


    /**
     * Constructs a NewsXmlParser with a string of XML data as a parameter.
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
     * NewsItems are contained in nodes in the XML. Parses the individual nodes for NewsItems.
     *
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParseException
     */
    public List<NewsItems> parseNodes() throws XmlPullParserException, IOException, ParseException {
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
     * Parses NewsItems data
     *
     * @return a new NewsItems with the parsed data.
     * @throws IOException
     * @throws XmlPullParserException
     */
    private NewsItems parseNewsItem() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "node");
        String title = null;
        String date = null;
        String desc = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle().replaceAll("&#039;", "'").replaceAll("&#038;", "");
            } else if (name.equals("date")) {
                date = readDate();
            } else if (name.equals("story")) {
                desc = readStory().replaceAll("</p>", "\n").replaceAll("<[^>]+>","").replaceAll("&#039;", "'").replaceAll("&#038;", "");
            }
        }
        return new NewsItems(title, date, desc);
    }

    /**
     * Helper method to read the title of a NewsItem.
     *
     * @return the title of an NewsItem
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
     * Helper method to read the date for an NewsItem.
     *
     * @return the date of a NewsItem
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readDate() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "date");
        String date = readText();
        parser.require(XmlPullParser.END_TAG, null, "date");
        return date;
    }

    /**
     * Helper method to read the story of a NewsItem
     *
     * @return the story of a NewsItems
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readStory() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "story");
        String story = readText();
        parser.require(XmlPullParser.END_TAG, null, "story");
        return story;
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

    /**
     * Skips an XML tag
     *
     * @throws XmlPullParserException
     * @throws IOException
     */
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