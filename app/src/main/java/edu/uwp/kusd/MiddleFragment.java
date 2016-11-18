package edu.uwp.kusd;

/**
 * Created by Liz on 10/17/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParserException;

import edu.uwp.kusd.network.VolleySingleton;


/**
 * Created by Liz on 10/17/2016.
 */

// In each fragment all requested schools are displayed with the parsed information
public class MiddleFragment extends Fragment {

    //A list of all schools parsed from the XML.
    public List<School> tSchools;

    private static final String SCHOOL_LIST_URL = "http://www.kusd.edu/xml-schools";

    private RecyclerView rv;

    public RVAdapter adapter;

    /**
     * Create the view for the fragment, request the XML data from KUSD, and parse the items.
     *
     * @param inflater           layout inflater for the fragment
     * @param container          a container for the fragment
     * @param savedInstanceState saved bundle of data from a previous state if any
     * @return a view for the fragment
     */


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mid_school_frag, container, false);
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Request XML from KUSD
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCHOOL_LIST_URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                //Create a new XML parser
                SchoolXmlParser schoolXmlParser = new SchoolXmlParser(response);

                rv = (RecyclerView) rootView.findViewById(R.id.rvM);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setHasFixedSize(true);
                rv.setItemViewCacheSize(24);
                rv.setDrawingCacheEnabled(true);
                rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                try {
                    //Parse the schools into a list
                    tSchools = schoolXmlParser.parseNodes(1);
                    adapter = new RVAdapter(tSchools, getActivity());

                    rv.setAdapter(adapter);




                } catch (XmlPullParserException | IOException | ParseException e) {
                    e.printStackTrace();
                }
                schoolXmlParser.closeXmlSteam();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);


        return rootView;

    }

    //converts image URL into bitmap image
    public Bitmap getBitmapFromURL(String src) {
        Bitmap myBitmap = null;

        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            // Log exception
        }
        return myBitmap;
    }
}