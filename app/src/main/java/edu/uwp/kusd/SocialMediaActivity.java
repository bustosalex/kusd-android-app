package edu.uwp.kusd;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


public class SocialMediaActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Social Media");


        // Go to website when clicking on logo
        ImageView face = (ImageView) findViewById(R.id.facebook);
        ImageView insta = (ImageView) findViewById(R.id.instagram);
        ImageView twitt = (ImageView) findViewById(R.id.twitter);
        ImageView you = (ImageView) findViewById(R.id.youtube);

        face.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //facebook
                String boundUrl = "https://www.facebook.com/kenoshaschools";
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                if (webIntentB.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentB);
                }
            }

        });
        insta.setOnClickListener(new View.OnClickListener() {
            //instagram
            public void onClick(View v) {
                String boundUrl = "https://www.instagram.com/kenoshaschools/";
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                if (webIntentB.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentB);
                }
            }
        });
        //twitter
        twitt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String boundUrl = "https://twitter.com/kusd";
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                if (webIntentB.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentB);
                }
            }
        });
        //youtube
        you.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String boundUrl = "https://www.youtube.com/user/kenoshaschools";
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                if (webIntentB.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntentB);
                }
            }
        });
    }

}


