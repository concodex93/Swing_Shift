package com.example.conor.SwingShift.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.conor.SwingShift.HTTPCall;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;
import com.example.conor.SwingShift.WalkThru.IntroActivity;
import com.sandro.restaurant.Restaurant;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by conor on 23/05/2016.
 *
 *
 */
public class LoginFragment extends Fragment {

    BootstrapEditText etEmployeeID;
    BootstrapEditText loginPassWord;
    BootstrapButton loginButton;
    TextView regHere;
    ProgressDialog progressDialog;
    ParseEmployee parseResponse;
    HTTPCall httpCall = new HTTPCall(SwipeMainActivity.sContext);
    ArrayList<Employee> ListOfEmployees;
    Runnable mHandlerTask;
    Handler mHandler;
    public static String LoggedInUser;
    public static Employee LoggedInUser2;
    public static Boolean isReceived = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TypefaceProvider.registerDefaultIconSets();
        GET_EMPLOYEES_FROM_DB();
        mHandler = new Handler();
        mHandlerTask = new Runnable()
        {
            @Override
            public void run() {
                GET_EMPLOYEES_FROM_DB();
                mHandler.postDelayed(mHandlerTask, 1000 * 20);
            }
        };

        startRepeatingTask();
        etEmployeeID = (BootstrapEditText) getView().findViewById(R.id.loginUserName);
        loginPassWord = (BootstrapEditText) getView().findViewById(R.id.loginPassWord);
        loginButton = (BootstrapButton) getView().findViewById(R.id.loginButton);
        //regHere = (TextView) getView().findViewById(R.id.regHere);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ... ");

        try {
            // TODO LOGIN BUTTON
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();

                    final String employeeID = etEmployeeID.getText().toString();
                    final String password = loginPassWord.getText().toString();

                    //TODO CHECK TO SEE IF EMPLOYEE EXISTS

                    Employee employee = CheckEmployeeID(employeeID);

                    if (employee != null) {
                        if (password.equals(employee.getPassword())) {
                            // TODO GO TO MAIN ACTIVITY
                            if (isReceived) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                boolean locked = prefs.getBoolean("locked", false);
                                //boolean locked =false;
                                if (locked) {
                                    Intent intent = new Intent(getActivity(), SwipeMainActivity.class);
                                    startActivity(intent);
                                    LoggedInUser = employee.getEmpID();
                                    LoggedInUser2 = employee;
                                    progressDialog.hide();
                                } else {
                                    Intent mIntent = new Intent(getActivity(),IntroActivity.class);
                                    LoggedInUser = employee.getEmpID();
                                    LoggedInUser2 = employee;
                                    progressDialog.hide();
                                    startActivity(mIntent);
                                }

                            }
                            else{
                                new Restaurant(getActivity(), "Error! Connection error! Try again.", Snackbar.LENGTH_LONG).setBackgroundColor(Color.GRAY).show();
//                                Toast.makeText(getActivity(), "Error! Connection error! Try again.", Toast.LENGTH_LONG).show();
                                GET_EMPLOYEES_FROM_DB();
                                progressDialog.hide();
                            }
                        } else {
                            //new Restaurant(getActivity(), "Password is incorrect.", Snackbar.LENGTH_LONG).setBackgroundColor(Color.GRAY).show();
                            Toast.makeText(getActivity(), "Error! Password is incorrect!", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    } else {
                        //new Restaurant(getActivity(), "Employee Number is incorrect!", Snackbar.LENGTH_LONG).setBackgroundColor(Color.GRAY).show();
                        Toast.makeText(getActivity(), "Error! Employee Number is incorrect!", Toast.LENGTH_LONG).show();
                        GET_EMPLOYEES_FROM_DB();
                        progressDialog.hide();

                    }
                }
            });

        } catch (Exception e){
            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), SwipeLogRegActivity.class);
            startActivity(intent);
        }
   }

    public void GET_EMPLOYEES_FROM_DB() {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(SwipeMainActivity.sContext);

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/employeevalues/getemployees";

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(getActivity(), "REQUEST SENT!", Toast.LENGTH_LONG).show();
                        CreateObjectFromResponse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");

            }
        });
        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        queue.add(stringRequest);
    }

    public void CreateObjectFromResponse(String response){
        parseResponse = new ParseEmployee(response);
        ListOfEmployees = parseResponse.ParseEmp();
        if (ListOfEmployees!=null){
            isReceived = true;
        }

    }

    public ArrayList<Employee> getListOfEmployees(){
        return ListOfEmployees;
    }

    public Employee CheckEmployeeID(String employeeID){
        if (getListOfEmployees()!= null) {
            for (Employee emp : getListOfEmployees()) {
                if (emp != null) {
                    if (employeeID.equals(emp.getEmpID())) {
                        return emp;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        startRepeatingTask();
    }


    public void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    public void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "I STOPPED", Toast.LENGTH_SHORT).show();
        stopRepeatingTask();
    }

}

