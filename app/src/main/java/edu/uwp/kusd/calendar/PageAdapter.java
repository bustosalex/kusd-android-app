// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * A class for an adapter for the TabLayout.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    /**
     * The number of tabs in the layout.
     */
    int mNumOfTabs;

    /**
     * Constructs a PageAdapter for the TabLayout.
     *
     * @param fm         fragment manager
     * @param mNumOfTabs number of tabs
     */
    public PageAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    /**
     * Gets a fragment for a tab.
     *
     * @param position the position to get a fragment at
     * @return a fragment at the given position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EventsFragment eventsFragment = new EventsFragment();
                return eventsFragment;
            case 1:
                PdfCalendarsFragment pdfCalendarsFragment = new PdfCalendarsFragment();
                return pdfCalendarsFragment;
            default:
                return null;
        }
    }

    /**
     * Gets the number of tabs.
     *
     * @return the number of tabs
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
