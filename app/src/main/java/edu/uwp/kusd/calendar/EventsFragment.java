package edu.uwp.kusd.calendar;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.uwp.kusd.R;
import edu.uwp.kusd.network.VolleyApplication;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.realm.RealmEventsAdapter;
import edu.uwp.kusd.realm.RefreshObject;
import edu.uwp.kusd.xmlParser.EventXmlParser;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Class for a fragment to display the list of events.
 */
public class EventsFragment extends Fragment {

    /**
     * A constant for the URL where the event XML is located.
     */
    private static final String EVENT_CALENDAR_URL = "http://www.kusd.edu/xml-calendar";

    /**
     * An EventDate to use as a filter to show only the events in a desired month year
     */
    private String filterDate;


    /**
     * The request queue for placing HTTP requests into
     */
    private RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    /**
     * A realm for database access
     */
    private Realm mRealm;

    /**
     * The adapter for the events fragment
     */
    private EventsAdapter mAdapter;

    /**
     * The recyclerview to display the list of events
     */
    private RecyclerView mRecyclerView;

    /**
     * A spinner to use as a filter for events
     */
    private Spinner mMonthSelectorSpinner;

    /**
     * A textview for the label of the spinner
     */
    private TextView mShowingEventsTextView;

    /**
     * A progress dialog to indicate loading
     */
    private static ProgressDialog mProgressDialog;

    /**
     * A boolean signifying if a data refresh is needed
     */
    private boolean mRefreshNeeded = false;

    /**
     * A text view to show when there is no events
     */
    private TextView mNoEvents;

    /**
     * Handles actions to perform when the fragment is created. Sets up the realm and checks if new data is needed
     *
     * @param savedInstanceState the saved state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Setup realm
        mRealm = Realm.getDefaultInstance();

        //Check for data refresh
        RealmQuery<RefreshObject> query = mRealm.where(RefreshObject.class);
        query.equalTo("mClassTag", "Events");
        RealmResults<RefreshObject> results = query.findAll();
        if (results.size() > 0) {
            if (System.currentTimeMillis() - results.get(0).getRefreshTime() > 7 * 24 * 60 * 60 * 1000) {
                mRefreshNeeded = true;
            }
        }
    }

    /**
     * Create the view for the fragment, request the XML data from KUSD, and parse the events.
     *
     * @param inflater           layout inflater for the fragment
     * @param container          a container for the fragment
     * @param savedInstanceState saved bundle of data from a previous state if any
     * @return a view for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragments view
        final View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        mProgressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        mShowingEventsTextView = (TextView) rootView.findViewById(R.id.showing_events_text_view);
        mShowingEventsTextView.setText(R.string.showing_events);

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        filterDate = "" + currentYear + "-" + currentMonth;

        //Request XML from KUSD if there is no need to refresh the data
        if (!mRealm.isEmpty() && !mRefreshNeeded) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
            setupRecyclerView();
            mMonthSelectorSpinner = (Spinner) rootView.findViewById(R.id.event_year_spinner);
            setupSpinner();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EVENT_CALENDAR_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Empty the realm
                    refreshRealm();

                    //Setup the spinner and parse the XML in an AsyncTask
                    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
                    mMonthSelectorSpinner = (Spinner) rootView.findViewById(R.id.event_year_spinner);
                    if (isAdded()) {
                        ArrayAdapter<String> tempArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, new String[]{"Choose a month"});
                        mMonthSelectorSpinner.setAdapter(tempArrayAdapter);
                    }
                    setupRecyclerView();
                    String[] param = new String[]{response};
                    setupSpinner();
                    new ParserTask().execute(param);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Display error if bad response
                    error.printStackTrace();
                    displayError(rootView);
                }
            });
            //Display error if no network connection
            if (isNetworkAvailable()) {
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setTitle("Loading events");
                mProgressDialog.setMessage("Please wait while events are fetched");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
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
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.eventsRecyclerView);
        mRecyclerView.setVisibility(View.GONE);
        mNoEvents = (TextView) parent.findViewById(R.id.no_events);
        mNoEvents.setVisibility(View.VISIBLE);
    }

    /**
     * Empties out the realm in preparation of new data
     */
    private void refreshRealm() {
        mRealm.beginTransaction();
        RealmQuery<RefreshObject> refreshQuery = mRealm.where(RefreshObject.class);
        refreshQuery.equalTo("mClassTag", "Events");
        RealmResults<RefreshObject> refreshResults = refreshQuery.findAll();
        if (refreshResults.size() > 0) {
            refreshResults.deleteAllFromRealm();
        }
        mRealm.commitTransaction();

        mRealm.beginTransaction();
        RealmQuery<Event> eventQuery = mRealm.where(Event.class);
        RealmResults<Event> eventResults = eventQuery.findAll();
        if (eventResults.size() > 0) {
            eventResults.deleteAllFromRealm();
        }
        mRealm.commitTransaction();


        mRealm.beginTransaction();
        RefreshObject refresh = mRealm.createObject(RefreshObject.class);
        refresh.setClassTag("Events");
        refresh.setRefreshTime(System.currentTimeMillis());
        mRealm.commitTransaction();
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
     * Gets all of the possible year-months available from the data set for use in setting up the
     * filter options
     *
     * @return a list of year months as strings
     */
    private List<String> getYearMonths() {
        RealmQuery<Event> query = mRealm.where(Event.class);
        RealmResults<Event> results = query.findAll();
        List<String> yearMonths = new ArrayList<>();
        for (Event event : results) {
            String yearMonth = event.getYear() + "-" + event.getMonth();
            if (!yearMonths.contains(yearMonth)) {
                yearMonths.add(yearMonth);
            }
        }
        return yearMonths;
    }

    /**
     * Gets the active progress dialog
     *
     * @return the progressdialog
     */
    public static ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    /**
     * Sets up the spinner - the filter
     */
    private void setupSpinner() {
        //Adds the year months into a new list in proper format for display on screen
        List<String> years = new ArrayList<>();
        String[] tempYearMonths;
        for (String yearMonth : getYearMonths()) {
            tempYearMonths = yearMonth.split("-");
            if (!years.contains(tempYearMonths[0])) {
                years.add(new DateFormatSymbols().getMonths()[Integer.parseInt(tempYearMonths[1]) - 1] + " " + tempYearMonths[0]);
            }
        }

        //Sort the year months by oldest to newest; for example 2016 March, 2016 April, 2016 May, 2016 June
        Collections.sort(years, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] split1 = o1.split(" ");
                String[] split2 = o2.split(" ");
                try {
                    Date date1 = new SimpleDateFormat("MMM").parse(split1[0]);
                    Date date2 = new SimpleDateFormat("MMM").parse(split2[0]);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    int monthNum1 = c.get(Calendar.MONTH) + 1;
                    c.setTime(date2);
                    int monthNum2 = c.get(Calendar.MONTH) + 1;
                    if (monthNum1 - monthNum2 > 0 && Integer.parseInt(split1[1]) - Integer.parseInt(split2[1]) > 0) {
                        return 1;
                    } else if (monthNum1 - monthNum2 == 0 && Integer.parseInt(split1[1]) - Integer.parseInt(split2[1]) == 0) {
                        return 0;
                    } else {
                        return -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        while (true) {
            //Make sure the fragment is added to an activity
            if (isAdded()) {
                //Add the data set to the spinner
                ArrayAdapter<String> yearSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, years);
                mMonthSelectorSpinner.setAdapter(yearSpinnerAdapter);
                mMonthSelectorSpinner.setPrompt("Choose a month");

                //Set the current filter setting to be the current month
                String[] currentYearMonth = filterDate.split("-");
                for (int i = 0; i < mMonthSelectorSpinner.getCount(); i++) {
                    if (mMonthSelectorSpinner.getItemAtPosition(i).toString().equals(new DateFormatSymbols().getMonths()[Integer.parseInt(currentYearMonth[1]) - 1] + " " + currentYearMonth[0])) {
                        mMonthSelectorSpinner.setSelection(i);
                    }
                }

                //Set the spinner action to change the data set when a new filter option is selected
                mMonthSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            RealmQuery<Event> query = mRealm.where(Event.class);
                            String[] temp = mMonthSelectorSpinner.getItemAtPosition(position).toString().split(" ");
                            Date tempDate = new SimpleDateFormat("MMM").parse(temp[0]);
                            Calendar c = Calendar.getInstance();
                            c.setTime(tempDate);
                            String month = Integer.toString(c.get(Calendar.MONTH) + 1);
                            if (Integer.parseInt(month) < 10) {
                                month = 0 + month;
                            }
                            query.equalTo("mYear", temp[1]);
                            query.equalTo("mMonth", month);
                            RealmResults<Event> results = query.findAll();
                            results = results.sort("mDay", Sort.ASCENDING);
                            RealmQuery<Event> query1 = mRealm.where(Event.class);
                            RealmResults<Event> f = query1.findAll();
                            setRealmAdapter(results);
                            mAdapter.notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            }
        }
    }

    /**
     * Sets up the adapter for use with realm
     *
     * @param events the data set for the adapter
     */
    public void setRealmAdapter(RealmResults<Event> events) {
        RealmEventsAdapter realmAdapter = new RealmEventsAdapter(VolleyApplication.getAppContext(), events);
        mAdapter.setRealmAdapter(realmAdapter);
        realmAdapter.notifyDataSetChanged();
    }

    /**
     * Sets up the recyclerview
     */
    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EventsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Handles actions to perform when the fragment is stopped. Closes pending requests in the queue
     */
    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(EVENT_CALENDAR_URL);
        }
    }

