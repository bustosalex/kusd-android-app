package edu.uwp.kusd.schools;

import android.content.Context;

import android.content.Intent;

import android.net.Uri;
import android.support.v7.widget.CardView;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;

import edu.uwp.kusd.R;

import edu.uwp.kusd.R;


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
        //TextView city;
        //TextView zip;
        TextView phone;
        TextView principal;
        ImageView image;


        SchoolViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            address = (TextView) itemView.findViewById(R.id.address);
            //city = (TextView) itemView.findViewById(R.id.city);
            //zip = (TextView) itemView.findViewById(R.id.zip);
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

        //converts the image URL to a bitmap to correctly display
        Glide.with(context).load(schools.get(i).image).into(schoolViewHolder.image);
        schoolViewHolder.name.setText(schools.get(i).schoolName);
        schoolViewHolder.address.setText(schools.get(i).address + "\n" + schools.get(i).city + "\n" + schools.get(i).zip);
        //schoolViewHolder.city.setText(schools.get(i).city);
        //schoolViewHolder.zip.setText(schools.get(i).zip);
        schoolViewHolder.phone.setText(schools.get(i).phone);
        schoolViewHolder.principal.setText(schools.get(i).principal);


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


}
