package edu.uwp.kusd;

import java.io.Serializable;

/**
 * A class to encapsulate event dates.
 */
public class EventDate implements Serializable {

    /**
     * The year an event is in.
     */
    private int mYear;

    /**
     * The month and event is in.
     */
    private int mMonth;

    /**
     * The day of an event.
     */
    private int mDay;

    /**
     * Constructs an EventDate object with year, month, and day parameters.
     *
     * @param year the year of an event
     * @param month the month of an event
     * @param day the day of an event
     */
    public EventDate(int year, int month, int day) {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    /**
     * Gets the year of an EventDate.
     *
     * @return the year of an EventDate
     */
    public int getYear() {
        return mYear;
    }

    /**
     * Gets the month of an EventDate.
     *
     * @return the month of an EventDate
     */
    public int getMonth() {
        return mMonth;
    }

    /**
     * Gets the day of an EventDate.
     *
     * @return the day of an EventDate
     */
    public int getDay() {
        return mDay;
    }

    /**
     * Determines if two EventDates are equal. In this case if they have the same month and year,
     * they're equal.
     *
     * @param o an EventDate
     * @return if an EventDate is equal to another
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDate eventDate = (EventDate) o;
        if (mYear != eventDate.mYear) return false;
        if (mMonth != eventDate.mMonth) return false;
        return mDay == eventDate.mDay;
    }

    /**
     * Implementation of hashCode for an EventDate.
     *
     * @return has value for an EventDate
     */
    @Override
    public int hashCode() {
        int result = mYear;
        result = 31 * result + mMonth;
        result = 31 * result + mDay;
        return result;
    }
}
