package edu.uwp.kusd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.network.CacheStringRequest;

/**
 * Created by Cabz on 11/30/2016.
 */

class CustomPagerAdapter extends FragmentPagerAdapter {






        List<FeatureObject> featureObjectList;
        protected Context mContext;

    public CustomPagerAdapter(FragmentManager fm, Context context, List<FeatureObject> featureObjectList) {
        super(fm);
        mContext = context;
        this.featureObjectList = featureObjectList;

    }




    @Override
        // This method returns the fragment associated with
        // the specified position.
        //
        // It is called when the Adapter needs a fragment
        // and it does not exists.
        public Fragment getItem(int position) {

            // Create fragment object
            Fragment fragment = new DemoFragment();

            // Attach some data to it that we'll
            // use to populate our fragment layouts
            Bundle args = new Bundle();
            args.putInt("page_position", position + 1);

            // Set the arguments on the fragment
            // that will be fetched in DemoFragment@onCreateView
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }