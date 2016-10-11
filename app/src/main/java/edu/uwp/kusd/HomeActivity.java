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
        appSections.add(news);
        appSections.add(calendar);
        appSections.add(lunch);
        appSections.add(schools);
        appSections.add(socialMedia);
        appSections.add(features);
        appSections.add(textAlert);
        appSections.add(boardMembers);
        appSections.add(infiniteCampus);

        recyclerView = (RecyclerView) findViewById(R.id.home_activty_icon_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        adapter = new HomeActivityRVAdapter(appSections, this);
        recyclerView.setAdapter(adapter);
    }
}
