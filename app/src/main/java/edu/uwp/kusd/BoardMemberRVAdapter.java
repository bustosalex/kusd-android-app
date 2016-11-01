package edu.uwp.kusd;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.uwp.kusd.network.VolleySingleton;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMemberRVAdapter extends RecyclerView.Adapter<BoardMemberRVAdapter.BoardMemberViewHolder> {

    private List<BoardMember> mBoardMembers;

    private Context mContext;

    public BoardMemberRVAdapter(List<BoardMember> boardMemberList, Context context) {
        mBoardMembers = boardMemberList;
        mContext = context;
    }

    @Override
    public BoardMemberRVAdapter.BoardMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_member_item, parent, false);
        return new BoardMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardMemberRVAdapter.BoardMemberViewHolder holder, int position) {
        holder.mNetworkImageView.setImageUrl(mBoardMembers.get(position).getPhotoURL(), VolleySingleton.getsInstance().getImageLoader());
        //holder.mPhotoImageView.setImageBitmap(mBoardMembers.get(position).getPhoto());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM y", Locale.US);
        holder.mNameTextView.setText(mBoardMembers.get(position).getName());
        holder.mPositionTextView.setText(mBoardMembers.get(position).getPosition());
        holder.mEmailTextView.setText(mBoardMembers.get(position).getEmail());
        holder.mPhoneTextView.setText(mBoardMembers.get(position).getPhone());
        String temp = mContext.getResources().getString(R.string.term_expires) + " " + dateFormat.format(mBoardMembers.get(position).getTerm());
        holder.mTermTextView.setText(temp);
    }

    @Override
    public int getItemCount() {
        return mBoardMembers.size();
    }

    public class BoardMemberViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView mNetworkImageView;
        //private ImageView mPhotoImageView;
        private TextView mNameTextView;
        private TextView mPositionTextView;
        private TextView mEmailTextView;
        private TextView mPhoneTextView;
        private TextView mTermTextView;


        public BoardMemberViewHolder(View itemView) {
            super(itemView);
            mNetworkImageView = (NetworkImageView) itemView.findViewById(R.id.board_photo);
            //mPhotoImageView = (ImageView) itemView.findViewById(R.id.board_photo);
            mNameTextView = (TextView) itemView.findViewById(R.id.board_name);
            mPositionTextView = (TextView) itemView.findViewById(R.id.board_position);
            mEmailTextView = (TextView) itemView.findViewById(R.id.board_email);
            mPhoneTextView = (TextView) itemView.findViewById(R.id.board_phone);
            mTermTextView = (TextView) itemView.findViewById(R.id.board_term);
        }
    }
}
