// TODO: Implement callback methods

package edu.uwp.kusd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for fragment that displays the list of PdfCalendars.
 */
public class PdfCalendarsFragment extends Fragment {

    /**
     * A RecyclerView to display the list of Pdfs.
     */
    private RecyclerView recyclerView;

    /**
     * An array of file titles.
     */
    private String[] fileTitles;

    /**
     * An array of file descriptions.
     */
    private String[] fileDescriptions;

    /**
     * An array of file names.
     */
    private String[] fileNames;

    /**
     * A list of PdfCalendars.
     */
    private List<PdfCalendar> pdfCalendarList;

    /**
     * A RecyclerView adapter for PdfCalendars.
     */
    private PdfCalendarRVAdapter adapter;

    /**
     * Creates the view for the fragment using a RecyclerView.
     *
     * @param inflater a view inflater for the fragment
     * @param container the containter for the fragment
     * @param savedInstanceState the saved state of a previous version of the fragment - if any
     * @return a View for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pdfcalendars, container, false);

        fileTitles = getResources().getStringArray(R.array.fileTitles);
        fileDescriptions = getResources().getStringArray(R.array.fileDescriptions);
        fileNames = getResources().getStringArray(R.array.fileTitles);

        pdfCalendarList = new ArrayList<>();
        for (int i = 0; i < fileTitles.length; i++) {
            PdfCalendar pdfCalendar = new PdfCalendar(fileTitles[i], fileDescriptions[i], fileNames[i]);
            pdfCalendarList.add(pdfCalendar);
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.pdfCalendarRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PdfCalendarRVAdapter(pdfCalendarList, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}