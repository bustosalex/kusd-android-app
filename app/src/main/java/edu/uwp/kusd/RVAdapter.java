package edu.uwp.kusd;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Liz on 10/10/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SchoolViewHolder> {
    List<School> schools;
    private Context context;

    public static class SchoolViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView address;
        TextView phone;
        TextView principal;
        TextView website;
        TextView image; //change later??

        SchoolViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (TextView) itemView.findViewById(R.id.image);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            principal = (TextView) itemView.findViewById(R.id.principal);
            website = (TextView) itemView.findViewById(R.id.website);

        }
    }


    RVAdapter(List<School> schools, Context context) {
        this.schools = schools;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(SchoolViewHolder schoolViewHolder, int i) {
        schoolViewHolder.name.setText(schools.get(i).schoolName);
        schoolViewHolder.address.setText(schools.get(i).address);
        schoolViewHolder.phone.setText(schools.get(i).phone);
        schoolViewHolder.principal.setText(schools.get(i).principal);
        schoolViewHolder.website.setText(schools.get(i).website);
        schoolViewHolder.image.setText(schools.get(i).image);
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
