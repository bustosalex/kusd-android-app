package edu.uwp.kusd;

/**
 * Created by Cabz on 10/25/2016.
 */

import java.io.IOException;
import java.util.List;
//import com.theopentutorials.android.beans.Employee;
//import com.theopentutorials.android.xml.XMLPullParserHandler;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//might be obselete
public class LunchParserPull extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_tab_one_fragment);
        ListView listView = (ListView) findViewById(R.id.recyclerview);

        List<LunchObj> lunchObj = null;
        try {
            LunchParserHandler parser = new LunchParserHandler();
            InputStream is = getAssets().open("test");
            lunchObj = parser.parse(is);

            ArrayAdapter<LunchObj> adapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, lunchObj);
            listView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}