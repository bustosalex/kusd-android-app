package edu.uwp.kusd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by Dakota on 11/5/2016.
 */

public class EventsAdapter extends RealmRecyclerViewAdapter<Event> {

    private final Context mContext;
    private Calendar c = Calendar.getInstance();
    private int currentYear = c.get(Calendar.YEAR);
    private int currentMonth = c.get(Calendar.MONTH) + 1;
    private int currentDay = c.get(Calendar.DAY_OF_MONTH);
    private Event mEvent;

    public EventsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_cardview_row_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        Event event = getItem(position);
        EventViewHolder holder = (EventViewHolder) viewHolder;
        Event temp = new Event(event.getId(), event.getEventTitle(), event.getDate(), event.getSchool(), event.getDetails(), " ", event.getPDF(), event.getYear(), event.getMonth(), event.getDay());
        holder.bindEvent(temp);


        /*
        holder.eventNameTextView.setText(event.getEventTitle().replace("&#039;", "'"));
        holder.dayTextView.setText(Integer.toString(event.getDayFromDate()));
        holder.monthNameTextView.setText(new DateFormatSymbols().getMonths()[event.getMonthFromDate() - 1].toUpperCase());
        */
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

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
         * TextView for the day of an event.
         */
        private TextView dayTextView;

        /**
         * TextView for the name of the month for an event.
         */
        private TextView monthNameTextView;

        private ImageButton pdfArrow;

        private LinearLayout mClickableEvent;

        private Event mEvent;

        /**
         * Constructs an EventViewHolder containing a card view, event name, school name, and day.
         *
         * @param eventView a view for an event
         */
        public EventViewHolder(View eventView) {
            super(eventView);
            cardView = (CardView) eventView.findViewById(R.id.cardView);
            pdfArrow = (ImageButton) eventView.findViewById(R.id.pdf_arrow);
            eventNameTextView = (TextView) eventView.findViewById(R.id.event_name_text_view);
            dayTextView = (TextView) eventView.findViewById(R.id.day_text_view);
            monthNameTextView = (TextView) eventView.findViewById(R.id.month_name_text_view);
            mClickableEvent = (LinearLayout) eventView.findViewById(R.id.clickable_event);
        }

        public void bindEvent(Event event) {
            mEvent = event;
            eventNameTextView.setText(event.getEventTitle().replace("&#039;", "'"));
            dayTextView.setText(Integer.toString(event.getDayFromDate()));
            monthNameTextView.setText(new DateFormatSymbols().getMonths()[event.getMonthFromDate() - 1].toUpperCase());
        }
    }
}
