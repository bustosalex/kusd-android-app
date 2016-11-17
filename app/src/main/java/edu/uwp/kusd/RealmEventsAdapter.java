package edu.uwp.kusd;

/**
 * Created by Dakota on 11/5/2016.
 */

import android.content.Context;

import io.realm.RealmResults;

public class RealmEventsAdapter extends RealmModelAdapter<Event> {

    public RealmEventsAdapter(Context context, RealmResults<Event> realmResults) {

        super(context, realmResults);
    }
}