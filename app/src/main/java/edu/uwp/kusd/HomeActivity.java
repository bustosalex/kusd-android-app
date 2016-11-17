package edu.uwp.kusd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        AppSection news = new AppSection(R.drawable.ic_newspaper_black_48dp, NewsActivity.class, "News");
        appSections.add(news);
        AppSection calendar = new AppSection(R.drawable.ic_calendar_black_48dp, CalendarActivity.class, "Calendars");
        appSections.add(calendar);
        AppSection lunch = new AppSection(R.drawable.ic_food_apple_black_48dp, LunchActivity.class, "Lunch Menus");
        appSections.add(lunch);
        AppSection schools = new AppSection(R.drawable.ic_school_black_48dp, SchoolsActivity.class, "School List");
        appSections.add(schools);
        AppSection socialMedia = new AppSection(R.drawable.ic_facebook_box_black_48dp, SocialMediaActivity.class, "Social Media");
        appSections.add(socialMedia);
        AppSection features = new AppSection(R.drawable.ic_star_black_48dp, FeaturesActivity.class, "Features");
        appSections.add(features);
        AppSection textAlert = new AppSection(R.drawable.ic_message_reply_text_black_48dp, TextAlertActivity.class, "Text Alerts");
        appSections.add(textAlert);
        AppSection boardMembers = new AppSection(R.drawable.ic_bank_black_48dp, BoardMembersActivity.class, "Board Members");
        appSections.add(boardMembers);
        AppSection infiniteCampus = new AppSection(R.drawable.ic_infinity_black_48dp, InfiniteCampusActivity.class, "Infinite Campus");
        appSections.add(infiniteCampus);

        recyclerView = (RecyclerView) findViewById(R.id.home_activty_icon_grid);
        NoScrollGridLayoutManager noScrollGrid = new NoScrollGridLayoutManager(HomeActivity.this, 3);
        noScrollGrid.setScrollEnabled(false);
        recyclerView.setLayoutManager(noScrollGrid);
        adapter = new HomeActivityRVAdapter(appSections, this);
        recyclerView.setAdapter(adapter);
    }
}
