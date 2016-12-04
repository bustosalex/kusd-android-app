package edu.uwp.kusd.News;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import edu.uwp.kusd.R;

public class NewsDetails extends AppCompatActivity {
    private TextView title;
    private TextView date;
    private TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("News");

        title = (TextView) findViewById(R.id.news_header);
        date = (TextView) findViewById(R.id.news_time);
        desc = (TextView) findViewById(R.id.news_fulldesc);

        Intent i =getIntent();

        String s = i.getStringExtra("NT");
        title.setText(s);
        s = i.getStringExtra("ND");
        date.setText(s);
        s = i.getStringExtra("NDE");
        desc.setText(s);

    }


}
