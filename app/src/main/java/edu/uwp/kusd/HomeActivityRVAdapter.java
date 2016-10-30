package edu.uwp.kusd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HomeActivityRVAdapter extends RecyclerView.Adapter<HomeActivityRVAdapter.HomeActivityViewHolder> {

    private List<AppSection> mAppSectionList;

    private Context context;

    public HomeActivityRVAdapter(List<AppSection> appSectionsList, Context context) {
        this.context = context;
        this.mAppSectionList = appSectionsList;
    }

    public class HomeActivityViewHolder extends RecyclerView.ViewHolder {

        private ImageButton mIcon;

        private LinearLayout mHomeClickableLayout;

        private TextView mSectionName;

        public HomeActivityViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageButton) itemView.findViewById(R.id.app_section_icon_image);
            mSectionName = (TextView) itemView.findViewById(R.id.app_section_name);
        }
    }

    @Override
    public HomeActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_section, parent, false);
        return new HomeActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeActivityViewHolder holder, final int position) {
        holder.mIcon.setBackground(context.getDrawable(mAppSectionList.get(position).getIcon()));
        holder.mSectionName.setText(mAppSectionList.get(position).getSectionName());
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, mAppSectionList.get(holder.getAdapterPosition()).getActivityName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppSectionList.size();
    }
}
