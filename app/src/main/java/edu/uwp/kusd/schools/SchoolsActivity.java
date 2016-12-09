package edu.uwp.kusd.schools;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.MenuInflater;

import edu.uwp.kusd.PagerAdapter;
import edu.uwp.kusd.R;


public class SchoolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Schools");

        //tablayout code
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Elem."));
        tabLayout.addTab(tabLayout.newTab().setText("Middle"));
        tabLayout.addTab(tabLayout.newTab().setText("High"));
        tabLayout.addTab(tabLayout.newTab().setText("Charter"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    //the menu for other pages on the "schools" webpage
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.school_list:
                Intent intent = new Intent(this, SchoolsActivity.class);
                startActivity(intent);
                return true;
            case R.id.school_boundaries:
                String boundUrl = "http://kusd.edu/schools/school-boundaries";
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                if (webIntentB.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentB);
                }
                return true;
            case R.id.supply_list:
                String supUrl = "http://kusd.edu/schools/supply-list";
                Intent webIntentS = new Intent(Intent.ACTION_VIEW, Uri.parse(supUrl));
                if (webIntentS.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentS);
                }
                return true;
            case R.id.hours_day:
                String hourUrl = "http://kusd.edu/schools/hours-school-day";
                //Go to the website
                Intent webIntentH = new Intent(Intent.ACTION_VIEW, Uri.parse(hourUrl));
                if (webIntentH.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentH);
                }
                return true;
            case R.id.choice_charter:
                String chUrl = "http://kusd.edu/schools/choicecharter-schools";
                //Go to the website
                Intent webIntentC = new Intent(Intent.ACTION_VIEW, Uri.parse(chUrl));
                if (webIntentC.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentC);
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}