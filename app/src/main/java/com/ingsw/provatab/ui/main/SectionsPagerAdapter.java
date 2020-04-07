package com.ingsw.provatab.ui.main;

import android.content.Context;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.ingsw.provatab.Cart;
import com.ingsw.provatab.Contact;
import com.ingsw.provatab.HomePage;
import com.ingsw.provatab.Profile;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{0,1,2,3};
    private final Context mContext;
    private final FragmentManager fragmentManager;
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentManager=fm;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                return new HomePage();
            case 1:
                return new Contact();
            case 2:
                return new Cart();
            case 3:
                return new Profile();
            default:
                return null;
        }

//return new EmptyFragment();
    }



    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
        }
        return null;
    }
}