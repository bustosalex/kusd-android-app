package edu.uwp.kusd.tabularSocialMedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import edu.uwp.kusd.R;

/**
 * Created by Dakota on 1/27/2017.
 */

public class SocialMediaFragment extends Fragment {

    /**
     * A tag fro the url for the social media site
     */
    private static final String ARG_URL = "social_media_url";

    /**
     * A Webview to display the social media site in
     */
    private WebView mWebView;

    /**
     * An ArrayList for storing history
     */
    private ArrayList<String> mHistory;

    /**
     * The url to visit
     */
    private String mURL;

    /**
     * On fragment create.
     *
     * @param savedInstanceState saved bundle from a previous version of the activity - if any.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mURL = getArguments().getString(ARG_URL);
        mHistory = new ArrayList<>();
    }

    /**
     * Constructs a new instance of a SocialMediaFragment
     * @param url
     * @return
     */
    public static SocialMediaFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        SocialMediaFragment fragment = new SocialMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Handles actions performed when the fragment is created. Sets up the webview.
     *
     * @param inflater           layout inflater for the fragment
     * @param container          a container for the fragment
     * @param savedInstanceState saved bundle of data from a previous state if any
     * @return a view for the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social_media, container, false);

        mWebView = (WebView) v.findViewById(R.id.socialMediaWebView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    mHistory.add(url);
                    return false;
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    Intent.createChooser(i, "Open App");
                    return true;
                }
            }
        });
        mHistory.add(mURL);
        mWebView.loadUrl(mURL);

        return v;
    }

    /**
     * Returns the fragments webview
     *
     * @return a webview
     */
    public WebView getWebView() {
        return mWebView;
    }

    /**
     * Gets the fragment's webview history
     *
     * @return and arraylist of strings for the webview history
     */
    public ArrayList<String> getHistory() {
        return mHistory;
    }


}
