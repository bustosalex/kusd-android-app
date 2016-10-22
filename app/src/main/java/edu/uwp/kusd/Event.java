// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd;

import java.io.Serializable;

/**
 * A class to encapsulate event data.
 * Relevant fields are the title, date, school(s) involved on the event, and any details listed.
 */

class Event implements Comparable<Event>, Serializable {

    /**
     * The title of an event.
     */
    private String mEventTitle;

    /**
     * The date or dates of an event.
     */
    private EventDate mDate;

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

    /**
     * Constructs a new event.
     *
     * @param eventTitle the title of an event
     * @param date the date of an event
     * @param school the school or schools involed in an event
     * @param details the details of an event
     */
    Event(String eventTitle, EventDate date, String school, String details, String fullDateInfo) {
        this.mEventTitle = eventTitle;
        this.mDate = date;
        this.mSchool = school;
        this.mDetails = details;
        this.mFullDateInfo = fullDateInfo;
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
    EventDate getDate() {
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
        return this.getDate().getDay() - o.getDate().getDay();
    }
}

