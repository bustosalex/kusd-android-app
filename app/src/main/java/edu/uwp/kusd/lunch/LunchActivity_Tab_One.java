package edu.uwp.kusd.lunch;

/**
 * Created by Cabz on 10/11/2016.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.R;
import edu.uwp.kusd.RVAdapter;
import edu.uwp.kusd.network.CacheStringRequest;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.LunchXMLParser;

public class LunchActivity_Tab_One extends Fragment {


    RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();
    //List of all lunch objects
    List<LunchObj> schoolLunches;
    private LunchObj lunchObj;
    private String text;
    // Location of the xml file to be parsed
    private String url = "http://www.kusd.edu/xml-menus";
    ArrayList<LunchObj> items = null;
    //List of lunch Objects that fit the proper category
    ArrayList<LunchObj> selectedItems = new ArrayList<LunchObj>();


    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_lunch_tab_one_fragment, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        //Network Request
        CacheStringRequest stringRequest = new CacheStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            //What to do with the request
            public void onResponse(String response) {



                LunchXMLParser parserHandler = new LunchXMLParser(response);
                try {
                    schoolLunches = parserHandler.parseNodes();

                    items = (ArrayList<LunchObj>) schoolLunches;
                    //Creates selected items to make.
                    for (int i = 0; i < items.size(); i++) {
                        LunchObj tempObj = new LunchObj();
                        if (items.get(i).getCategory().equals("Elementary School Menus")) {
                            System.out.println(items.get(i).getCategory());
                            tempObj.setCategory(items.get(i).getCategory());
                            tempObj.setTitle(items.get(i).getTitle());
                            tempObj.setFileUrl(items.get(i).getfileURL());
                            tempObj.cloneLunch(items.get(i));
                            selectedItems.add(tempObj);

                        }
                    }

                    RVAdapter adapter = new RVAdapter(getContext(), selectedItems);
                    recyclerview.setAdapter(adapter);

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);

        return view;
    }







}
