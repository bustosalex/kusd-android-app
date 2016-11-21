package edu.uwp.kusd.xmlParser;

/**
 * Created by Dakota on 11/12/2016.
 */

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

import edu.uwp.kusd.homepage.Highlight;

/**
 * A class to parse XML for events.
 */
public class HighlightsXmlParser {

    /**
     * An input stream used to parse the XML from a string.
     */
    private InputStream xmlInputStream;

    /**
     * Used in parsing the XML.
     */
    XmlPullParserFactory xmlPullParserFactory;

    /**
     * Used in parsing the XML.
     */
    XmlPullParser parser;

    private String xml;

    /**
     * Constructs an EventXmlParser with a string of XML data as a parameter.
     *
     * @param xmlData string of XML data
     */
    public HighlightsXmlParser(String xmlData) {
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
    public List<Highlight> parseNodes() throws XmlPullParserException, IOException, ParseException {
        //Parse each event node
        List<Highlight> highlights = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            highlights.add(parseHighlight());
        }
        closeXmlSteam();
        return highlights;
    }

    /**
     * Parses event data - including title, date, school, and details for the event.
     *
     * @return a new Event with the parsed data.
     * @throws IOException
     * @throws XmlPullParserException
     */
    private Highlight parseHighlight() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "node");
        String imageTitle = null;
        String imageUrl = null;
        String imageLink = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                imageTitle = readTitle();
            } else if (name.equals("image")) {
                imageUrl = readImageUrl();
            } else if (name.equals("link")) {
                imageLink = readImageLink();
            } else if (name.equals("body")) {
                skip();
            }
        }
        return new Highlight(imageTitle, imageUrl, imageLink);
    }

    /**
     * Helper method to read the title of a file
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
     * Helper method to read the URL of a file.
     *
     * @return the details of an event
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readImageUrl() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "image");
        String details = readText();
        parser.require(XmlPullParser.END_TAG, null, "image");
        return details;
    }

    private String readImageLink() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "link");
        String details = readText();
        parser.require(XmlPullParser.END_TAG, null, "link");
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