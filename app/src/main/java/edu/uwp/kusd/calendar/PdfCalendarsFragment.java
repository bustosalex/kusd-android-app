// TODO: Implement callback methods

package edu.uwp.kusd.calendar;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;

import edu.uwp.kusd.R;
import edu.uwp.kusd.network.VolleyApplication;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.realm.RealmPdfCalendarAdapter;
import edu.uwp.kusd.realm.RefreshObject;
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
     * A RecyclerView mAdapter for PdfCalendars.
     */
    private PdfCalendarAdapter mAdapter;

    /**
     * The request queue for making HTTP requests
     */
    private RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    /**
     * The url for the pdf calendar xml
     */
    private static final String PDF_CALENDAR_URL = "http://www.kusd.edu/xml-pdf-calendars";

    /**
     * An instance of realm to access the DB
     */
    private Realm mRealm;

    /**
     * A variable to signify if a data refresh is needed
     */
    private boolean mRefreshNeeded = false;

    /**
     * A text view to display if there are no pdfs
     */
    private TextView mNoPdfs;

    /**
     * Creates the view for the fragment using a RecyclerView.
     *
     * @param inflater           a view inflater for the fragment
     * @param container          the containter for the fragment
     * @param savedInstanceState the saved state of a previous version of the fragment - if any
     * @return a View for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pdfcalendars, container, false);

        //Request XML from KUSD if there is no need to refresh the data
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

                        //parse the xml
                        parser.parseNodes();
                        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.pdfCalendarRecyclerView);

                        //Setup the recyclerview with the data
                        RealmQuery<PdfCalendar> query = mRealm.where(PdfCalendar.class);
                        RealmResults<PdfCalendar> results = query.findAll();
                        setupRecyclerView();
                        setRealmAdapter(results);

                    } catch (XmlPullParserException | IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    displayError(rootView);
                }
            });
            if (isNetworkAvailable()) {
                requestQueue.add(stringRequest);
            } else {
                displayError(rootView);
            }
        }
        return rootView;
    }

    /**
     * Checks for an active network connection
     *
     * @return a boolean corresponding to if the device has an active network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Sets up views to let the user know there is no network connection
     *
     * @param parent the parent view that contains the views to setup
     */
    private void displayError(View parent) {
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.pdfCalendarRecyclerView);
        mRecyclerView.setVisibility(View.GONE);
        mNoPdfs = (TextView) parent.findViewById(R.id.no_pdfs);
        mNoPdfs.setVisibility(View.VISIBLE);
    }

    /**
     * Handles actions to perform when the fragment is destroyed. Close and compact the realm
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        Realm.compactRealm(mRealm.getConfiguration());
    }

    /**
     * Empties out the realm in preparation of new data
     */
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

    /**
     * Handles actions to perform when the fragment is created. Sets up the realm and checks if new data is needed
     *
     * @param savedInstanceState the saved state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRealm = Realm.getDefaultInstance();
        RealmQuery<RefreshObject> query = mRealm.where(RefreshObject.class);
        query.equalTo("mClassTag", "Pdf Calendars");
        RealmResults<RefreshObject> results = query.findAll();
        if (results.size() > 0) {
            if (System.currentTimeMillis() - results.get(0).getRefreshTime() > 7 * 24 * 60 * 60 * 1000) {
                mRefreshNeeded = true;
            }
        }
    }

    /**
     * Sets up the adapter for use with realm
     *
     * @param pdfCalendars the data set for the adapter
     */
    public void setRealmAdapter(RealmResults<PdfCalendar> pdfCalendars) {
        RealmPdfCalendarAdapter realmAdapter = new RealmPdfCalendarAdapter(VolleyApplication.getAppContext(), pdfCalendars);
        mAdapter.setRealmAdapter(realmAdapter);
        realmAdapter.notifyDataSetChanged();
    }

    /**
     * Sets up the recyclerview
     */
    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PdfCalendarAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Handles actions to perform when the fragment is stopped. Closes pending requests in the queue
     */
    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(PDF_CALENDAR_URL);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final View v = this.getView();
        switch (item.getItemId()) {
            case R.id.refresh_button:
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PDF_CALENDAR_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PdfCalendarXmlParser parser = new PdfCalendarXmlParser(response);
                        try {
                            refreshRealm();

                            //parse the xml
                            parser.parseNodes();
                            mRecyclerView = (RecyclerView) v.findViewById(R.id.pdfCalendarRecyclerView);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            if (mNoPdfs != null) {
                                mNoPdfs.setVisibility(View.GONE);
                            }

                            //Setup the recyclerview with the data
                            RealmQuery<PdfCalendar> query = mRealm.where(PdfCalendar.class);
                            RealmResults<PdfCalendar> results = query.findAll();
                            setupRecyclerView();
                            setRealmAdapter(results);

                        } catch (XmlPullParserException | IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        displayError(v);
                    }
                });
                if (isNetworkAvailable()) {
                    requestQueue.add(stringRequest);
                } else {
                    displayError(v);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}