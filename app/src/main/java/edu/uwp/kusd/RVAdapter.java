package edu.uwp.kusd;

/**
 * Created by Cabz on 10/11/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemViewHolder> {

    private List<LunchObj> mItems;
    private Context mContext;

    public RVAdapter(Context context, List<LunchObj> mItems) {
        this.mItems = mItems;
        this.mContext = context;
    }
    //Magic happens
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private CardView mCardView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.tab_layout);
            mTextView = (TextView) itemView.findViewById(R.id.list_item);
        }
    }


    //Creates the info to be displayed and the onclick listener as well.
    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.mTextView.setText(mItems.get(i).getTitle());

        itemViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = "";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String tempString = itemViewHolder.mTextView.getText().toString();

                for (int j = 0; j < mItems.size(); j++) {
                    if(mItems.get(j).getTitle().equals(tempString)){
                        pdf = mItems.get(j).getfileURL();
                        break;
                    }

                }

                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?&embedded=true&url=" + pdf), "text/html");
                mContext.startActivity(intent);


                Toast.makeText(itemViewHolder.itemView.getContext(), "Press back to return to KUSD", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_lunch_list_row, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view );
        // itemViewHolder

        return itemViewHolder;
    }




    @Override
    public int getItemCount() {
        return mItems.size();
    }
}