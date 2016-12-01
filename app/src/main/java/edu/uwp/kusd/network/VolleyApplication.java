package edu.uwp.kusd.network;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dakota on 10/7/2016.
 */

public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Realm.init(sInstance);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static VolleyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}

