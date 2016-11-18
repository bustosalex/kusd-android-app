package edu.uwp.kusd;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.support.v7.widget.CardView;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.List;


/**
 * RecyclerView adapter for each tab
 */

public class SRVAdapter extends RecyclerView.Adapter<SRVAdapter.SchoolViewHolder> {
    List<School> schools;
    private Context context;

    SRVAdapter(List<School> schools, Context context) {
        this.schools = schools;
        this.context = context;

    }


    public class SchoolViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView address;
        TextView city;
        TextView zip;
        TextView phone;
        TextView principal;
        ImageView image;


        SchoolViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            address = (TextView) itemView.findViewById(R.id.address);
            city = (TextView) itemView.findViewById(R.id.city);
            zip = (TextView) itemView.findViewById(R.id.zip);
            phone = (TextView) itemView.findViewById(R.id.phone);
            principal = (TextView) itemView.findViewById(R.id.principal);

        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onBindViewHolder(SchoolViewHolder schoolViewHolder, int i) {

        //sets the link for each school website
        //when the card is selected it opens the link
        final int x = i; //for inner placeholder variable

        //sets the clicklistener here to get the correct website
        schoolViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            String boundUrl;
            @Override
            public void onClick(View v) {

                boundUrl = schools.get(x).website;
                Intent webIntentB = new Intent(Intent.ACTION_VIEW, Uri.parse(boundUrl));
                v.getContext().startActivity(webIntentB);
            }


        });

        schoolViewHolder.name.setText(schools.get(i).schoolName);
        schoolViewHolder.address.setText(schools.get(i).address);
        schoolViewHolder.city.setText(schools.get(i).city);
        schoolViewHolder.zip.setText(schools.get(i).zip);
        schoolViewHolder.phone.setText(schools.get(i).phone);
        schoolViewHolder.principal.setText(schools.get(i).principal);


        //converts each image
        //URL into a bitmap image and sets the image
        Bitmap b = getBitmapFromURL(schools.get(i).image);
        schoolViewHolder.image.setImageBitmap(b);


    }

    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_schools_lists, viewGroup, false);
        SchoolViewHolder pvh = new SchoolViewHolder(v);


        return pvh;
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    //bitmap conversion method
    public Bitmap getBitmapFromURL(String src) {
        Bitmap myBitmap = null;

        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            // Log exception
        }
        return myBitmap;
    }


}
