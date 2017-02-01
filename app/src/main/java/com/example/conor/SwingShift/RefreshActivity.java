package com.example.conor.SwingShift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;

public class RefreshActivity extends AppCompatActivity {

    //TODO UI
    Button retryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        retryBtn = (Button) findViewById(R.id.retryBtn);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RefreshActivity.this, SwipeMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
