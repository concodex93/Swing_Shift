package com.example.conor.SwingShift.MenuButton;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

public class HelpMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me);
        setTitle("Help & Support");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView helpTv = (TextView) findViewById(R.id.helpTv);
        helpTv.setTypeface(typeface);

        BootstrapButton bootstrapButton = (BootstrapButton) findViewById(R.id.redirectButton);
        bootstrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://swingshift.azurewebsites.net/"));
                startActivity(viewIntent);
            }
        });

        ImageView imageView = new ImageView (HelpMeActivity.this);
        imageView.setImageResource(R.mipmap.homeee);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMeActivity.this, SwipeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
