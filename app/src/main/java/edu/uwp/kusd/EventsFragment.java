// TODO: 10/3/2016 - Implement callback methods

package edu.uwp.kusd;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.HashMap;
import java.util.List;

import edu.uwp.kusd.network.CacheStringRequest;
import edu.uwp.kusd.network.VolleyApplication;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.realm.RealmController;
import edu.uwp.kusd.xmlParser.EventXmlParser;
import io.realm.Realm;
import io.realm.RealmObject;
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
     *
     */
    private static final String PARSED_EVENTS = "parsed events";

    /**
     * A list of all events parsed from the XML.
     */
    private List<Event> mEventList;

    /**
     * A RecyclerView to show the events in.
     */
    private RecyclerView recyclerView;

    /**
     * An EventDate to use as a filter to show only the events in a desired month year
     */
    private String filterDate;

    /**
     * A HashMap with EventDates and keys and lists of events as values.
     */
    private HashMap<String, List<Event>> mEventsByMonth;

    /**
     *
     */
    RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    Realm mRealm;

    EventsAdapter mAdapter;
    RecyclerView mRecyclerView;
    private Spinner mMonthSelectorSpinner;
    private TextView mShowingEventsTextView;
    private static ProgressDialog mProgressDialog;
    private boolean mRefreshNeeded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        RealmQuery<RefreshObject> query = mRealm.where(RefreshObject.class);
        query.equalTo("mClassTag", "Events");
        RealmResults<RefreshObject> results = query.findAll();
        if (results.size() > 0) {
            if (System.currentTimeMillis() - results.get(0).getRefreshTime() > 1 * 1 * 1000) {
                mRefreshNeeded = true;
            }
        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        RealmQuery<RefreshObject> query = mRealm.where(RefreshObject.class);
        query.equalTo("mClassTag", "Events");
        RealmResults<RefreshObject> results = query.findAll();
        if (results.size() > 0) {
            if (System.currentTimeMillis() - results.get(0).getRefreshTime() > 1 * 1 * 1000) {
                mRefreshNeeded = true;
            }
        }
    }*/

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

        //Request XML from KUSD
        //TODO: Handle request errors
        //TODO: Close the queue
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        filterDate = "" + currentYear + "-" + currentMonth;

        if (!mRealm.isEmpty() && !mRefreshNeeded) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
            setupRecyclerView();
            mShowingEventsTextView = (TextView) rootView.findViewById(R.id.showing_events_text_view);
            mShowingEventsTextView.setText(R.string.showing_events);
            mMonthSelectorSpinner = (Spinner) rootView.findViewById(R.id.event_year_spinner);
            setupSpinner();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EVENT_CALENDAR_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Create a new XML parser
                    // String urlRegex = "<a href=\"https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&/=]*)\">";
                    //response = response.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br/>", "").replaceAll("<br />", "").replaceAll("</a>", "").replaceAll(urlRegex, "").replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</li>", "").replaceAll("</ul>", "").replaceAll("<strong>", "").replaceAll("</strong>", "");

                    refreshRealm();

                    Log.i("CalendarNetwork", "Events made a network request");

                    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventsRecyclerView);
                    mShowingEventsTextView = (TextView) rootView.findViewById(R.id.showing_events_text_view);
                    mShowingEventsTextView.setText(R.string.showing_events);
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
                    error.printStackTrace();
                }
            });
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setTitle("Loading events");
            mProgressDialog.setMessage("Please wait while events are fetched");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            requestQueue.add(stringRequest);
        }
        return rootView;
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        Realm.compactRealm(mRealm.getConfiguration());
    }

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

    public static ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    private void setupSpinner() {
        List<String> years = new ArrayList<>();
        String[] tempYearMonths;
        for (String yearMonth : getYearMonths()) {
            tempYearMonths = yearMonth.split("-");
            if (!years.contains(tempYearMonths[0])) {
                years.add(new DateFormatSymbols().getMonths()[Integer.parseInt(tempYearMonths[1]) - 1] + " " + tempYearMonths[0]);
            }
        }

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
            if (isAdded()) {
                ArrayAdapter<String> yearSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, years);
                mMonthSelectorSpinner.setAdapter(yearSpinnerAdapter);
                mMonthSelectorSpinner.setPrompt("Choose a month");
                String[] currentYearMonth = filterDate.split("-");
                for (int i = 0; i < mMonthSelectorSpinner.getCount(); i++) {
                    if (mMonthSelectorSpinner.getItemAtPosition(i).toString().equals(new DateFormatSymbols().getMonths()[Integer.parseInt(currentYearMonth[1]) - 1] + " " + currentYearMonth[0])) {
                        mMonthSelectorSpinner.setSelection(i);
                    }
                }

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
            } else {
                Log.i("EVENTSFRAGMENT1", "CONTEXT IS NULL");
            }
        }
    }

    public void setRealmAdapter(RealmResults<Event> events) {
        RealmEventsAdapter realmAdapter = new RealmEventsAdapter(VolleyApplication.getAppContext(), events);
        mAdapter.setRealmAdapter(realmAdapter);
        realmAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EventsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(EVENT_CALENDAR_URL);
        }
    }

    private class ParserTask extends AsyncTask<String, Void, Void> {

        private EventXmlParser calendarXmlParser;

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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupSpinner();
            EventsFragment.getProgressDialog().dismiss();
        }
    }
}