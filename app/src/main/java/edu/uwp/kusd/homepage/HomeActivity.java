package edu.uwp.kusd.homepage;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.features.FeaturesActivity;
import edu.uwp.kusd.InfiniteCampusActivity;
import edu.uwp.kusd.LunchActivity;
import edu.uwp.kusd.News.NewsActivity;
import edu.uwp.kusd.R;
import edu.uwp.kusd.features.ScrollingFeaturesActivity;
import edu.uwp.kusd.schools.SchoolsActivity;
import edu.uwp.kusd.SocialMediaActivity;
import edu.uwp.kusd.textAlerts.TextAlertActivity;
import edu.uwp.kusd.boardMembers.BoardMembersActivity;
import edu.uwp.kusd.calendar.CalendarActivity;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.HighlightsXmlParser;
import edu.uwp.kusd.tabularSocialMedia.TabularSocialMediaActivity;

public class HomeActivity extends AppCompatActivity {

    /**
     * The recyclerview for the app sections
     */
    private RecyclerView recyclerView;

    /**
     * The adapter for the recyclerview
     */
    private RecyclerView.Adapter adapter;

    /**
     * A view flipper for the image slider
     */
    private ViewFlipper mViewFlipper;

    /**
     * The url for the highlights xml
     */
    private static final String HIGHLIGHTS_URL = "http://www.kusd.edu/xml-highlights";

    /**
     * An image to display if there is no network connection
     */
    private ImageView mNoNetworkImage;

    /**
     * The request queue for making HTTP requests for the xml
     */
    private RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //Make a request for the highlight xml

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HIGHLIGHTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    HighlightsXmlParser parser = new HighlightsXmlParser(response);
                    List<Highlight> highlights = parser.parseNodes();

                    //Setup the highlight image slider
                    mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
                    for (Highlight highlight : highlights) {
                        setFlipperImages(highlight);
                    }
                    Animation slideIn = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.slide_in);
                    Animation slideOut = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.slide_out);
                    mViewFlipper.setAutoStart(true);
                    mViewFlipper.setInAnimation(slideIn);
                    mViewFlipper.setOutAnimation(slideOut);
                    mViewFlipper.setFlipInterval(4500);
                    mViewFlipper.startFlipping();


                } catch (IOException | XmlPullParserException | ParseException e) {
                    e.printStackTrace();
                    displayError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        if (isNetworkAvailable()) {
            requestQueue.add(stringRequest);
        } else {
            displayError();
        }

        //Setup the app section buttons
        List<AppSection> appSections = new ArrayList<>();
        AppSection news = new AppSection(R.drawable.newspaper_blue, NewsActivity.class, "News");
        appSections.add(news);
        AppSection calendar = new AppSection(R.drawable.calendar_blue, CalendarActivity.class, "Calendars");
        appSections.add(calendar);
        AppSection lunch = new AppSection(R.drawable.food_blue, LunchActivity.class, "Menus");
        appSections.add(lunch);
        AppSection schools = new AppSection(R.drawable.school_blue, SchoolsActivity.class, "School List");
        appSections.add(schools);
        AppSection socialMedia = new AppSection(R.drawable.social_media_blue, TabularSocialMediaActivity.class, "Social Media");
        appSections.add(socialMedia);
        AppSection features = new AppSection(R.drawable.star_blue, ScrollingFeaturesActivity.class, "Features");
        appSections.add(features);
        AppSection textAlert = new AppSection(R.drawable.message_reply_text_blue, TextAlertActivity.class, "Text Alerts");
        appSections.add(textAlert);
        AppSection boardMembers = new AppSection(R.drawable.bank_blue, BoardMembersActivity.class, "Board Members");
        appSections.add(boardMembers);
        AppSection infiniteCampus = new AppSection(R.drawable.infinity_blue_square, InfiniteCampusActivity.class, "Infinite Campus");

        appSections.add(infiniteCampus);

        recyclerView = (RecyclerView) findViewById(R.id.home_activty_icon_grid);
        NoScrollGridLayoutManager noScrollGrid = new NoScrollGridLayoutManager(HomeActivity.this, 3);
        noScrollGrid.setScrollEnabled(false);
        recyclerView.setLayoutManager(noScrollGrid);
        adapter = new HomeActivityRVAdapter(appSections, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets the images in the image slider
     *
     * @param highlight a highlight to set
     */
    private void setFlipperImages(final Highlight highlight) {
        ImageView image = new ImageView(getApplicationContext());
        Animation slideIn = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.slide_in);
        Glide.with(this).load(highlight.getImageURL()).centerCrop().animate(slideIn).into(image);
        mViewFlipper.addView(image);
        if (highlight.getImageLink() != null) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(highlight.getImageLink()), "text/html");
                    startActivity(intent);
                    Toast.makeText(HomeActivity.this, "Press back to return to KUSD", Toast.LENGTH_LONG).show();
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
        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mViewFlipper.setVisibility(View.GONE);
        mNoNetworkImage = (ImageView) findViewById(R.id.no_network_image);
        mNoNetworkImage.setVisibility(View.VISIBLE);
    }
}
