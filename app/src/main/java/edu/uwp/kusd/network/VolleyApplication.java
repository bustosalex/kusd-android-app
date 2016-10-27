package edu.uwp.kusd.network;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dakota on 10/7/2016.
 */

public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static VolleyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
