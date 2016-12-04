package edu.uwp.kusd;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cabz on 11/30/2016.
 */

public class FeatureParserHandler {
     List<FeatureObject> featureObjectList;
    private FeatureObject featureObject;
    private String text;

    public FeatureParserHandler(){
        featureObjectList = new ArrayList<FeatureObject>();
    }

    public List<FeatureObject> parse(InputStream is){
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
                        if (tagname.equalsIgnoreCase("node")) {
                            // create a new instance of LunchObj
                          featureObject = new FeatureObject();

                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    //Grabs the title, pdf file url and the type of school through parsing the xml.
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("node")) {
                            // add new school to list
                            featureObjectList.add( featureObject);
                        } else if (tagname.equalsIgnoreCase("image")) {
                            featureObject.setImage(text);
                        } else if (tagname.equalsIgnoreCase("link")) {
                            featureObject.setLink(text);
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

        return featureObjectList;
    }


}