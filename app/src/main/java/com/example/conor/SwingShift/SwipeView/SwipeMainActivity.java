package com.example.conor.SwingShift.SwipeView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.conor.SwingShift.MenuButton.HelpMeActivity;
import com.example.conor.SwingShift.MenuButton.mSettingActivity;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.WalkThru.IntroActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class SwipeMainActivity extends FragmentActivity {

    public static Context sContext;
    //TODO UI
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_main);
        TypefaceProvider.registerDefaultIconSets();


        viewPager = (ViewPager) findViewById(R.id.view_pager);
                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);

        SwipeMainActivity.sContext = getApplicationContext();
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor("#FFFFFF");
            }

        });

        ImageView imageView = new ImageView (SwipeMainActivity.this);
        imageView.setImageResource(R.mipmap.linem);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        //actionButton.setBackgroundResource(R.mipmap.userm);

//        ImageView itemIcon1 = new ImageView(this);
//        itemIcon1.setImageResource(R.mipmap.help);
//
//        ImageView itemIcon2 = new ImageView(this);
//        itemIcon2.setImageResource(R.mipmap.user);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton button1 = itemBuilder.build();
        button1.setBackgroundResource(R.mipmap.pass);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeMainActivity.this, mSettingActivity.class);
                startActivity(intent);
            }
        });

        SubActionButton button2 = itemBuilder.build();
        button2.setBackgroundResource(R.mipmap.helpp);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeMainActivity.this, HelpMeActivity.class);
                startActivity(intent);
            }
        });

        SubActionButton button3 = itemBuilder.build();
        button3.setBackgroundResource(R.mipmap.manuall);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeMainActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(SwipeMainActivity.this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();

    }


}
