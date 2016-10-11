package edu.uwp.kusd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<AppSection> appSections = new ArrayList<>();
        AppSection news = new AppSection(R.drawable.ic_newspaper_black_48dp, NewsActivity.class);
        appSections.add(news);
        AppSection calendar = new AppSection(R.drawable.ic_calendar_black_48dp, CalendarActivity.class);
        appSections.add(calendar);
        AppSection lunch = new AppSection(R.drawable.ic_food_apple_black_48dp, LunchActivity.class);
        appSections.add(lunch);
        AppSection schools = new AppSection(R.drawable.ic_school_black_48dp, SchoolsActivity.class);
        appSections.add(schools);
        AppSection socialMedia = new AppSection(R.drawable.ic_facebook_box_black_48dp, SocialMediaActivity.class);
        appSections.add(socialMedia);
        AppSection features = new AppSection(R.drawable.ic_star_black_48dp, FeaturesActivity.class);
        appSections.add(features);
        AppSection textAlert = new AppSection(R.drawable.ic_message_reply_text_black_48dp, TextAlertActivity.class);
        appSections.add(textAlert);
        AppSection boardMembers = new AppSection(R.drawable.ic_bank_black_48dp, BoardMembersActivity.class);
        appSections.add(boardMembers);
        AppSection infiniteCampus = new AppSection(R.drawable.ic_infinity_black_48dp, InfiniteCampusActivity.class);
        appSections.add(infiniteCampus);

        recyclerView = (RecyclerView) findViewById(R.id.home_activty_icon_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        adapter = new HomeActivityRVAdapter(appSections, this);
        recyclerView.setAdapter(adapter);
    }
}
