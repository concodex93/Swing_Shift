package com.example.conor.SwingShift.SwipeView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.Locale;

/**
 * Created by conor on 06/04/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) { // create corresponding fragment object

        switch (position){
            case 0:
                return new ShiftFragment();
            case 1:
                return new SwapFragment();
            case 2:
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {  // This will generate the number of views for fragments
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale locale = Locale.getDefault();
        switch (position) {
            case 0:
                return "Shifts";
            case 1:
                return "Swaps";
        }

        return null;
    }
}
