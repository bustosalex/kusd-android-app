package edu.uwp.kusd.features;

/**
 * Created by Cabz on 12/1/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.uwp.kusd.features.Feature;
import edu.uwp.kusd.homepage.HomeActivity;



public class FeaturesPagerAdapter extends PagerAdapter {

    private List<Feature> mFeatures;

    private Context mContext;

    //List of features to make a fragment off
    FeaturesPagerAdapter(Context context, List<Feature> featureList) {
        this.mContext = context;
        this.mFeatures = featureList;
    }

    @Override
    public int getCount() {
        return mFeatures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    //Downloads image from websitem and sets a click listener to open the link
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(mFeatures.get(position).getImageURL()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(mFeatures.get(position).getImageLink()), "text/html");
                mContext.startActivity(intent);
                Toast.makeText(mContext, "Press back to return to KUSD", Toast.LENGTH_LONG).show();
            }
        });
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}