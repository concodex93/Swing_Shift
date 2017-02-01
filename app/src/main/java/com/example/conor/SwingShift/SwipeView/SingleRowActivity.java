package com.example.conor.SwingShift.SwipeView;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.conor.SwingShift.HTTPCall;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.Shift;
import com.example.conor.SwingShift.WalkThru.IntroActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.SimpleDateFormat;

public class SingleRowActivity extends AppCompatActivity {

    // TODO UI
    TextView dayTVsr;
    TextView dateTVsr;
    TextView startTimeTVsr;
    ImageView imageView9;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_row);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        dateTVsr = (TextView) findViewById(R.id.dateTVsr);
        dayTVsr = (TextView) findViewById(R.id.dayTVsr);
        startTimeTVsr = (TextView) findViewById(R.id.startTimeTVsr);

        final Intent intent = getIntent();
        final Shift shift = (Shift) intent.getSerializableExtra("TEMPSHIFT");

        dateTVsr.setText(shift.getDate());
        dateTVsr.setTypeface(typeface);
        dateTVsr.setTextColor(Color.BLACK);

        dayTVsr.setText(shift.getDay());
        dayTVsr.setTypeface(typeface);
        dayTVsr.setTextColor(Color.BLACK);

        startTimeTVsr.setText(shift.getTimeOfShift());
        startTimeTVsr.setTypeface(typeface);
        startTimeTVsr.setTextColor(Color.BLACK);

        imageView9 = (ImageView) findViewById(R.id.imageView9);

        String [] dateArray = shift.getDate().split("/");

        double date = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf1.format(date);
        String [] acutaulDateArray = dateString.split(" ");

        try {

            if((Integer.parseInt(dateArray[0])) == Integer.parseInt(acutaulDateArray[2].replace(",", ""))){
                imageView9.setImageResource(R.mipmap.alarm);

            }

            else if (shift.isGenSwingPhoto() && !shift.isGenStarPhoto()) {
                imageView9.setImageResource(R.mipmap.swingtransparent);
            }

            else if(shift.isGenStarPhoto() && !shift.isGenSwingPhoto()){
                imageView9.setImageResource(R.mipmap.star_fil);
            }

            else if(!shift.isGenSwingPhoto() && !shift.isGenStarPhoto()) {
                imageView9.setImageResource(R.mipmap.staruff);
            }


        } catch (NullPointerException e){
            // pass
        }

        ImageView imageView = new ImageView (this);
        imageView.setImageResource(R.mipmap.homeee);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleRowActivity.this, SwipeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
