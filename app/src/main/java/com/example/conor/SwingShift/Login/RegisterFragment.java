package com.example.conor.SwingShift.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.conor.SwingShift.Email.SendEmail;
import com.example.conor.SwingShift.HTTPCall;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by conor on 23/05/2016.
 */
public class RegisterFragment extends Fragment{

    BootstrapEditText etUserName;
    BootstrapEditText etEmployeeID;
    BootstrapEditText etPassWord;
    BootstrapButton regButton;
    HTTPCall httpCall = new HTTPCall(SwipeMainActivity.sContext);
    ProgressDialog progressDialog;
    ArrayList<Employee> ListOfEmployees = new ArrayList<Employee>();
    ParseEmployee parseResponse;
    Random random = new Random();
    int rand = random.nextInt(10000) + 1;
    String tempPassword = String.valueOf(rand);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_reg, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        etUserName = (BootstrapEditText) getView().findViewById(R.id.etUserName);
        etEmployeeID = (BootstrapEditText) getView().findViewById(R.id.etEmployeeID);
        regButton = (BootstrapButton) getView().findViewById(R.id.regButton);

        final String URL = "http://rosterrest.azurewebsites.net/api/employeevalues/getemployee";
        final String URL2 = "http://rosterrest.azurewebsites.net/api/employeevalues/putemployee";

        //    // TODO  PROGRESS BAR
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ... ");

        GET_EMPLOYEES_FROM_DB();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String email = etUserName.getText().toString(); // EMAIL FOR NOW
                final String employeeID = etEmployeeID.getText().toString();

                //TODO CHECK TO SEE IF EMPLOYEE EXISTS
                final Employee employee = CheckEmployeeID(employeeID);

                if (employee != null) {
                    // TODO SET EMPLOYEE PARAMETERS
                    employee.setEmail(email);
                    employee.setPassword(tempPassword);
                    // TODO UPDATE DB
                    httpCall.UPDATE_EMPLOYEE_IN_DB(employee, URL2, employee.getID());
                    // TODO SEND EMAIL
                    sendEmail();
                    progressDialog.hide();
                    Intent intent = new Intent(getActivity(), SwipeLogRegActivity.class);
                    startActivity(intent);
//
                } else {
                    progressDialog.hide();
                    Toast.makeText(getActivity(), "Error! Employee number invalid!", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

                        //Toast.makeText(getActivity(), "Reg request sent", Toast.LENGTH_SHORT).show();
                        CreateObjectFromResponse(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");


            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void CreateObjectFromResponse(String response){
        parseResponse = new ParseEmployee(response);
        ListOfEmployees = parseResponse.ParseEmp();

    }

    public ArrayList<Employee> getListOfEmployees(){
        return this.ListOfEmployees;
    }

    public Employee CheckEmployeeID(String employeeID){
        for(Employee emp : getListOfEmployees()){
            if (emp.getEmpID().equals(employeeID)){
                return emp;
            }
        }

        return null;
    }

    private void sendEmail() {
        //Getting content for email
        String email = etUserName.getText().toString().trim();
        String subject = "Swing Shift Account";
        String message = "Welcome to Swing Shift, "  + "\n" + "\n" +"Your temporary password is :" + tempPassword +
                "\n" + "Use this to log in to the Swing Shift app. Make sure you change this password once logged in!" + "\n" + "\n"
                + "Kindest regards, " + "\n" + "The Swing Shift Team :)";

        //Creating SendMail object
        SendEmail sm = new SendEmail(getActivity(), email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

}
