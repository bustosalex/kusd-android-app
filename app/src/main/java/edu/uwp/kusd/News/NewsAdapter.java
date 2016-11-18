package edu.uwp.kusd.News;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.R;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MemberViewHolder> {
    private List <NewsItems> members = new ArrayList<NewsItems>();
    // private List<NewsItems> members;
    private Context context;
    //change back to list if it breaks
    public NewsAdapter(List<NewsItems> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        private CardView cardView;
        private TextView title_tv;
        private TextView title_tv1;
        private TextView date_tv;
        private TextView content_tv;
        private Context ctx;
        List <NewsItems> nitems = new ArrayList<NewsItems>();



        public MemberViewHolder(View itemView, Context ctx, List<NewsItems> nItems) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.nitems = nItems;
            this.ctx = ctx;
            cardView = (CardView) itemView.findViewById(R.id.cv);
            title_tv = (TextView) itemView.findViewById(R.id.news_title);
            date_tv = (TextView) itemView.findViewById(R.id.news_date);
            content_tv = (TextView) itemView.findViewById(R.id.news_content);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            NewsItems newsItem = this.nitems.get(position);
            Intent intent = new Intent (this.ctx,NewsDetails.class);
            String newsTitle = newsItem.getNewsItem();
            String newsDate = newsItem.getNewsDate();
            String newsDesc = newsItem.getNewsContent();
            intent.putExtra("NT",newsTitle);
            intent.putExtra("ND",newsDate);
            intent.putExtra("NDE",newsDesc);

            // title_tv.setText(news);
            // title_tv = (TextView) itemView.findViewById(R.id.news_header);






            this.ctx.startActivity(intent);

        }

    }

    @Override
    public void onBindViewHolder(MemberViewHolder memberViewHolder, int i) {
        memberViewHolder.title_tv.setText(members.get(i).getNewsItem());
        memberViewHolder.date_tv.setText(members.get(i).getNewsDate());
        memberViewHolder.content_tv.setText(members.get(i).getNewsContent());

    }


    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_news_cardview, viewGroup, false);
        MemberViewHolder memberViewHolder = new MemberViewHolder(view,context,members);
        return memberViewHolder;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}