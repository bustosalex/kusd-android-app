package edu.uwp.kusd;

/**
 * Created by Cabz on 10/11/2016.
 */


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LunchActivity_Tab_One extends Fragment{


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

        try {
            InputStream is = getActivity().getAssets().open("test");


            items = (ArrayList<LunchObj>) parser.parse(is);




        } catch (IOException e) {
            e.printStackTrace();
        }

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

}

