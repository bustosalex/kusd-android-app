package edu.uwp.kusd.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uwp.kusd.BoardMember;
import edu.uwp.kusd.xmlParser.BoardMemberXmlParser;

/**
 * Created by Dakota on 10/23/2016.
 */

public class TestRequest extends Request<List<BoardMember>> {

    private final Response.Listener<List<BoardMember>> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public TestRequest(int method, String url, Response.Listener<List<BoardMember>> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public TestRequest(String url, Response.Listener<List<BoardMember>> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(List<BoardMember> response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<List<BoardMember>> parseNetworkResponse(NetworkResponse response) {
        String responseString = new String(response.data);
        BoardMemberXmlParser parser = new BoardMemberXmlParser(responseString);
        List<BoardMember> memberList;
        try {
            memberList = parser.parseBoardMembers();
            return Response.success(memberList, parseIgnoreCacheHeaders(response, memberList));
        } catch (XmlPullParserException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response, List<BoardMember> parsedData) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 7 * 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }
}
