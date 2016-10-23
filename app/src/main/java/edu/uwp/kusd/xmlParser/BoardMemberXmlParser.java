package edu.uwp.kusd.xmlParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uwp.kusd.BoardMember;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMemberXmlParser {

    private XmlPullParser mParser;

    private XmlPullParserFactory mXmlPullParserFactory;

    private InputStream mXmlInputStream;

    public BoardMemberXmlParser(String xmlData) {
        try {
            mXmlInputStream = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
            mXmlPullParserFactory = XmlPullParserFactory.newInstance();
            mParser = mXmlPullParserFactory.newPullParser();
            mParser.setInput(mXmlInputStream, null);
        } catch (UnsupportedEncodingException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public List<BoardMember> parseBoardMembers() throws XmlPullParserException, IOException, ParseException {
        List<BoardMember> members = new ArrayList<>();

        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            BoardMember tempMember = parseMember();
            members.add(tempMember);
        }
        return members;
    }

    private BoardMember parseMember() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "node");
        String photoURL = null;
        String name = null;
        String position = null;
        String email = null;
        String phone = null;
        Date term = null;

        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = mParser.getName();
            if (tag.equals("photo")) {
                photoURL = readPhotoURL();
            } else if (tag.equals("name")) {
                name = readName();
            } else if (tag.equals("position")) {
                position = readPosition();
            } else if (tag.equals("email")) {
                email = readEmail();
            } else if (tag.equals("phone")) {
                phone = readPhone();
            } else if (tag.equals("termexpires")) {
                term = readTerm();
            } else if (tag.equals("biography")) {
                skip();
            } else if (tag.equals("li")) {
                skip();
            } else if (tag.equals("ul")) {
                skip();
            }
        }
        return new BoardMember(null, photoURL, name, position, email, phone, term);
    }

    private String readPhotoURL() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "photo");
        String photoUrl = readText();
        mParser.require(XmlPullParser.END_TAG, null, "photo");
        return photoUrl;
    }

    private String readName() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "name");
        String name = readText();
        mParser.require(XmlPullParser.END_TAG, null, "name");
        return name;
    }

    private String readPosition() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "position");
        String position = readText();
        mParser.require(XmlPullParser.END_TAG, null, "position");
        return position;
    }

    private String readEmail() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "email");
        String email = readText();
        mParser.require(XmlPullParser.END_TAG, null, "email");
        return email;
    }

    private String readPhone() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "phone");
        String phone = readText();
        mParser.require(XmlPullParser.END_TAG, null, "phone");
        return phone;
    }

    private Date readTerm() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, "termexpires");
        String temp = readText();
        temp = temp.substring(0, temp.indexOf(" "));
        Date term = parseDate(temp);
        mParser.require(XmlPullParser.END_TAG, null, "termexpires");
        return term;
    }

    private void skip() throws XmlPullParserException, IOException {
        if (mParser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (mParser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText() throws IOException, XmlPullParserException {
        String result = "";
        if (mParser.next() == XmlPullParser.TEXT) {
            result = mParser.getText();
            mParser.nextTag();
        }
        return result;
    }

    private Date parseDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.US);
        //String[] fields = input.split("-");
        Date tempDate = null;
        try {
            tempDate = dateFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    public void close() {
        try {
            mXmlInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}