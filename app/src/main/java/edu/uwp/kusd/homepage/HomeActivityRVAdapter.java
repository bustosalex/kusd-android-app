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

    /**
     * The list of sections in the app
     */
    private List<AppSection> mAppSectionList;

    /**
     * The context for the adapter
     */
    private Context context;

    /**
     * Constructs a HomeActivityRVAdapter
     *
     * @param appSectionsList the list of sections in the app
     * @param context the context
     */
    public HomeActivityRVAdapter(List<AppSection> appSectionsList, Context context) {
        this.context = context;
        this.mAppSectionList = appSectionsList;
    }

    public class HomeActivityViewHolder extends RecyclerView.ViewHolder {

        /**
         * The image button for the section
         */
        private ImageButton mIcon;

        /**
         * The textview for the name of the section
         */
        private TextView mSectionName;

        /**
         * Binds an app section to a view
         *
         * @param itemView the view to bind
         */
        public HomeActivityViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageButton) itemView.findViewById(R.id.app_section_icon_image);
            mSectionName = (TextView) itemView.findViewById(R.id.app_section_name);
        }
    }

    /**
     * Handles actions to perform when a viewholder is created
     *
     * @param parent the parent viewgroup
     * @param viewType the view type
     * @return A board member viewholder
     */
    @Override
    public HomeActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_section, parent, false);
        return new HomeActivityViewHolder(view);
    }

    /**
     * Handles actions to perform when a board member is bound to a view
     *
     * @param holder the view holder
     * @param position the position to bind at
     */
    @Override
    public void onBindViewHolder(final HomeActivityViewHolder holder, final int position) {
        holder.mIcon.setBackground(context.getDrawable(mAppSectionList.get(position).getIcon()));
        holder.mSectionName.setText(mAppSectionList.get(position).getSectionName());

        //Sets up the infinite campus section and the rest of the other section buttons' click listeners
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

    /**
     * Gets the count of the app sections
     *
     * @return the count of the app sections
     */
    @Override
    public int getItemCount() {
        return mAppSectionList.size();
    }

    /**
     * Checks if an app is installed
     *
     * @param uri the url for the app
     * @return a boolean for if the app is installed
     */
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
