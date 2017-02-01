package com.example.conor.SwingShift.MenuButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.conor.SwingShift.HTTPCall;
import com.example.conor.SwingShift.Login.Employee;
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

public class mSettingActivity extends AppCompatActivity {

    BootstrapButton settingsButton;
    BootstrapEditText currentPassword;
    BootstrapEditText newPassword;
    BootstrapEditText conPassword;

    HTTPCall httpCall = new HTTPCall(this);
    final String URL2 = "http://rosterrest.azurewebsites.net/api/employeevalues/putemployee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_setting);
        setTitle("Reset Password");

        currentPassword = (BootstrapEditText) findViewById(R.id.currentPassword);
        newPassword = (BootstrapEditText) findViewById(R.id.newPassword);
        conPassword = (BootstrapEditText) findViewById(R.id.conPassword);
        settingsButton = (BootstrapButton) findViewById(R.id.settingButton);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _currPassword = currentPassword.getText().toString();
                String _newPassword = newPassword.getText().toString();
                String _conPassword = conPassword.getText().toString();
                Employee emp = LoginFragment.LoggedInUser2;
                if (_currPassword.equals(emp.getPassword())) {

                    if (_newPassword.equals(_conPassword)) {
                        emp.setPassword(_newPassword);
                        httpCall.UPDATE_EMPLOYEE_IN_DB(emp, URL2, emp.getID());
                        Toast.makeText(mSettingActivity.this, "Password successfully changed!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mSettingActivity.this, SwipeMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mSettingActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mSettingActivity.this, "Your current password is incorrect!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView imageView = new ImageView (mSettingActivity.this);
        imageView.setImageResource(R.mipmap.homeee);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mSettingActivity.this, SwipeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
