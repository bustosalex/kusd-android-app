package edu.uwp.kusd.homepage;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.features.FeaturesActivity;
import edu.uwp.kusd.InfiniteCampusActivity;
import edu.uwp.kusd.LunchActivity;
import edu.uwp.kusd.News.NewsActivity;
import edu.uwp.kusd.R;
import edu.uwp.kusd.SchoolsActivity;
import edu.uwp.kusd.SocialMediaActivity;
import edu.uwp.kusd.textAlerts.TextAlertActivity;
import edu.uwp.kusd.boardMembers.BoardMembersActivity;
import edu.uwp.kusd.calendar.CalendarActivity;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.HighlightsXmlParser;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private ViewFlipper mViewFlipper;

    private static final String HIGHLIGHTS_URL = "http://www.kusd.edu/xml-highlights";

    RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        toolbarText.setText("Kenosha Unified School District");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HIGHLIGHTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    HighlightsXmlParser parser = new HighlightsXmlParser(response);
                    List<Highlight> highlights = parser.parseNodes();

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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);

        List<AppSection> appSections = new ArrayList<>();
        AppSection news = new AppSection(R.drawable.ic_newspaper_black_36dp, NewsActivity.class, "News");
        appSections.add(news);
        AppSection calendar = new AppSection(R.drawable.ic_calendar_black_36dp, CalendarActivity.class, "Calendars");
        appSections.add(calendar);
        AppSection lunch = new AppSection(R.drawable.ic_food_apple_black_36dp, LunchActivity.class, "Lunch Menus");
        appSections.add(lunch);
        AppSection schools = new AppSection(R.drawable.ic_school_black_36dp, SchoolsActivity.class, "School List");
        appSections.add(schools);
        AppSection socialMedia = new AppSection(R.drawable.ic_facebook_box_black_36dp, SocialMediaActivity.class, "Social Media");
        appSections.add(socialMedia);
        AppSection features = new AppSection(R.drawable.ic_star_black_36dp, FeaturesActivity.class, "Features");
        appSections.add(features);
        AppSection textAlert = new AppSection(R.drawable.ic_message_reply_text_black_36dp, TextAlertActivity.class, "Text Alerts");
        appSections.add(textAlert);
        AppSection boardMembers = new AppSection(R.drawable.ic_bank_black_36dp, BoardMembersActivity.class, "Board Members");
        appSections.add(boardMembers);
        AppSection infiniteCampus = new AppSection(R.drawable.ic_infinity_black_36dp, InfiniteCampusActivity.class, "Infinite Campus");
        appSections.add(infiniteCampus);

        recyclerView = (RecyclerView) findViewById(R.id.home_activty_icon_grid);
        NoScrollGridLayoutManager noScrollGrid = new NoScrollGridLayoutManager(HomeActivity.this, 3);
        noScrollGrid.setScrollEnabled(false);
        recyclerView.setLayoutManager(noScrollGrid);
        adapter = new HomeActivityRVAdapter(appSections, this);
        recyclerView.setAdapter(adapter);
    }

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
}
