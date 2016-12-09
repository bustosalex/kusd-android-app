package edu.uwp.kusd.boardMembers;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.R;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.BoardMemberXmlParser;

/**
 * Created by Dakota on 10/21/2016.
 */

public class   BoardMembersFragment extends Fragment {

    /**
     * The recyclerview for the board member list
     */
    private RecyclerView mRecyclerView;

    /**
     * The request queue for making HTTP requests
     */
    private RequestQueue mRequestQueue;

    /**
     * The list of board members
     */
    private List<BoardMember> mBoardMembersList = new ArrayList<>();

    /**
     * The URL to request XML from
     */
    private static final String BOARD_MEMBER_URL = "http://www.kusd.edu/xml-board-members";

    /**
     * Handles creation of the fragment
     *
     * @param inflater the layout inflater
     * @param container the container the fragment is housed in
     * @param savedInstanceState the saved state
     * @return a view for the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_board_members, container, false);

        mRequestQueue = VolleySingleton.getsInstance().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BOARD_MEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    BoardMemberXmlParser parser = new BoardMemberXmlParser(response);
                    mBoardMembersList = parser.parseBoardMembers();
                    mRecyclerView = (RecyclerView) v.findViewById(R.id.board_members_recycler_view);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    BoardMemberRVAdapter adapter = new BoardMemberRVAdapter(mBoardMembersList, getActivity());
                    mRecyclerView.setAdapter(adapter);
                } catch (XmlPullParserException | IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                displayError(v);
            }
        });
        if (isNetworkAvailable()) {
            mRequestQueue.add(stringRequest);
        } else {
            displayError(v);
        }
        return v;
    }

    /**
     * Handles the stopping of the fragment
     */
    @Override
    public void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(BOARD_MEMBER_URL);
    }

    /**
     * Checks for an active network connection
     *
     * @return a boolean corresponding to if the device has an active network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Sets up views to let the user know there is no network connection
     *
     * @param parent the parent view that contains the views to setup
     */
    private void displayError(View parent) {
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.board_members_recycler_view);
        mRecyclerView.setVisibility(View.GONE);
        TextView noEvents = (TextView) parent.findViewById(R.id.no_board);
        noEvents.setVisibility(View.VISIBLE);
    }
}
