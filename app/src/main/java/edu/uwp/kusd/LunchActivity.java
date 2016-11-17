package edu.uwp.kusd;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;



public class LunchActivity extends AppCompatActivity {

    Context context = getApplicationContext();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lunch Menus");

         actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        LunchActivity_ViewPageAdapter adapter = new  LunchActivity_ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LunchActivity_Tab_One(), "Elementary School");
        adapter.addFragment(new LunchActivity_Tab_Two(), "Middle School");
        adapter.addFragment(new LunchActivity_Tab_Three(), "High School");


        viewPager.setAdapter(adapter);

    }


}
