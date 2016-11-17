package edu.uwp.kusd;

/**
 * Created by Dakota on 11/5/2016.
 */

import android.content.Context;

import io.realm.RealmResults;

public class RealmPdfCalendarAdapter extends RealmModelAdapter<PdfCalendar> {

    public RealmPdfCalendarAdapter(Context context, RealmResults<PdfCalendar> realmResults) {

        super(context, realmResults);
    }
}