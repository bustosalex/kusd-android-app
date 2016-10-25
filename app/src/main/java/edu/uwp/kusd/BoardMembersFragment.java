package edu.uwp.kusd;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uwp.kusd.network.InternalStorage;
import edu.uwp.kusd.network.TestRequest;
import edu.uwp.kusd.network.VolleySingleton;
import edu.uwp.kusd.xmlParser.BoardMemberXmlParser;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMembersFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private List<BoardMember> mBoardMembersList = new ArrayList<>();

    private BoardMemberXmlParser mBoardMemberXmlParser;

    private static final String BOARD_MEMBER_URL = "http://www.kusd.edu/xml-board-members";

    private static final String PARSED_MEMBERS = "parsedMembers";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_board_members, container, false);

        mImageLoader = VolleySingleton.getsInstance().getImageLoader();
        mRequestQueue = VolleySingleton.getsInstance().getRequestQueue();
        Cache.Entry temp = mRequestQueue.getCache().get(BOARD_MEMBER_URL);
        if (temp != null && (!temp.isExpired() && !temp.refreshNeeded())) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        @SuppressWarnings("unchecked")
                        List<BoardMember> tempBoardMemberList = (List<BoardMember>) InternalStorage.readObject(getContext(), PARSED_MEMBERS);
                        mRecyclerView = (RecyclerView) v.findViewById(R.id.board_members_recycler_view);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        BoardMemberRVAdapter adapter = new BoardMemberRVAdapter(tempBoardMemberList, getActivity());
                        mRecyclerView.setAdapter(adapter);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            };
            Thread t = new Thread(r);
            t.start();
        }

        TestRequest testRequest = new TestRequest(Request.Method.GET, BOARD_MEMBER_URL, new Response.Listener<List<BoardMember>>() {
            @Override
            public void onResponse(List<BoardMember> response) {
                mBoardMembersList = response;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InternalStorage.writeObject(getContext(), PARSED_MEMBERS, mBoardMembersList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();

                mRecyclerView = (RecyclerView) v.findViewById(R.id.board_members_recycler_view);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                BoardMemberRVAdapter adapter = new BoardMemberRVAdapter(mBoardMembersList, getActivity());
                mRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(testRequest);

        /*CacheStringRequest stringRequest = new CacheStringRequest(Request.Method.GET, BOARD_MEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mBoardMemberXmlParser = new BoardMemberXmlParser(response);
                try {
                    mBoardMembersList = mBoardMemberXmlParser.parseBoardMembers();

                    *//*for (final BoardMember member : mBoardMembersList) {
                        ImageRequest imageRequest = new ImageRequest(member.getPhotoURL(), new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                member.setPhoto(response);
                            }
                        }, 1024, 1024, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        mRequestQueue.add(imageRequest);
                    }*//*

                    mRecyclerView = (RecyclerView) v.findViewById(R.id.board_members_recycler_view);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    BoardMemberRVAdapter adapter = new BoardMemberRVAdapter(mBoardMembersList, getActivity());
                    mRecyclerView.setAdapter(adapter);
                } catch (XmlPullParserException|IOException|ParseException e) {
                    e.printStackTrace();
                }
                mBoardMemberXmlParser.close();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(stringRequest);*/

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(BOARD_MEMBER_URL);
    }
}
