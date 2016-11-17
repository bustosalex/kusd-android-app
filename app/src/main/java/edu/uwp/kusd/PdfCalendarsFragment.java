// TODO: Implement callback methods

package edu.uwp.kusd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import edu.uwp.kusd.network.VolleyApplication;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.realm.RealmController;
import edu.uwp.kusd.xmlParser.PdfCalendarXmlParser;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A class for fragment that displays the list of PdfCalendars.
 */
public class PdfCalendarsFragment extends Fragment {

    /**
     * A RecyclerView to display the list of Pdfs.
     */
    private RecyclerView mRecyclerView;

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
     * A RecyclerView mAdapter for PdfCalendars.
     */
    private PdfCalendarAdapter mAdapter;



    RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
    private static final String PDF_CALENDAR_URL = "http://www.kusd.edu/xml-pdf-calendars";
    Realm mRealm;
    private boolean mRefreshNeeded = false;

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
        final View rootView = inflater.inflate(R.layout.fragment_pdfcalendars, container, false);

        if (!mRealm.isEmpty() && !mRefreshNeeded) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.pdfCalendarRecyclerView);
            RealmQuery<PdfCalendar> query = mRealm.where(PdfCalendar.class);
            RealmResults<PdfCalendar> results = query.findAll();
            setupRecyclerView();
            setRealmAdapter(results);
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, PDF_CALENDAR_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    PdfCalendarXmlParser parser = new PdfCalendarXmlParser(response);
                    try {
                        refreshRealm();

                        parser.parseNodes();
                        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.pdfCalendarRecyclerView);

                        RealmQuery<PdfCalendar> query = mRealm.where(PdfCalendar.class);
                        RealmResults<PdfCalendar> results = query.findAll();
                        setupRecyclerView();
                        setRealmAdapter(results);
                        Log.i("CalendarNetwork", "PdfCalendars made a network request");

                    } catch (XmlPullParserException | IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(stringRequest);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        Realm.compactRealm(mRealm.getConfiguration());
    }

    private void refreshRealm() {
        mRealm.beginTransaction();
        RealmQuery<RefreshObject> refreshQuery = mRealm.where(RefreshObject.class);
        refreshQuery.equalTo("mClassTag", "Pdf Calendars");
        RealmResults<RefreshObject> refreshResults = refreshQuery.findAll();
        if (refreshResults.size() > 0) {
            refreshResults.deleteAllFromRealm();
        }
        mRealm.commitTransaction();

        mRealm.beginTransaction();
        RealmQuery<PdfCalendar> pdfQuery = mRealm.where(PdfCalendar.class);
        RealmResults<PdfCalendar> pdfResults = pdfQuery.findAll();
        if (pdfResults.size() > 0) {
            pdfResults.deleteAllFromRealm();
        }
        mRealm.commitTransaction();


        mRealm.beginTransaction();
        RefreshObject refresh = mRealm.createObject(RefreshObject.class);
        refresh.setClassTag("Pdf Calendars");
        refresh.setRefreshTime(System.currentTimeMillis());
        mRealm.commitTransaction();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        RealmQuery<RefreshObject> query = mRealm.where(RefreshObject.class);
        query.equalTo("mClassTag", "Pdf Calendars");
        RealmResults<RefreshObject> results = query.findAll();
        if (results.size() > 0) {
            if (System.currentTimeMillis() - results.get(0).getRefreshTime() > 1 * 1 * 1000) {
                mRefreshNeeded = true;
            }
        }
    }

    public void setRealmAdapter(RealmResults<PdfCalendar> pdfCalendars) {
        RealmPdfCalendarAdapter realmAdapter = new RealmPdfCalendarAdapter(VolleyApplication.getAppContext(), pdfCalendars);
        mAdapter.setRealmAdapter(realmAdapter);
        realmAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PdfCalendarAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }
}