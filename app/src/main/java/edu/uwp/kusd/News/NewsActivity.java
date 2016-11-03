package edu.uwp.kusd.News;


import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.uwp.kusd.R;

public class NewsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_test);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("News");

        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView1);

        //parse news items into a list and print them
        List<NewsItems> newsItems = null;
        try {
            NewsRead parser = new NewsRead();
            InputStream is = getAssets().open("news.xml");
            newsItems = parser.parse(is);
            ArrayList<String> strings = new ArrayList<>();
            for(NewsItems n : newsItems){
                strings.add(n.getNewsItem().trim() + "\n\n" + n.getNewsDate().trim() + "\n\n" + n.getNewsContent().trim() + "\n");
            }

            ArrayAdapter<String> adapter =new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(adapter);


        } catch (IOException e) {e.printStackTrace();}

    }

}
