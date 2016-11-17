package edu.uwp.kusd;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmObject;
import io.realm.RealmBaseAdapter;
/**
 * Created by Dakota on 11/5/2016.
 */

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    private RealmBaseAdapter<T> mRealmBaseAdapter;

    public T getItem(int position) {
        return mRealmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter() {
        return mRealmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {
        mRealmBaseAdapter = realmAdapter;
    }
}
