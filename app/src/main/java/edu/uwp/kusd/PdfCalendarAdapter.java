// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * An adapter for the PdfCalendar RecyclerView
 */
public class PdfCalendarAdapter extends RealmRecyclerViewAdapter<PdfCalendar> {

    /**
     * The context.
     */
    private Context context;

    /**
     * Constructs an adapter for the PdfCalendar RecyclerView.
     *
     * @param context the context
     */
    public PdfCalendarAdapter(Context context) {
        this.context = context;
    }

    /**
     * A ViewHolder for the PdfCalendar RecyclerView.
     */
    public class PdfCalendarViewHolder extends RecyclerView.ViewHolder {

        /**
         * A CardView to house the file title TextView and the file description TextView.
         */
        private CardView cardView;

        /**
         * TextView for the file title.
         */
        private TextView fileTitleTextView;

        /**
         * RelativeLayout for grouping clickable items.
         */
        private RelativeLayout mClickableLayout;

        /**
         * Constructs a ViewHolder for the PdfCalendar RecyclerView.
         *
         * @param pdfCalendarView a pdfCalendar view
         */
        public PdfCalendarViewHolder(View pdfCalendarView) {
            super(pdfCalendarView);
            cardView = (CardView) pdfCalendarView.findViewById(R.id.cardView);
            mClickableLayout = (RelativeLayout) pdfCalendarView.findViewById(R.id.clickable_card);
            fileTitleTextView = (TextView) pdfCalendarView.findViewById(R.id.pdf_title);
        }
    }

    /**
     * Binds the PdfCalendar data to a view.
     *
     * @param viewHolder a PdfCalendarViewHolder
     * @param position the position to bind at
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        final PdfCalendar pdfCalendar = getItem(position);
        final PdfCalendarViewHolder holder = (PdfCalendarViewHolder) viewHolder;

        holder.fileTitleTextView.setText(pdfCalendar.getFileTitle());
        //onClick listener for each item in the list
        holder.mClickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?&embedded=true&url=" + pdfCalendar.getFileURL()), "text/html");
                context.startActivity(intent);
                Toast.makeText(context, "Press back to return to KUSD", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Inflates the layout for a card on ViewHolder creation.
     *
     * @param parent the parent ViewGroup
     * @param viewType the ViewType
     * @return a PdfCalendarViewHolder
     */
    @Override
    public PdfCalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdfcalendar_cardview_row_item, parent, false);
        return new PdfCalendarViewHolder(view);
    }

    /**
     * Gets the size of the list of pdfCalendars.
     *
     * @return the size of the list of pdfCalendars.
     */
    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }
}
