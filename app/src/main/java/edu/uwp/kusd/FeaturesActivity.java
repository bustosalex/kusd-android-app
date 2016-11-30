package edu.uwp.kusd;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

//appcombat?
public class FeaturesActivity extends AppCompatActivity {

    List<FeatureObject> featureObjectList;

        CustomPagerAdapter mCustomPagerAdapter;
        ViewPager mViewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_features);

            // == Setting up the ViewPager ==

        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager() , getApplicationContext(), featureObjectList);

            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mCustomPagerAdapter);
        }
    }