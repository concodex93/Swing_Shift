package com.example.conor.SwingShift.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewConfiguration;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.PagerAdapter;
import com.example.conor.SwingShift.SwipeView.SlidingTabLayout;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;

import java.lang.reflect.Field;

public class SwipeLogRegActivity extends FragmentActivity {

    public static Context sContext;
    //TODO UI
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOverflowMenu();
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_swipe_log_reg);

        viewPager = (ViewPager) findViewById(R.id.view_pager2);
        PagerLogRegAdapter pagerAdapter = new PagerLogRegAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);

        SwipeMainActivity.sContext = getApplicationContext();
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab2);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor("#FFFFFF");
            }

        });

    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
