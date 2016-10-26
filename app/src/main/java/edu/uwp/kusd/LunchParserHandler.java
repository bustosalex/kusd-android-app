package edu.uwp.kusd;

/**
 * Created by Cabz on 10/25/2016.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class LunchParserHandler {

    List<LunchObj> schoolLunches;
    private LunchObj lunchObj;
    private String text;

    public LunchParserHandler(){
        schoolLunches = new ArrayList<LunchObj>();
    }

    public List<LunchObj> getSchoolLunches(){
        return schoolLunches;
    }

    public List<LunchObj> parse(InputStream is){
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("title")) {
                            // create a new instance of LunchObj
                           lunchObj = new LunchObj();

                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("title")) {
                            // add new school to list
                            schoolLunches.add(lunchObj);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            lunchObj.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("file")) {
                            lunchObj.setFileUrl(text);
                        } else if (tagname.equalsIgnoreCase("category")) {
                            lunchObj.setCategory(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schoolLunches;
    }


    }






