package edu.uwp.kusd.calendar;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;

import edu.uwp.kusd.R;
import edu.uwp.kusd.realm.RealmRecyclerViewAdapter;

/**
 * Created by Dakota on 11/5/2016.
 */

public class EventsAdapter extends RealmRecyclerViewAdapter<Event> {

    private final Context mContext;


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
        Event temp = new Event(event.getId(), event.getEventTitle(), event.getSchool(), event.getDetails(), event.getYear(), event.getMonth(), event.getDay());
        holder.bindEvent(temp);
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

        /**
         * Constructs an EventViewHolder containing a card view, event name, school name, and day.
         *
         * @param eventView a view for an event
         */
        public EventViewHolder(View eventView) {
            super(eventView);
            cardView = (CardView) eventView.findViewById(R.id.cardView);
            eventNameTextView = (TextView) eventView.findViewById(R.id.event_name_text_view);
            dayTextView = (TextView) eventView.findViewById(R.id.day_text_view);
            monthNameTextView = (TextView) eventView.findViewById(R.id.month_name_text_view);
        }

        public void bindEvent(Event event) {
            eventNameTextView.setText(event.getEventTitle().replace("&#039;", "'").replace("&#38;", "&"));
            int day = Integer.parseInt(event.getDay());
            dayTextView.setText(Integer.toString(day));
            monthNameTextView.setText(new DateFormatSymbols().getMonths()[Integer.parseInt(event.getMonth()) - 1].toUpperCase());
        }
    }
}
