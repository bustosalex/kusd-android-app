package edu.uwp.kusd.schools;

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

/**
 * Created by Liz on 11/2/2016.
 */



public class SchoolXmlParser {

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
     * Constructs a SchoolXmlParser with a string of XML data as a parameter.
     *
     * @param xmlData string of XML data
     */


//lists to hold each school separated by type
    List<School> Eschools = new ArrayList<>();
    List<School> Mschools = new ArrayList<>();
    List<School> Hschools = new ArrayList<>();
    List<School> Cschools = new ArrayList<>();


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
     * Schools are contained in nodes in the XML. Parses the individual nodes for edu.uwp.kusd.schools.
     *
     * @return a list of parsed edu.uwp.kusd.schools
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParseException
     */
    public List<School> parseNodes(Integer i) throws XmlPullParserException, IOException, ParseException {


        //Parse each event node
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            //Add parse data of edu.uwp.kusd.schools and add parsed edu.uwp.kusd.schools matching only the selected type to the list to be returned
            String name = parser.getName();
            School tempSchool = parseSchool();

            if (tempSchool.schoolType.contains("Elementary Schools")) {
                Eschools.add(tempSchool);
            }
            else if  (tempSchool.schoolType.contains("Middle Schools")) {
                Mschools.add(tempSchool);
            }
            else if  (tempSchool.schoolType.contains("High Schools")) {
                Hschools.add(tempSchool);
            }
            else if  (tempSchool.schoolType.contains("Charter Schools")) {
                Cschools.add(tempSchool);
            }

        }
        //index passed in to get the right school type
        if (i == 0) {
            return Eschools;
        }
        else if (i == 1) {
            return Mschools;
        }
        else if (i == 2) {
            return Hschools;
        }
        else
            return Cschools;

    }


    /**
     * Parses school data
     *
     * @return a new school with the parsed data.
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
        String schoolType = null;
        String city = null;
        String zip = null;
        String hours = null;
        String state = null;


        //check for data type, the ones that are not displayed are ignored
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
                city = readCity();

            } else if (name.equals("zip")) {
                zip = readZip();

            } else if (name.equals("hours")) {
                skip(parser);
                //hours aren't available for all edu.uwp.kusd.schools, so they are left out

            } else if (name.equals("phone")) {
                phone = readPhone();

            } else if (name.equals("principal")) {
                principal = readPrincipal();

            } else if (name.equals("website")) {
                website = readWebsite();

            } else if (name.equals("state")) {
                skip(parser);

            } else if (name.equals("schooltype")) {
                schoolType = readSchoolType();
            }
        }


        return new School(schoolName, image, schoolType, address, city, zip, phone, principal, website);


    }

  //skip method for the variables that are not needed
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

    //all the read methods
    private String readCity() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "city");
        String city = readText();
        parser.require(XmlPullParser.END_TAG, null, "city");
        return city.concat(", WI, ");
    }


    private String readZip() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "zip");
        String zip = readText();
        parser.require(XmlPullParser.END_TAG, null, "zip");
        return zip;
    }


    private String readSchoolType() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "schooltype");
        String schoolType = readText();
        parser.require(XmlPullParser.END_TAG, null, "schooltype");
        return schoolType;
    }


    private String readSchoolName() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "name");
        String schoolName = readText();
        parser.require(XmlPullParser.END_TAG, null, "name");
        return schoolName;
    }


    private String readImage() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "image");
        String image = readText();
        parser.require(XmlPullParser.END_TAG, null, "image");

        return image;
    }


    private String readAddress() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "address");
        String address = readText();
        parser.require(XmlPullParser.END_TAG, null, "address");
        return address;
    }


    private String readPhone() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "phone");
        String phone = readText();
        parser.require(XmlPullParser.END_TAG, null, "phone");
        return phone;
    }


    private String readWebsite() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "website");
        String website = readText();
        parser.require(XmlPullParser.END_TAG, null, "website");
        return website;
    }



    private String readPrincipal() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "principal");
        String principal = readText();
        parser.require(XmlPullParser.END_TAG, null, "principal");
        return principal;
    }


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

