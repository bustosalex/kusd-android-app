package edu.uwp.kusd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.uwp.kusd.schools.CharterFragment;
import edu.uwp.kusd.schools.DistrictFragment;
import edu.uwp.kusd.schools.ElemFragment;
import edu.uwp.kusd.schools.HighFragment;
import edu.uwp.kusd.schools.MiddleFragment;
import edu.uwp.kusd.schools.SchoolCategoryFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ElemFragment tab1 = new ElemFragment();
                return tab1;
            case 1:
                MiddleFragment tab2 = new MiddleFragment();
                return tab2;
            case 2:
                HighFragment tab3 = new HighFragment();
                return tab3;
            case 3:
                CharterFragment tab4 = new CharterFragment();
                return tab4;
            case 4:
                DistrictFragment tab5 = new DistrictFragment();
                return tab5;
            case 5:
                SchoolCategoryFragment choiceSchools = SchoolCategoryFragment.newInstance(5);
                return choiceSchools;
            case 6:
                SchoolCategoryFragment specialtySchools = SchoolCategoryFragment.newInstance(6);
                return specialtySchools;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}