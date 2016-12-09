package edu.uwp.kusd.xmlParser;

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


public class EventXmlParser {

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
     * The realm DB
     */
    private Realm mRealm;

    /**
     * The xml input
     */
    private String xml;

    /**
     * The duplicate dates to skip over
     */
    private List<String> skipDates;

    /**
     * Constructs an EventXmlParser with a string of XML data as a parameter.
     *
     * @param xmlData string of XML data
     */
    public EventXmlParser(String xmlData) {
        try {
            mRealm = Realm.getDefaultInstance();
            skipDates = new ArrayList<>();
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
    public void parseNodes() throws XmlPullParserException, IOException, ParseException {
        //Parse each event node
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            parseEvent();
        }
        mRealm.close();
        closeXmlSteam();
    }

    /**
     * Parses event data - including title, date, school, and details for the event.
     *
     * @return a new Event with the parsed data.
     * @throws IOException
     * @throws XmlPullParserException
     */
    private void parseEvent() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "node");
        String title = null;
        String date = null;
        String school = null;
        String details = null;
        String dateString;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //Parse the title if the current tag is title
            if (name.equals("title")) {
                title = readTitle();
                //Parse the date if the current tag is date
            } else if (name.equals("date")) {
                try {
                    date = readDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Parse the school name if the current tag is school
            } else if (name.equals("school")) {
                school = readSchool();
                //Parse the details of an event if the current tag is details
            } else if (name.equals("details")) {
                details = readDetails();
            } else if (name.equals("p")) {
                skip();
            } else if (name.equals("br")) {
                skip();
            } else if (name.equals("image")) {
                skip();
            }
        }
        dateString = date;
        //Check for events that are duplicates
        if (skipDates.contains(dateString)) {
            return;
        } else if (dateString.matches("((\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+),\\s)+(\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+){1}") || date.matches("((\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+)\\s(to)\\s(\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+),\\s)+(\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+)\\s(to)\\s(\\d+-\\d+-\\d+)\\s(\\d+:\\d+:\\d+)")) {
            processMultipleDates(title, details, school, dateString);
        } else {
            date = date.substring(0, date.indexOf(" "));
            String[] splitDate = date.split("-");
            mRealm.beginTransaction();
            Event event = mRealm.createObject(Event.class);
            event.setEventTitle(title);
            event.setYear(splitDate[0]);
            event.setMonth(splitDate[1]);
            event.setDay(splitDate[2]);
            event.setDetails(details);
            UUID id = UUID.randomUUID();
            event.setId(id.toString());
            event.setSchool(school);
            mRealm.commitTransaction();
        }
    }

    /**
     * Processes an event that has multiple dates.
     *
     * @param title the title of an event
     * @param details the details of an event
     * @param school the school of an event
     * @param dateString the date string of an event
     */
    private void processMultipleDates(String title, String details, String school, String dateString) {
        String[] dates = dateString.split(",\\s");
        for (String date : dates) {
            date = date.substring(0, date.indexOf(" "));
            String[] splitDate = date.split("-");
            mRealm.beginTransaction();
            Event event = mRealm.createObject(Event.class);
            event.setEventTitle(title);
            event.setYear(splitDate[0]);
            event.setMonth(splitDate[1]);
            event.setDay(splitDate[2]);
            event.setDetails(details);
            UUID id = UUID.randomUUID();
            event.setId(id.toString());
            event.setSchool(school);
            mRealm.commitTransaction();
        }
        skipDates.add(dateString);
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
     * Helper method to read the date of an event.
     *
     * @return the EventDate for an event
     * @throws IOException
     * @throws XmlPullParserException
     * @throws ParseException
     */
    private String readDate() throws IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, null, "date");
        String temp = readText();
        parser.require(XmlPullParser.END_TAG, null, "date");
        return temp;
    }

    /**
     * Helper method to read the school name for an event.
     *
     * @return the name of an event
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readSchool() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "school");
        String school = readText();
        parser.require(XmlPullParser.END_TAG, null, "school");
        return school;
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

    /**
     * Skips a tag
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