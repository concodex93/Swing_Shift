package com.example.conor.SwingShift;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.conor.SwingShift.Login.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by conor on 18/05/2016.
 */
public class HTTPCall extends Application {

    // TODO CLASSES
    private Context context;
    ParseResponseShift parseResponse;

    // TODO DATA STRUCTURES
    ArrayList<Shift> ListOfShifts;

    //TODO SHIFT
    public static final String KEY_EMPLOYEEID = "EmployeeID";
    public static final String KEY_ID_SHIFT = "ID";
    public static final String KEY_START_TIME = "StartTime";
    public static final String KEY_END_TIME = "EndTime";
    public static final String KEY_DAY = "Day";
    public static final String KEY_DATE = "Date";

    //TODO SWAP
    public static final String KEY_SWAPID = "ID";
    public static final String KEY_EMPLOYEEID_SWAP = "EmployeeID";
    public static final String KEY_SHIFT_ID_SWAP = "ShiftID";
    public static final String KEY_EMP_SWAPID = "EmployeeSwapID";
    public static final String KEY_SHIFT_TO_SWAPID = "ShiftToSwapID";

    //TODO EMPLOYEE
    public static final String KEY_ID_EMP = "ID";
    public static final String KEY_EMPID = "EmpID";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_CONTRACTHOURS = "ContractedHours";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_LASTNAME = "SecondName";
    public static final String KEY_JOBTITLE = "JobTitle";


    public HTTPCall(Context c){
        this.context = c;
    }


    public void POST(final Shift shift, String URL){

        // TODO  Request a string response from the provided URL.
        //String url = "http://rosterrest.azurewebsites.net/api/swapvalues/PostSwap";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //String s = String.valueOf(error.networkResponse.statusCode);
                    }
                }){
            @Override
            public Map<String,String> getParams() throws AuthFailureError{

                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_EMPLOYEEID, shift.getEmployeeID());
                params.put(KEY_SHIFT_ID_SWAP, shift.getID()); // TODO ID IS NULL
                params.put("LookingForShift", String.valueOf(true));
                return params;
            }

        };
        stringRequest.setShouldCache(false);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void UPDATE_SWAP_IN_DB(final Swap swap, final Shift shift, String URL, String ID){

        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL + "/" + ID;

        // TODO  Request a string response from the provided URL.
        //String url = "http://rosterrest.azurewebsites.net/api/swapvalues/PutSwap/"+ID;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {

                Map<String, String>  params = new HashMap<String, String> ();

                params.put(KEY_SWAPID, swap.getID());
                params.put(KEY_EMPLOYEEID_SWAP, swap.getEmployeedID());
                params.put(KEY_SHIFT_ID_SWAP, swap.getShiftID());
                params.put(KEY_EMP_SWAPID, shift.getEmployeeID());
                params.put(KEY_SHIFT_TO_SWAPID, shift.getID());
                params.put("LookingForShift", String.valueOf(swap.isLookingForShift()));
                params.put("Authorized", "false");
                return params;
            }

        };

        queue.add(putRequest);
    }

    public void UPDATE_SHIFT_IN_DB(final Shift shift_new, final Shift shift_old, String URL, String ID){

        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL + "/" + ID;

        // TODO  Request a string response from the provided URL.
        //String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/putshift/"+ID;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {

                Map<String, String>  params = new HashMap<String, String>();

                params.put(KEY_ID_SHIFT, shift_old.getID());
                params.put(KEY_EMPLOYEEID, shift_old.getEmployeeID());
                params.put(KEY_START_TIME, shift_new.getTimeOfShift());
                params.put(KEY_DAY, shift_new.getDay());
                params.put(KEY_DATE, shift_new.getDate());
                params.put("genSwingPhoto", String.valueOf(shift_new.isGenSwingPhoto()));
                params.put("genStarPhoto", String.valueOf(shift_new.isGenStarPhoto()));

                return params;
            }

        };

        queue.add(putRequest);
    }

    public void UPDATE_SHIFT_IN_DB(final Shift shift_new, String URL, String ID){

        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL + "/" + ID;

        // TODO  Request a string response from the provided URL.
        //String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/putshift/"+ID;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {

                Map<String, String>  params = new HashMap<String, String>();

                params.put(KEY_ID_SHIFT, shift_new.getID());
                params.put(KEY_EMPLOYEEID, shift_new.getEmployeeID());
                params.put(KEY_START_TIME, shift_new.getTimeOfShift());
                params.put(KEY_DAY, shift_new.getDay());
                params.put(KEY_DATE, shift_new.getDate());
                params.put("genSwingPhoto", String.valueOf(shift_new.isGenSwingPhoto()));
                params.put("genStarPhoto", String.valueOf(shift_new.isGenStarPhoto()));

                return params;
            }

        };

        queue.add(putRequest);
    }

    public void UPDATE_EMPLOYEE_IN_DB(final Employee employee, String URL, String ID){

        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/employeevalues/Putemployee/"+ID;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){

            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {

                Map<String, String>  params = new HashMap<String, String> ();

                params.put(KEY_ID_EMP, employee.getID());
                params.put(KEY_EMPID, employee.getEmpID());
                params.put(KEY_PASSWORD, employee.getPassword());
                params.put(KEY_CONTRACTHOURS, employee.getContractedHours());
                params.put(KEY_USERNAME, employee.getEmail());
                params.put(KEY_FIRSTNAME, employee.getFirstName());
                params.put(KEY_LASTNAME, employee.getSecondName());
                params.put(KEY_JOBTITLE, employee.getJobTitle());


                return params;
            }

        };

        queue.add(putRequest);
    }
}
