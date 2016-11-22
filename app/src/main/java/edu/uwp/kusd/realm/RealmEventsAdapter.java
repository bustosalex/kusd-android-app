package edu.uwp.kusd.realm;

/**
 * Created by Dakota on 11/5/2016.
 */

import android.content.Context;

import edu.uwp.kusd.calendar.Event;
import io.realm.RealmResults;

public class RealmEventsAdapter extends RealmModelAdapter<Event> {

    public RealmEventsAdapter(Context context, RealmResults<Event> realmResults) {

        super(context, realmResults);
    }
}