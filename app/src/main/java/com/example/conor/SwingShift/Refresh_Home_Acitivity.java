package com.example.conor.SwingShift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;

public class Refresh_Home_Acitivity extends AppCompatActivity {

    Button retryBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh__home__acitivity);

        retryBtn2 = (Button) findViewById(R.id.retryBtn2);

        retryBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Refresh_Home_Acitivity.this, SwipeMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
