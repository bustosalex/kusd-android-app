// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd.calendar;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * A class to encapsulate event data.
 * Relevant fields are the title, date, school(s) involved on the event, and any details listed.
 */

public class Event extends RealmObject implements Serializable {

    /**
     * Id of an event/
     */
    private String mId;

    /**
     * The title of an event.
     */
    private String mEventTitle;

    private String mYear;

    private String mMonth;

    private String mDay;

    /**
     * The school or or edu.uwp.kusd.schools involved in the event.
     */
    private String mSchool;

    /**
     * The details of the event - if any.
     */
    private String mDetails;

    public Event() {
    }

    /**
     * Constructs a new event.
     *
     * @param eventTitle the title of an event
     * @param school     the school or edu.uwp.kusd.schools involed in an event
     * @param details    the details of an event
     */
    public Event(String id, String eventTitle, String school, String details, String year, String month, String day) {
        this.mId = id;
        this.mEventTitle = eventTitle;
        this.mSchool = school;
        this.mDetails = details;
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    public String getId() {
        return mId;
    }

    /**
     * Gets the title of an event.
     *
     * @return the title of an event.
     */
    String getEventTitle() {
        return mEventTitle;
    }

    /**
     * Gets the school(s) involved in an event.
     *
     * @return school(s) involed in an event.
     */
    String getSchool() {
        return mSchool;
    }

    /**
     * Gets details for an event.
     *
     * @return details for an event.
     */
    String getDetails() {
        return mDetails;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setEventTitle(String eventTitle) {
        mEventTitle = eventTitle;
    }

    public void setSchool(String school) {
        mSchool = school;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

}

