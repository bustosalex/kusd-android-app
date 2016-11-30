package edu.uwp.kusd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.network.CacheStringRequest;
import edu.uwp.kusd.network.VolleySingleton;

/**
 * Created by Cabz on 11/30/2016.
 */

public class DemoFragment extends Fragment {

    RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();
    private String url = "http://www.kusd.edu/xml-features";
    List<FeatureObject> featureObjectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        final View rootView = inflater.inflate(R.layout.fragment_demo, container, false);



        CacheStringRequest stringRequest = new CacheStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            //What to do with the request
            public void onResponse(String response) {


                String temp = response;


                InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
               FeatureParserHandler parserHandler = new  FeatureParserHandler();
                featureObjectList = parserHandler.parse(stream);
                //get rid of duplicate thing
                featureObjectList = featureObjectList.subList(0,4);


                CustomPagerAdapter adapter = new CustomPagerAdapter(getFragmentManager(),getContext(), featureObjectList );



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);



        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.text)).setText("Page " + args.getInt("page_position"));

        return rootView;
    }
}