    /**
     * An asynctask to put XML parsing on another thread
     */
    private class ParserTask extends AsyncTask<String, Void, Void> {

        /**
         * A parser for events
         */
        private EventXmlParser calendarXmlParser;

        /**
         * Handles actions to perform on the background thread
         *
         * @param params the xml
         * @return void
         */
        @Override
        protected Void doInBackground(String... params) {
            calendarXmlParser = new EventXmlParser(params[0]);
            try {
                calendarXmlParser.parseNodes();
            } catch (XmlPullParserException | IOException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Handles actions to perform once the background task is complete. Set up the spinner
         * and close the loading dialog
         *
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupSpinner();
            EventsFragment.getProgressDialog().dismiss();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                final View v = this.getView();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, EVENT_CALENDAR_URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //Empty the realm
                        refreshRealm();
                        //Setup the spinner and parse the XML in an AsyncTask
                        mRecyclerView = (RecyclerView) v.findViewById(R.id.eventsRecyclerView);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        if (mNoEvents != null) {
                            mNoEvents.setVisibility(View.GONE);
                        }

                        mMonthSelectorSpinner = (Spinner) v.findViewById(R.id.event_year_spinner);
                        if (isAdded()) {
                            ArrayAdapter<String> tempArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, new String[]{"Choose a month"});
                            mMonthSelectorSpinner.setAdapter(tempArrayAdapter);
                        }
                        setupRecyclerView();
                        String[] param = new String[]{response};
                        setupSpinner();
                        new ParserTask().execute(param);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error if bad response
                        error.printStackTrace();
                        displayError(v);
                    }
                });
                //Display error if no network connection
                if (isNetworkAvailable()) {
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setTitle("Loading events");
                    mProgressDialog.setMessage("Please wait while events are fetched");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
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
