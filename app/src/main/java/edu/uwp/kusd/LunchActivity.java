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

import edu.uwp.kusd.network.VolleyApplication;


public class LunchActivity extends AppCompatActivity {

    Context context = VolleyApplication.getAppContext();
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
        actionBar.setTitle("Menus");

         actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }
    //Sets the name of the Fragment and which tab it will be working off of.
    private void setupViewPager(ViewPager viewPager) {
        LunchActivity_ViewPageAdapter adapter = new  LunchActivity_ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LunchActivity_Tab_One(), "Elementary");
        adapter.addFragment(new LunchActivity_Tab_Two(), "Middle");
        adapter.addFragment(new LunchActivity_Tab_Three(), "High");


        viewPager.setAdapter(adapter);

    }


}
