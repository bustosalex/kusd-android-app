package edu.uwp.kusd;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import edu.uwp.kusd.network.VolleySingleton;


/**
 * Created by Liz on 10/17/2016.
 */

// In this case, the fragment displays simple text based on the page
public class ElemFragment extends Fragment {

    private List<School> mSchools;


    private static final String SCHOOL_LIST_URL = "http://www.kusd.edu/xml-schools";

    /**
     * A list of all events parsed from the XML.
     */
    private RecyclerView rv;


    /**
     * Create the view for the fragment, request the XML data from KUSD, and parse the events.
     *
     * @param inflater           layout inflater for the fragment
     * @param container          a container for the fragment
     * @param savedInstanceState saved bundle of data from a previous state if any
     * @return a view for the fragment
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.elem_school_frag, container, false);


        //Request XML from KUSD

        //TODO: Handle request errors
        //TODO: Close the queue
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCHOOL_LIST_URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                //Create a new XML parser
                SchoolXmlParser schoolXmlParser = new SchoolXmlParser(response);
                try {
                    //Parse the events into a list
                    mSchools = schoolXmlParser.parseNodes();
                    rv = (RecyclerView) rootView.findViewById(R.id.rvE);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setHasFixedSize(true);
                    RVAdapter adapter = new RVAdapter(mSchools, getActivity());
                    rv.setAdapter(adapter);

                } catch (XmlPullParserException | IOException | ParseException e) {
                    e.printStackTrace();
                }
                schoolXmlParser.closeXmlSteam();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);


        return rootView;


    }


    private class SchoolXmlParser {

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


        public SchoolXmlParser(String xmlData) {
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
        public List<School> parseNodes() throws XmlPullParserException, IOException, ParseException {
            List<School> schools = new ArrayList<>();

            //Parse each event node
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                //Add parse data of events and add parsed events to the list to be returned
                String name = parser.getName();
                School tempSchool = parseSchool();
                schools.add(tempSchool);
            }
            return schools;
        }

        /**
         * Parses event data - including title, date, school, and details for the event.
         *
         * @return a new Event with the parsed data.
         * @throws IOException
         * @throws XmlPullParserException
         */
        private School parseSchool() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "node");
            String schoolName = null;
            String image = null;
            String address = null;
            String phone = null;
            String website = null;
            String principal = null;
            // String schoolType = null;
            // String city = null;
            // String zip = null;


            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();


                if (name.equals("name")) {
                    schoolName = readSchoolName();

                } else if (name.equals("image")) {
                    image = readImage();

                } else if (name.equals("address")) {
                    address = readAddress();

                } else if (name.equals("city")) {
                    // city = readCity();
                    skip(parser);

                } else if (name.equals("zip")) {
                    // zip = readZip();
                    skip(parser);

                } else if (name.equals("hours")) {
                    // zip = readZip();
                    skip(parser);

                } else if (name.equals("phone")) {
                    phone = readPhone();

                } else if (name.equals("principal")) {
                    website = readPrincipal();

                } else if (name.equals("website")) {
                    principal = readWebsite();

                } else if (name.equals("schooltype")) {
                    // schoolType = readSchoolType();
                    skip(parser);
                }
            }


            return new School(schoolName, image, address, phone, principal, website);
        }

        /**
         * Helper method to read the school name for an event.
         *
         * @return the name of an event
         * @throws IOException
         * @throws XmlPullParserException
         */


        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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
/**
        private String readCity() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "city");
            String city = readText();
            parser.require(XmlPullParser.END_TAG, null, "city");
            return city;
        }


         * private String readZip() throws IOException, XmlPullParserException {
         * parser.require(XmlPullParser.START_TAG, null, "zip");
         * String zip = readText();
         * parser.require(XmlPullParser.END_TAG, null, "zip");
         * return zip;
         * }
         * <p>
         * /**
         * Helper method to read the details of an event.
         *
         * @return the details of an event
         * @throws IOException
         * @throws XmlPullParserException
         */

        /**
         * private String readSchoolType() throws IOException, XmlPullParserException {
         * parser.require(XmlPullParser.START_TAG, null, "schooltype");
         * String schoolType = readText();
         * parser.require(XmlPullParser.END_TAG, null, "schooltype");
         * return schoolType;
         * }
         * <p>
         * /**
         * Helper method to read the title of an event.
         *
         * @return the title of an event
         * @throws IOException
         * @throws XmlPullParserException
         */
        private String readSchoolName() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "name");
            String schoolName = readText();
            parser.require(XmlPullParser.END_TAG, null, "name");
            return schoolName;
        }

        /**
         * Helper method to read the date of an event.
         *
         * @return the EventDate for an event
         * @throws IOException
         * @throws XmlPullParserException
         * @throws ParseException
         */
        private String readImage() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "image");
            String image = readText();
            parser.require(XmlPullParser.END_TAG, null, "image");
            return image;
        }


        /**
         * Helper method to read the details of an event.
         *
         * @return the details of an event
         * @throws IOException
         * @throws XmlPullParserException
         */
        private String readAddress() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "address");
            String address = readText();
            parser.require(XmlPullParser.END_TAG, null, "address");
            return address;
        }


        /**
         * Helper method to read the details of an event.
         *
         * @return the details of an event
         * @throws IOException
         * @throws XmlPullParserException
         */

        private String readPhone() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "phone");
            String phone = readText();
            parser.require(XmlPullParser.END_TAG, null, "phone");
            return phone;
        }

        /**
         * Helper method to read the details of an event.
         *
         * @return the details of an event
         * @throws IOException
         * @throws XmlPullParserException
         */

        private String readWebsite() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "website");
            String website = readText();
            parser.require(XmlPullParser.END_TAG, null, "website");
            return website;
        }

        /**
         * Helper method to read the details of an event.
         *
         * @return the details of an event
         * @throws IOException
         * @throws XmlPullParserException
         */

        private String readPrincipal() throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, null, "principal");
            String principal = readText();
            parser.require(XmlPullParser.END_TAG, null, "principal");
            return principal;
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
    }




}

