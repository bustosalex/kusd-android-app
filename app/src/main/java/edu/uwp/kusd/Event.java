// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * A class to encapsulate event data.
 * Relevant fields are the title, date, school(s) involved on the event, and any details listed.
 */

public class Event extends RealmObject implements Comparable<Event>, Serializable {

    /**
     * Id of an event/
     */

    private String mId;

    /**
     * The title of an event.
     */
    private String mEventTitle;

    /**
     * The date or dates of an event.
     */
    //private EventDate mDate;
    private String mDate;

    private String mYear;

    private String mMonth;

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
     * String representation of the full date including time.
     */
    private String mFullDateInfo;

    private String mPDF;

    public Event() {

    }


    /**
     * Constructs a new event.
     *
     * @param eventTitle the title of an event
     * @param date the date of an event
     * @param school the school or schools involed in an event
     * @param details the details of an event
     */
    public Event(String id, String eventTitle, String date, String school, String details, String fullDateInfo, String pdf, String year, String month, String day) {
        this.mId = id;
        this.mEventTitle = eventTitle;
        this.mDate = date;
        this.mSchool = school;
        this.mDetails = details;
        this.mFullDateInfo = fullDateInfo;
        this.mPDF = pdf;
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
     *  Gets the date of an event.
     *
     * @return the date of an event.
     */
    String getDate() {
        return mDate;
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
     * Compare method for sorting events by date.
     *
     * @param o an event object
     * @return and int signifying if a date is before, after or equal to another event.
     */
    @Override
    public int compareTo(Event o) {
        return this.getDayFromDate() - o.getDayFromDate();
    }

    public int getYearFromDate() {
        String[] fields = this.mDate.split("-");
        return Integer.parseInt(fields[0]);
    }

    public int getMonthFromDate() {
        String[] fields = this.mDate.split("-");
        return Integer.parseInt(fields[1]);
    }

    public int getDayFromDate() {
        String[] fields = this.mDate.split("-");
        return Integer.parseInt(fields[2]);
    }

    public void setId(String id) {
        mId = id;
    }

    public void setEventTitle(String eventTitle) {
        mEventTitle = eventTitle;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setSchool(String school) {
        mSchool = school;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public void setFullDateInfo(String fullDateInfo) {
        mFullDateInfo = fullDateInfo;
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

    public String getPDF() {
        return mPDF;
    }

    public void setPDF(String PDF) {
        mPDF = PDF;
    }
}

