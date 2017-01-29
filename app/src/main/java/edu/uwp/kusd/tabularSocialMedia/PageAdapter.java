// TODO: 10/3/2016 - Documentation

package edu.uwp.kusd.tabularSocialMedia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for an adapter for the TabLayout.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    List<SocialMediaFragment> fragments = new ArrayList<>();

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
                SocialMediaFragment facebookFragment = SocialMediaFragment.newInstance("https://m.facebook.com/kenoshaschools");
                fragments.add(facebookFragment);
                return facebookFragment;
            case 1:
                SocialMediaFragment twitterFragment = SocialMediaFragment.newInstance("https://mobile.twitter.com/kusd");
                fragments.add(twitterFragment);
                return twitterFragment;
            case 2:
                SocialMediaFragment youtubeFragment = SocialMediaFragment.newInstance("https://m.youtube.com/user/kenoshaschools");
                fragments.add(youtubeFragment);
                return youtubeFragment;
            case 3:
                SocialMediaFragment instagramFragment = SocialMediaFragment.newInstance("https://www.instagram.com/kenoshaschools/");
                fragments.add(instagramFragment);
                return instagramFragment;
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

    /**
     * Returns an existing fragment from the list of fragments
     *
     * @param index the index to retrieve from
     * @return a SocialMediaFragment
     */
    public SocialMediaFragment getFragment(int index) {
        return fragments.get(index);
    }
}
