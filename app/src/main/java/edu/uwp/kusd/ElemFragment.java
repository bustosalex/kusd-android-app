package edu.uwp.kusd;

import android.content.Context;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;

import java.text.ParseException;

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
 * Created by Liz on 10/5/2016.
 */

// In each fragment all requested schools are displayed with the parsed information
public class ElemFragment extends Fragment {


    //A list of all schools parsed from the XML.
    public List<School> tSchools;


    private static final String SCHOOL_LIST_URL = "http://www.kusd.edu/xml-schools";


    private RecyclerView rv;

    public RVAdapter adapter;

    public Context context;

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
        final View rootView = inflater.inflate(R.layout.elem_school_frag, container, false);


        //Request XML from KUSD
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCHOOL_LIST_URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                //Create a new XML parser
                SchoolXmlParser schoolXmlParser = new SchoolXmlParser(response);

                //set all the properties for the recyclerview
                rv = (RecyclerView) rootView.findViewById(R.id.rvE);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setHasFixedSize(true);
                rv.setItemViewCacheSize(24); //maximum amount of images for the schools
                rv.setDrawingCacheEnabled(true);
                rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


                try {
                    //Parse the schools into a list
                    tSchools = schoolXmlParser.parseNodes(0);

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


}

