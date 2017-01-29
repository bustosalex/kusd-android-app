package edu.uwp.kusd.tabularSocialMedia;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

import java.util.ArrayList;

import edu.uwp.kusd.R;


/**
 * Created by Dakota on 1/27/2017.
 */

public class TabularSocialMediaActivity extends AppCompatActivity {

    /**
     * A ViewPager for the different webviews
     */
    private ViewPager mPager;

    /**
     * An adapter for the ViewPager
     */
    private PageAdapter mAdapter;

    /**
     * On activity create - instantiate the tool bar and tab layout for the activity.
     *
     * @param savedInstanceState saved bundle from a previous version of the activity - if any.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("TSMA", "Opened activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabular_social_media);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Social Media");

        //Instantiate the TabLayout for the event and PDF Calendars tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.facebook_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.twitter_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.youtube_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.instagram_title));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(4);
        //final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Handles going back in the webview's history
     *
     * @param keyCode keycode for a keyevent
     * @param event   the event
     * @return a boolean for the action performed
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SocialMediaFragment tempFragment = mAdapter.getFragment(mPager.getCurrentItem());

            WebView webview = tempFragment.getWebView();
            ArrayList<String> history = tempFragment.getHistory();
            if (history.size() > 1) {
                history.remove(webview.getUrl());
                webview.goBack();
                //webview.loadUrl(history.get(history.size()-1));
            } else {
                finish();
            }
            return true;
        }
        return true;
    }
}
