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
     * Id of an event
     */
    private String mId;

    /**
     * The title of an event
     */
    private String mEventTitle;

    /**
     * The year of an event
     */
    private String mYear;

    /**
     * The month of an event
     */
    private String mMonth;

    /**
     * The day of an event
     */
    private String mDay;

    /**
     * The school or or schools involved in the event.
     */
    private String mSchool;

    /**
     * The details of the event - if any.
     */
    private String mDetails;

    /**
     * Default constructor for an event
     */
    public Event() {
    }

    /**
     * Constructs a new event.
     *
     * @param eventTitle the title of an event
     * @param school     the school or schools involed in an event
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

    /**
     * Gets the id of an event
     *
     * @return the id of an event
     */
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

    /**
     * Sets the id of an event
     *
     * @param id the id of an event
     */
    public void setId(String id) {
        mId = id;
    }

    /**
     * Sets the event title of an event
     *
     * @param eventTitle the title of an event
     */
    public void setEventTitle(String eventTitle) {
        mEventTitle = eventTitle;
    }

    /**
     * Sets the school of an event
     *
     * @param school the school of an event
     */
    public void setSchool(String school) {
        mSchool = school;
    }

    /**
     * Sets the details of an event
     *
     * @param details the details of an event
     */
    public void setDetails(String details) {
        mDetails = details;
    }

    /**
     * Gets the year of an event
     *
     * @return the year of an event
     */
    public String getYear() {
        return mYear;
    }

    /**
     * Sets the year of an event
     *
     * @param year the year of an event
     */
    public void setYear(String year) {
        mYear = year;
    }

    /**
     * Gets the month of an event
     *
     * @return the month of an event
     */
    public String getMonth() {
        return mMonth;
    }

    /**
     * Sets the month of an event
     *
     * @param month the month of an event
     */
    public void setMonth(String month) {
        mMonth = month;
    }

    /**
     * Gets the day of an event
     *
     * @return the event of an event
     */
    public String getDay() {
        return mDay;
    }

    /**
     * Sets the day of an event
     *
     * @param day the day of and event
     */
    public void setDay(String day) {
        mDay = day;
    }

}

