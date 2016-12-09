package edu.uwp.kusd.boardMembers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import edu.uwp.kusd.R;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMemberRVAdapter extends RecyclerView.Adapter<BoardMemberRVAdapter.BoardMemberViewHolder> {

    /**
     * The list of board members
     */
    private List<BoardMember> mBoardMembers;

    /**
     * The context for the adapter
     */
    private Context mContext;

    /**
     * Constructs a BoardMemberRVAdapter
     *
     * @param boardMemberList a list of board members
     * @param context the context
     */
    public BoardMemberRVAdapter(List<BoardMember> boardMemberList, Context context) {
        mBoardMembers = boardMemberList;
        mContext = context;
    }

    /**
     * Handles actions to perform when a viewholder is created
     *
     * @param parent the parent viewgroup
     * @param viewType the view type
     * @return A board member viewholder
     */
    @Override
    public BoardMemberRVAdapter.BoardMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_member_item, parent, false);
        return new BoardMemberViewHolder(view);
    }

    /**
     * Handles actions to perform when a board member is bound to a view
     *
     * @param holder the view holder
     * @param position the position to bind at
     */
    @Override
    public void onBindViewHolder(BoardMemberRVAdapter.BoardMemberViewHolder holder, int position) {
        Glide.with(mContext).load(mBoardMembers.get(position).getPhotoURL()).centerCrop().into(holder.mPhotoImageView);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM y", Locale.US);
        holder.mNameTextView.setText(mBoardMembers.get(position).getName());
        holder.mPositionTextView.setText(mBoardMembers.get(position).getPosition());
        holder.mEmailTextView.setText(mBoardMembers.get(position).getEmail());
        holder.mPhoneTextView.setText(mBoardMembers.get(position).getPhone());
        String temp = mContext.getResources().getString(R.string.term_expires) + " " + dateFormat.format(mBoardMembers.get(position).getTerm());
        holder.mTermTextView.setText(temp);
    }

    /**
     * Gets the number of board members
     *
     * @return the number of board members
     */
    @Override
    public int getItemCount() {
        return mBoardMembers.size();
    }


    public class BoardMemberViewHolder extends RecyclerView.ViewHolder {

        /**
         * The image view for the board members photo
         */
        private ImageView mPhotoImageView;

        /**
         * The text view for the board members name
         */
        private TextView mNameTextView;

        /**
         * The text view for the board members position
         */
        private TextView mPositionTextView;

        /**
         * The text view for the board members email
         */
        private TextView mEmailTextView;

        /**
         * The text view for the board members phone number
         */
        private TextView mPhoneTextView;

        /**
         * The text view for the board members term
         */
        private TextView mTermTextView;


        /**
         * Binds a board member to the view
         *
         * @param itemView the view
         */
        public BoardMemberViewHolder(View itemView) {
            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.board_photo);
            mNameTextView = (TextView) itemView.findViewById(R.id.board_name);
            mPositionTextView = (TextView) itemView.findViewById(R.id.board_position);
            mEmailTextView = (TextView) itemView.findViewById(R.id.board_email);
            mPhoneTextView = (TextView) itemView.findViewById(R.id.board_phone);
            mTermTextView = (TextView) itemView.findViewById(R.id.board_term);
        }
    }
}
