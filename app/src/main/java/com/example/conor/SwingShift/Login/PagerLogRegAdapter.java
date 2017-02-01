package com.example.conor.SwingShift.Login;

/**
 * Created by conor on 24/05/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.Locale;


public class PagerLogRegAdapter extends FragmentPagerAdapter {

    public PagerLogRegAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) { // create corresponding fragment object

        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
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
                return "Log In";
            case 1:
                return "Register ";


        }

        return null;
    }
}
