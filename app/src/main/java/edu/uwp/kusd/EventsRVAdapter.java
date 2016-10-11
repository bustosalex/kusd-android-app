package edu.uwp.kusd;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class for an Adapter for the Event RecyclerView.
 */
public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.EventViewHolder> {

    /**
     * Hashmap of events listed by month-year.
     */
    private HashMap<EventDate, List<Event>> mEventListByDate;

    /**
     * The context.
     */
    private Context context;

    /**
     * An EventDate to choose what month-year to show events for.
     */
    private EventDate mMonthYear;

    /**
     * Constructs and adapter for the Event RecyclerView.
     *
     * @param eventListByDate hashmap of events listed by month-year
     * @param context the context
     * @param monthYear a EventDate to choose what month-year to show events for
     */
    public EventsRVAdapter(HashMap<EventDate, List<Event>> eventListByDate, Context context, EventDate monthYear) {
        this.mEventListByDate = eventListByDate;
        this.context = context;
        this.mMonthYear = monthYear;
    }

    /**
     * An EventViewHolder for the Event RecyclerView.
     */
    public class EventViewHolder extends RecyclerView.ViewHolder {

        /**
         * CardView to contain the event details.
         */
        private CardView cardView;

        /**
         * TextView for the event name.
         */
        private TextView eventNameTextView;

        /**
         * TextView for the school name(s) for an event.
         */
        private TextView schoolTextView;

        /**
         * TextView for the day of an event.
         */
        private TextView dayTextView;

        /**
         * TextView for the name of the month for an event.
         */
        private TextView monthNameTextView;

        /**
         * Constructs an EventViewHolder containing a card view, event name, school name, and day.
         *
         * @param eventView a view for an event
         */
        public EventViewHolder(View eventView) {
            super(eventView);
            cardView = (CardView) eventView.findViewById(R.id.cardView);
            eventNameTextView = (TextView) eventView.findViewById(R.id.event_name_text_view);
            schoolTextView = (TextView) eventView.findViewById(R.id.school_text_view);
            dayTextView = (TextView) eventView.findViewById(R.id.day_text_view);
            monthNameTextView = (TextView) eventView.findViewById(R.id.month_name_text_view);
        }
    }

    /**
     * Binds event data to the EventViewHolder.
     *
     * @param eventViewHolder the view holder to bind data to
     * @param position the postion to bind data at
     */
    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int position) {
        //Sort the events by date
        Collections.sort(mEventListByDate.get(mMonthYear));
        eventViewHolder.eventNameTextView.setText(mEventListByDate.get(mMonthYear).get(position).getEventTitle().replace("&#039;", "'"));
        eventViewHolder.schoolTextView.setText(mEventListByDate.get(mMonthYear).get(position).getSchool());
        eventViewHolder.dayTextView.setText(Integer.toString(mEventListByDate.get(mMonthYear).get(position).getDate().getDay()));
        eventViewHolder.monthNameTextView.setText(new DateFormatSymbols().getMonths()[mEventListByDate.get(mMonthYear).get(position).getDate().getMonth() - 1].toUpperCase());
    }

    /**
     * Handles the creation of a the EventViewHolder.
     *
     * @param parent the parent viewgroup
     * @param viewType the view type
     * @return an EventViewHolder
     */
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_cardview_row_item, parent, false);
        return new EventViewHolder(view);
    }

    /**
     * Gets the number of items in a list of events.
     *
     * @return the number of items in a list of events
     */
    @Override
    public int getItemCount() {
         return mEventListByDate.get(mMonthYear).size();
    }
}
