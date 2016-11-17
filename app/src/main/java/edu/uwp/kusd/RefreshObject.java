package edu.uwp.kusd;

import io.realm.RealmObject;

/**
 * Created by Dakota on 11/12/2016.
 */

public class RefreshObject extends RealmObject {

    private String mClassTag;

    private long mRefreshTime;

    public RefreshObject() {
    }

    public String getClassTag() {
        return mClassTag;
    }

    public void setClassTag(String classTag) {
        mClassTag = classTag;
    }

    public long getRefreshTime() {
        return mRefreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        mRefreshTime = refreshTime;
    }
}
