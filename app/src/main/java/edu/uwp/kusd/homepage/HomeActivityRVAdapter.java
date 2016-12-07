package edu.uwp.kusd.homepage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.uwp.kusd.R;

public class HomeActivityRVAdapter extends RecyclerView.Adapter<HomeActivityRVAdapter.HomeActivityViewHolder> {

    private List<AppSection> mAppSectionList;

    private Context context;

    public HomeActivityRVAdapter(List<AppSection> appSectionsList, Context context) {
        this.context = context;
        this.mAppSectionList = appSectionsList;
    }

    public class HomeActivityViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRelativeLayout;

        private ImageButton mIcon;

        private TextView mSectionName;

        public HomeActivityViewHolder(View itemView) {
            super(itemView);

            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.clickable_section);
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
                if (mAppSectionList.get(holder.getAdapterPosition()).getSectionName().equals("Infinite Campus")) {
                    boolean installed = isAppInstalled("com.infinitecampus.mobilePortal");
                    if (installed) {
                        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.infinitecampus.mobilePortal");
                        context.startActivity(intent);
                    } else {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.infinitecampus.mobilePortal" ));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.infinitecampus.mobilePortal&hl=en"));
                            context.startActivity(intent);
                        }
                    }
                } else {
                    Intent i = new Intent(context, mAppSectionList.get(holder.getAdapterPosition()).getActivityName());
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppSectionList.size();
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
