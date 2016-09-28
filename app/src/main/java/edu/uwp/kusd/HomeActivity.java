package edu.uwp.kusd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    private ImageButton mNewsButton;
    private ImageButton mCalendarButton;
    private ImageButton mLunchButton;
    private ImageButton mSchoolsButton;
    private ImageButton mSocialMediaButton;
    private ImageButton mFeaturesButton;
    private ImageButton mTextAlertButton;
    private ImageButton mBoardMembersButton;
    private ImageButton mInfiniteCampusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNewsButton = (ImageButton) findViewById(R.id.news_button);
        mNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start NewsActivity
                Intent i = new Intent(HomeActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });

        mCalendarButton = (ImageButton) findViewById(R.id.calendar_button);
        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CalendarActivity
                Intent i = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(i);
            }
        });

        mLunchButton = (ImageButton) findViewById(R.id.lunch_button);
        mLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start LunchActivity
                Intent i = new Intent(HomeActivity.this, LunchActivity.class);
                startActivity(i);
            }
        });

        mSchoolsButton = (ImageButton) findViewById(R.id.schools_button);
        mSchoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start SchoolsActivity
                Intent i = new Intent(HomeActivity.this, SchoolsActivity.class);
                startActivity(i);
            }
        });

        mSocialMediaButton = (ImageButton) findViewById(R.id.social_media_button);
        mSocialMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start SocialMediaActivity
                Intent i = new Intent(HomeActivity.this, SocialMediaActivity.class);
                startActivity(i);
            }
        });

        mFeaturesButton = (ImageButton) findViewById(R.id.features_button);
        mFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start FeaturesActivity
                Intent i = new Intent(HomeActivity.this, FeaturesActivity.class);
                startActivity(i);
            }
        });

        mTextAlertButton = (ImageButton) findViewById(R.id.text_alert_button);
        mTextAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start TextAlertActivity
                Intent i = new Intent(HomeActivity.this, TextAlertActivity.class);
                startActivity(i);
            }
        });

        mBoardMembersButton = (ImageButton) findViewById(R.id.board_members_button);
        mBoardMembersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start BoardMembersActivity
                Intent i = new Intent(HomeActivity.this, BoardMembersActivity.class);
                startActivity(i);
            }
        });

        mInfiniteCampusButton = (ImageButton) findViewById(R.id.infinite_campus_button);
        mInfiniteCampusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start SocialMediaActivity
                Intent i = new Intent(HomeActivity.this, InfiniteCampusActivity.class);
                startActivity(i);
            }
        });
    }
}
