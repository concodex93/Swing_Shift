package com.example.conor.SwingShift.WalkThru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.PagerAdapter;
import com.example.conor.SwingShift.SwipeView.SlidingTabLayout;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;


public class IntroActivity extends FragmentActivity {

    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = (ViewPager) findViewById(R.id.view_pager3);
        IntroPager pagerAdapter = new IntroPager(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab3);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor("#FFFFFF");
            }

        });

        ImageView imageView = new ImageView (this);
        imageView.setImageResource(R.mipmap.homeee);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, SwipeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putBoolean("locked", true).apply();
    }


}
