package com.example.conor.SwingShift.WalkThru;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.conor.SwingShift.MenuButton.PageIntoOne;

import java.util.Locale;


/**
 * Created by conor on 02/06/2016.
 */
public class IntroPager extends FragmentPagerAdapter {

    public IntroPager(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new IntroOne();
            case 1:
                return new IntroTwo();
            case 2:
                return new IntroThree();
            case 3:
                return new IntroFour();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
