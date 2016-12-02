package edu.uwp.kusd.features;

/**
 * Created by Cabz on 12/1/2016.
 */

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import java.util.List;

import edu.uwp.kusd.R;

import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.FeaturesXmlParser;


/**
 * Created by Dakota on 11/30/2016.
 */

public class FeaturesFragment extends Fragment {

    private static final String FEATURES_URL = "http://www.kusd.edu/xml-features";

    private RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_features, container, false);




        StringRequest stringRequest = new StringRequest(Request.Method.GET, FEATURES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    FeaturesXmlParser parser = new FeaturesXmlParser(response);
                    List<Feature> features = parser.parseNodes();
                    ViewPager pager = (ViewPager) v.findViewById(R.id.features_view_pager);
                    FeaturesPagerAdapter pagerAdapter = new FeaturesPagerAdapter(getActivity(), features);
                    pager.setAdapter(pagerAdapter);
                } catch (IOException | XmlPullParserException | ParseException e) {
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
        return v;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayError(View v) {
        ViewPager pager = (ViewPager) v.findViewById(R.id.features_view_pager);
        pager.setVisibility(View.GONE);
        TextView noFeatures = (TextView) v.findViewById(R.id.no_features);
        noFeatures.setVisibility(View.VISIBLE);
    }
}