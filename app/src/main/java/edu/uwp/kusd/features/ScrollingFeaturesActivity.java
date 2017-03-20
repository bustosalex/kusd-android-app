package edu.uwp.kusd.features;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import edu.uwp.kusd.R;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.FeaturesXmlParser;

/**
 * Created by dakota on 3/19/17.
 */

public class ScrollingFeaturesActivity extends AppCompatActivity {

    /**
     * The URL to pull features images from.
     */
    private static final String FEATURES_URL = "http://www.kusd.edu/xml-features";

    /**
     * An instance of the request queue for volley.
     */
    private RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    /**
     * A view flipper for the image slider
     */
    private ViewFlipper mViewFlipper;

    /**
     * Sets up the view flipper for the features images.
     *
     * @param savedInstanceState a previous saved state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_features);

        //Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.features_activity_title);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FEATURES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    FeaturesXmlParser parser = new FeaturesXmlParser(response);
                    List<Feature> features = parser.parseNodes();

                    //Setup the highlight image slider
                    mViewFlipper = (ViewFlipper) findViewById(R.id.scrolling_features_flipper);
                    for (Feature feature : features) {
                        setFlipperImages(feature);
                    }
                    Animation slideIn = AnimationUtils.loadAnimation(ScrollingFeaturesActivity.this, R.anim.slide_in);
                    Animation slideOut = AnimationUtils.loadAnimation(ScrollingFeaturesActivity.this, R.anim.slide_out);
                    mViewFlipper.setAutoStart(true);
                    mViewFlipper.setInAnimation(slideIn);
                    mViewFlipper.setOutAnimation(slideOut);
                    mViewFlipper.setFlipInterval(7000);
                    mViewFlipper.startFlipping();
                } catch (IOException | XmlPullParserException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                displayError();
            }
        });
        if (isNetworkAvailable()) {
            requestQueue.add(stringRequest);
        } else {
            displayError();
        }
    }

    /**
     * Sets the images to load in the view flipper.
     *
     * @param feature the feature to load the image for
     */
    private void setFlipperImages(final Feature feature) {
        ImageView image = new ImageView(getApplicationContext());
        Animation slideIn = AnimationUtils.loadAnimation(ScrollingFeaturesActivity.this, R.anim.slide_in);
        Glide.with(this).load(feature.getImageURL()).animate(slideIn).into(image);
        mViewFlipper.addView(image);
        if (feature.getImageLink() != null) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(feature.getImageLink()), "text/html");
                    startActivity(intent);
                    Toast.makeText(ScrollingFeaturesActivity.this, "Press back to return to KUSD", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Checks to see if a network connection is available
     *
     * @return true/false if a network connection is available
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Displays an error message if there is no network connection
     */
    private void displayError() {
        TextView noFeatures = (TextView) findViewById(R.id.no_scrolling_features);
        noFeatures.setVisibility(View.VISIBLE);
    }
}
