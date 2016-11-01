//TODO: Implement callback methods

package edu.uwp.kusd;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Class for the Calendars activity. The activity houses two tabs: an events tab and a PDF Calendars tab.
 * Each tab is a fragment.
 */
public class CalendarActivity extends AppCompatActivity {

    /**
     * On activity create - instantiate the tool bar and tab layout for the activity.
     *
     * @param savedInstanceState saved bundle from a previous version of the activity - if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Instantiate the tool bar and set the title of the activity in the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.calendar_activity_title);

        //Instantiate the TabLayout for the event and PDF Calendars tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.calendar_activity_events_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.calendar_activity_pdf_calendars));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Placeholder implementation of onPause.
     * TODO: Implement if needed.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Placeholder implementation of onStop.
     * TODO: Implement if needed.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Placeholder implementation of onDestroy.
     * TODO: Implement if needed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Placeholder implementation of onResume.
     * TODO: Implement if needed.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Placeholder implementation of onRestart.
     * TODO: Implement if needed.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Placeholder implementation of onStart.
     * TODO: Implement if needed.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }
}
