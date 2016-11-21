package edu.uwp.kusd.boardMembers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class BoardMembersFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private RequestQueue mRequestQueue;

    private List<BoardMember> mBoardMembersList = new ArrayList<>();

    private static final String BOARD_MEMBER_URL = "http://www.kusd.edu/xml-board-members";

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
            }
        });
        mRequestQueue.add(stringRequest);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(BOARD_MEMBER_URL);
    }
}
