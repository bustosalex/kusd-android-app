// TODO: 10/3/2016 - Implement callback methods

package edu.uwp.kusd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.uwp.kusd.network.CacheStringRequest;
import edu.uwp.kusd.network.InternalStorage;
import edu.uwp.kusd.network.VolleySingleton;

/**
 * Class for a fragment to display the list of events.
 */
public class EventsFragment extends Fragment {

    /**
     * A constant for the URL where the event XML is located.
     */
    private static final String EVENT_CALENDAR_URL = "http://www.kusd.edu/xml-calendar";

    /**
     *
     */
    private static final String PARSED_EVENTS = "parsed events";

    /**
     * A list of all events parsed from the XML.
     */
    private List<Event> mEventList;

    /**
     * A RecyclerView to show the events in.
     */
    private RecyclerView recyclerView;

    /**
     * An EventDate to use as a filter to show only the events in a desired month year
     */
    private EventDate filterDate = new EventDate(2016, 10, 0);

    /**
     * A HashMap with EventDates and keys and lists of events as values.
     */
    private HashMap<EventDate, List<Event>> mEventsByMonth;

    /**
     *
     */
    RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    /**
     * Create the view for the fragment, request the XML data from KUSD, and parse the events.
     *
     * @param inflater layout inflater for the fragment
     * @param container a container for the fragment
     * @param savedInstanceState saved bundle of data from a previous state if any
     * @return a view for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the fragments view
        final View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        //Request XML from KUSD
        //TODO: Handle request errors
        //TODO: Close the queue

        Cache.Entry temp = requestQueue.getCache().get(EVENT_CALENDAR_URL);
        if (temp != null && (!temp.isExpired() && !temp.refreshNeeded())) {
            try {
                @SuppressWarnings("unchecked")
                HashMap<EventDate, List<Event>> tempeventslist = (HashMap<EventDate, List<Event>>) InternalStorage.readObject(getContext(), PARSED_EVENTS);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                EventsRVAdapter adapter = new EventsRVAdapter(tempeventslist, getActivity(), filterDate);
                recyclerView.setAdapter(adapter);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {

            StringRequest stringRequest = new CacheStringRequest(Request.Method.GET, EVENT_CALENDAR_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Create a new XML parser
                    String urlRegex = "<a href=\"https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&/=]*)\">";
                    response = response.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br/>", "").replaceAll("<br />", "").replaceAll("</a>", "").replaceAll(urlRegex, "").replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</li>", "").replaceAll("</ul>", "").replaceAll("<strong>", "").replaceAll("</strong>", "");
                    EventXmlParser calendarXmlParser = new EventXmlParser(response);

                    try {
                        //Parse the events into a list
                        mEventList = calendarXmlParser.parseNodes();

                        //Instantiate the RecyclerView for the events list
                        recyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        //Separate events by month-year
                        mEventsByMonth = eventSeparator(mEventList);
                        InternalStorage.writeObject(getContext(), PARSED_EVENTS, mEventsByMonth);

                        //Pass the separated events HashMap into the RecyclerView with
                        EventsRVAdapter adapter = new EventsRVAdapter(mEventsByMonth, getActivity(), filterDate);
                        recyclerView.setAdapter(adapter);
                    } catch (XmlPullParserException | IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    calendarXmlParser.closeXmlSteam();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(stringRequest);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(EVENT_CALENDAR_URL);
        }
    }

    /**
     * Separates events by month-year into a HashMap.
     *
     * @param allEvents the un-separated list of events
     * @return a separated list of events
     */
    private HashMap<EventDate, List<Event>> eventSeparator(List<Event> allEvents) {
        HashMap<EventDate, List<Event>> eventsByMonth = new HashMap<>();
        EventDate tempEventDate;

        //Generate the the entries in the HashMap
        for (Event event : allEvents) {
            tempEventDate = new EventDate(event.getDate().getYear(), event.getDate().getMonth(), 0);
            if (!eventsByMonth.containsKey(tempEventDate)) {
                eventsByMonth.put(tempEventDate, new ArrayList<Event>());
            }
        }

        //Place events into the corresponding list for their month-year
        for (Event event : allEvents) {
            tempEventDate = new EventDate(event.getDate().getYear(), event.getDate().getMonth(), 0);
            eventsByMonth.get(tempEventDate).add(event);
        }
        return eventsByMonth;
    }

    /**
     * A class to parse XML for events.
     */
    private class EventXmlParser {

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

        /**
         * Constructs an EventXmlParser with a string of XML data as a parameter.
         *
         * @param xmlData string of XML data
         */
        public EventXmlParser(String xmlData) {
            try {
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
         * @return a list of parsed events
         * @throws XmlPullParserException
         * @throws IOException
         * @throws ParseException
         */
        public List<Event> parseNodes() throws XmlPullParserException, IOException, ParseException {
            List<Event> events = new ArrayList<>();

            //Parse each event node
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                //Add parse data of events and add parsed events to the list to be returned
                Event tempEvent = parseEvent();
                events.add(tempEvent);
            }
            return events;
        }

        /**
         * Parses event data - including title, date, school, and details for the event.
         *
         * @return a new Event with the parsed data.
         * @throws IOException
         * @throws XmlPullParserException
         */
        private Event parseEvent() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "node");
            String title = null;
            EventDate date = null;
            String school = null;
            String details = null;
            String dateString = null;

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
                      //  dateString = readDateString();
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
                } else if (name.equals("ul")) {
                    skip();
                }
            }
            return new Event(title, date, school, details, dateString);
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
        private EventDate readDate() throws IOException, XmlPullParserException, ParseException {
            parser.require(XmlPullParser.START_TAG, null, "date");
            String temp = readText();
            temp = temp.substring(0, temp.indexOf(" "));
            EventDate tempEventDate = parseDate(temp);
            parser.require(XmlPullParser.END_TAG, null, "date");
            return tempEventDate;
        }

        /*private String readDateString() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "date");
            String temp = readText();
            parser.require(XmlPullParser.END_TAG, null, "date");
            return temp;
        }*/

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
        void closeXmlSteam() {
            try {
                xmlInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Helper method to parse the text for the date of an event into an EventDate object.
         *
         * @param input the input date
         * @return a parsed EventDate
         */
        private EventDate parseDate(String input) {
            String[] fields = input.split("-");
            return new EventDate(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
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
}