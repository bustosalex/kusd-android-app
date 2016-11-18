package edu.uwp.kusd.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Dakota on 10/7/2016.
 */

public class VolleySingleton {

    private static VolleySingleton sInstance = null;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(VolleyApplication.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
               return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

    }

    public static VolleySingleton getsInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}

