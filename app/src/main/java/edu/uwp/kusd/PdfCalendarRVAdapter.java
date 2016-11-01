// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

/**
 * An adapter for the PdfCalendar RecyclerView
 */
public class PdfCalendarRVAdapter extends RecyclerView.Adapter<PdfCalendarRVAdapter.PdfCalendarViewHolder> {

    /**
     * A list of PdfCalendars.
     */
    private List<PdfCalendar> pdfCalendars;

    /**
     * The context.
     */
    private Context context;

    /**
     * Constructs an adapter for the PdfCalendar RecyclerView.
     *
     * @param pdfCalendars a list of PdfCalendars
     * @param context the context
     */
    public PdfCalendarRVAdapter(List<PdfCalendar> pdfCalendars, Context context) {
        this.pdfCalendars = pdfCalendars;
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
         * TextView for the file description.
         */
        private TextView fileDescriptionTextView;

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
            fileDescriptionTextView = (TextView) pdfCalendarView.findViewById(R.id.pdf_description);
        }
    }

    /**
     * Binds the PdfCalendar data to a view.
     *
     * @param pdfCalendarViewHolder a PdfCalendarViewHolder
     * @param position the position to bind at
     */
    @Override
    public void onBindViewHolder(final PdfCalendarViewHolder pdfCalendarViewHolder, final int position) {
        pdfCalendarViewHolder.fileTitleTextView.setText(pdfCalendars.get(position).getFileTitle());
        pdfCalendarViewHolder.fileDescriptionTextView.setText(pdfCalendars.get(position).getFileDescription());

        //onClick listener for each item in the list
        pdfCalendarViewHolder.mClickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = PdfReaderActivity.newIntent(context, pdfCalendars.get(pdfCalendarViewHolder.getAdapterPosition()).getFileName(), "Calendars");
                context.startActivity(i);
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
        return pdfCalendars.size();
    }
}
