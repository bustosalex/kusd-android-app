package edu.uwp.kusd;

/**
 * Created by Liz on 10/17/2016.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Liz on 10/17/2016.
 */

// In this case, the fragment displays simple text based on the page
public class MiddleFragment extends Fragment {
    private List<School> schools;
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mid_school_frag, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rvM);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);


        return rootView;
    }


}
