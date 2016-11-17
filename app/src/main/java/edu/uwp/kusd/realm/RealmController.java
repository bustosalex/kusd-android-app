package edu.uwp.kusd.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.Date;

import edu.uwp.kusd.Event;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dakota on 11/5/2016.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public Event getEventById(String id) {
        return realm.where(Event.class).equalTo("mid", id).findFirst();
    }

    public RealmResults<Event> getEventsListbyEventDate(Date date) {
        return realm.where(Event.class).equalTo("mDate", date).findAll();
    }
}
