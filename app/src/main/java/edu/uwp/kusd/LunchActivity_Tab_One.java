package edu.uwp.kusd;

/**
 * Created by Cabz on 10/11/2016.
 */


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.network.VolleyApplication;
import edu.uwp.kusd.network.VolleySingleton;



public class LunchActivity_Tab_One extends Fragment{


    RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();
    List<LunchObj> schoolLunches;
    private LunchObj lunchObj;
    private String text;
    private String url = "http://www.kusd.edu/xml-menus";


    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_lunch_tab_one_fragment, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<LunchObj> items = null;
        ArrayList<LunchObj> selectedItems = new ArrayList<LunchObj>();


        LunchParserHandler parser = new LunchParserHandler();





      stringRequest();


//        try {
//            InputStream is = getActivity().getAssets().open("test");
//            items = (ArrayList<LunchObj>) parser.parse(is);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        for (int i = 0; i < items.size(); i++) {
            LunchObj tempObj = new LunchObj();
            if (items.get(i).getCategory().equals("Elementary School Menus")){
                System.out.println(items.get(i).getCategory());
                    tempObj.setCategory(items.get(i).getCategory());
                    tempObj.setTitle(items.get(i).getTitle());
                    tempObj.setFileUrl(items.get(i).getfileURL());
                    tempObj.cloneLunch(items.get(i));
                    selectedItems.add(tempObj);

            }
        }

        RVAdapter adapter = new RVAdapter(selectedItems);
        recyclerview.setAdapter(adapter);

    }



    public List<LunchObj> parse(String is){
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();



            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("node")) {
                            // create a new instance of LunchObj
                            lunchObj = new LunchObj();

                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("node")) {
                            // add new school to list
                            schoolLunches.add(lunchObj);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            lunchObj.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("file")) {
                            lunchObj.setFileUrl(text);
                        } else if (tagname.equalsIgnoreCase("category")) {
                            lunchObj.setCategory(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schoolLunches;
    }

    public void stringRequest(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        schoolLunches = parse(url);

                        //Parsed data and Want to return list
                        //return list;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error.Response", error.toString());


            }

        });
        queue.add(stringRequest);
    }






}

