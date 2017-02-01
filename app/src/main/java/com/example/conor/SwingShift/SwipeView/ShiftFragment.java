package com.example.conor.SwingShift.SwipeView;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.conor.SwingShift.HTTPCall;
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.MyInternalStorage;
import com.example.conor.SwingShift.ParseResponseShift;
import com.example.conor.SwingShift.ParseResponseSwap;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.RefreshActivity;
import com.example.conor.SwingShift.Shift;
import com.example.conor.SwingShift.Swap;
import com.example.conor.SwingShift.ViewShiftAdapter;
import com.sandro.restaurant.Restaurant;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by conor on 19/05/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShiftFragment extends android.support.v4.app.Fragment {

    // TODO UI
    ListView listView;

    // TODO DATA STRUCTURES
    ArrayList<Shift> LoadedShifts = new ArrayList<Shift>();
    ArrayList<Shift> ListOfShifts;
    ArrayList<Shift> ListOfShifts2;
    ArrayList<Swap> ListOfSwaps;


    // TODO CLASSES
    ProgressDialog progressDialog;
    ParseResponseShift parseResponse;
    Shift shift;
    HTTPCall httpCall = new HTTPCall(SwipeMainActivity.sContext);
    public static final String FILE_NAME = "mFile919.txt";
    MyInternalStorage mStorage = new MyInternalStorage(SwipeMainActivity.sContext);
    ParseResponseSwap responseSwap;
    //TODO  INTENT KEYS
    String KEY = "SWAP";
    boolean refreshData;

    //TODO API URLs
    public static final String URL_POST_SWAP = "http://rosterrest.azurewebsites.net/api/swapvalues/PostSwap";
    public static final String URL_PUT_SHIFT = "http://rosterrest.azurewebsites.net/api/shiftvalues/putshift";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO  PROGRESS BAR
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ... ");
        progressDialog.show();

        // TODO INSTANCIATE UI AND OTHER
        listView = (ListView)getView().findViewById(R.id.listView3);

        if (listView != null) {
            listView.setLongClickable(true);
        }

        // TODO SET LONG ON CLICK FOR LIST
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                shift = (Shift) listView.getItemAtPosition(position);
                setShift(shift);
                AlertForListView();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SingleRowActivity.class);
                shift = (Shift) listView.getItemAtPosition(position);
                // puts shift object in intent and assigns key value associated with it
                intent.putExtra("TEMPSHIFT", shift);
                startActivity(intent);
            }
        });

            try {
                // get data from intent
                Intent mIntend = getActivity().getIntent();
                // assign value from intent extras
                refreshData = mIntend.getExtras().getBoolean("REFRESH", false);
                if (refreshData) {
                    // make Volley call
                    GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
                    progressDialog.hide();
                }
            }
            catch (NullPointerException e){}
            try
            {
                // TODO MAKE API CALL
                if ((LoadedShifts = mStorage.Load(FILE_NAME)).size() > 0) {
                    ListOfShifts2 = new ArrayList<Shift>();
                    for (Shift s : LoadedShifts) {
                        ListOfShifts2.add(s);
                    }
                    listView.setAdapter(new ViewShiftAdapter(getActivity(), ListOfShifts2));
                    progressDialog.hide();
                } else {
                    GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
                    progressDialog.hide();
                }
            }
            catch (IndexOutOfBoundsException | NullPointerException e)
            {
            GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
            progressDialog.hide();
            }

    }

    // TODO STRING RESPONSE
    public void GET_SHIFTS_FROM_DB(String id) {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/getshiftwithempid/" +id;

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // TODO CREATE OBJECT FROM PARSED DATA
                       // Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                        CreateObjectFromResponse(response);
                        // TODO PARSE TO JSON TEST
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");
                Toast.makeText(getActivity(), RESPONSE, Toast.LENGTH_SHORT).show();
                progressDialog.hide();

            }
        });
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void GET_SWAPS_FROM_DB() {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/swapvalues/getswaps";

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), "GET SWAPS SWAP", Toast.LENGTH_SHORT).show();
                        // TODO CREATE OBJECT FROM PARSED DATA
                        CreateSwapFromResponse(response);
                        // TODO PARSE TO JSON TEST

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");
                Intent intent = new Intent(getActivity(), RefreshActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), RESPONSE, Toast.LENGTH_SHORT).show();
                progressDialog.hide();

            }
        });
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void CreateSwapFromResponse(String response){
        responseSwap = new ParseResponseSwap(response);
        //TODO Added to list of swappable shifts
        if (response!=null) {
            ListOfSwaps = responseSwap.ParseSWAP();
        }
    }

    public void CreateObjectFromResponse(String response){
        parseResponse = new ParseResponseShift(response);
        ListOfShifts = parseResponse.Parse();

        File dir = getActivity().getFilesDir();
        File file = new File(dir, FILE_NAME);
        if (file.isFile()) {
            if (file.exists()) {
                boolean deleted = getActivity().deleteFile(FILE_NAME);
            }
        }

        mStorage.Save(ListOfShifts, FILE_NAME);
        // TODO pass data to Adapter
        listView.setAdapter(new ViewShiftAdapter(getActivity(), ListOfShifts));

    }

    public void AlertForListView() {
        GET_SWAPS_FROM_DB();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to 'Swing' this shift?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                            // checks to see if swap alreay exists
                            if (doesSwapExist(getShift())!=null){
                            boolean swapExist = doesSwapExist(getShift());
                            if (!swapExist) { // if swap doesn't exist already
                                if(!getShift().isGenStarPhoto()) { // cannot swap a shift from a previous swap
                                    // Hash table of all months
                                    Hashtable<String,Integer> MonthHastTable = new Hashtable<String,Integer>() {{
                                        put("January",      1);
                                        put("February",     2);
                                        put("March",        3);
                                        put("April",        4);
                                        put("May",          5);
                                        put("June",         6);
                                        put("July",         7);
                                        put("August",       8);
                                        put("September",    9);
                                        put("October",      10);
                                        put("November",     11);
                                        put("December",     12);
                                    }};
                                    // Stores current date and month into a string array
                                    String [] dateShiftArray = getShift().getDate().split("/");
                                    double date = System.currentTimeMillis();
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM, yyyy");
                                    String dateString = sdf1.format(date);
                                    String [] actualDateArray = dateString.split(" ");
                                    // get values out of arrays and compare them
                                    int shiftDate = Integer.parseInt(dateShiftArray[0]);
                                    int currentDate = Integer.parseInt(actualDateArray[0]);
                                    int shiftMonth = Integer.parseInt(dateShiftArray[1]);
                                    String Month = actualDateArray[1].replace(",", "");
                                    int currentMonth = MonthHastTable.get(Month);

                                    // if shift data is correct
                                    if (shiftMonth > currentMonth){

                                            Shift updatedShift = getShift();
                                            // POST shift to swaps table
                                            httpCall.POST(getShift(), URL_POST_SWAP);
                                            updatedShift.setGenSwingPhoto(true);
                                            // update shift in DB
                                            httpCall.UPDATE_SHIFT_IN_DB(updatedShift, getShift(), URL_PUT_SHIFT, getShift().getID());
                                            Intent intent = new Intent(getActivity(), SwingInMotionActivity.class);
                                            GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
                                            new Restaurant(getActivity(), "Shift successfully added to swaps!", Snackbar.LENGTH_LONG)
                                                .setTextColor(Color.WHITE).show();
                                            startActivity(intent);

                                    }
                                    else if (shiftDate > currentDate && shiftMonth == currentMonth){

                                        Shift updatedShift = getShift();
                                        // POST shift to swaps table
                                        httpCall.POST(getShift(), URL_POST_SWAP);
                                        // Change status of shift
                                        updatedShift.setGenSwingPhoto(true);
                                        // update shift in DB
                                        httpCall.UPDATE_SHIFT_IN_DB(updatedShift, getShift(), URL_PUT_SHIFT, getShift().getID());
                                        new Restaurant(getActivity(), "Shift successfully added to swaps!", Snackbar.LENGTH_LONG)
                                                .setTextColor(Color.WHITE).show();
                                        // go to new Activity
                                        Intent intent = new Intent(getActivity(), SwingInMotionActivity.class);
                                        GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
                                        startActivity(intent);
                                    }
                                    else{
                                        new Restaurant(getActivity(), "Cannot Swing a shift with current date!", Snackbar.LENGTH_LONG)
                                                .setTextColor(Color.WHITE).show();

                                    }
                                }
                                else{
                                    new Restaurant(getActivity(), "Cannot swap the same shift more then once! Select another shift", Snackbar.LENGTH_LONG)
                                            .setTextColor(Color.WHITE).show();

                                }
                            } else{
                                new Restaurant(getActivity(), "Swap already pending. Select another shift", Snackbar.LENGTH_LONG)
                                        .setTextColor(Color.WHITE).show();
                            }
                         } else{
                                new Restaurant(getActivity(), "Connection Error!", Snackbar.LENGTH_LONG)
                                        .setTextColor(Color.WHITE).show();
                                GET_SWAPS_FROM_DB();
                            }

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setTitle("Alert!")
                .setIcon(R.mipmap.swingtransparent);
        AlertDialog alert = builder.create();
        alert.show();
    }

    // TODO get Shift Object
    public Shift getShift(){
        Shift shift = this.shift;
        return shift;
    }

    public void setShift(Shift s){
        this.shift = s;
    }

    @Override
    public void onPause() {
        super.onPause();
        File dir = getActivity().getFilesDir();
        File file = new File(dir, FILE_NAME);
        if (file.isFile()) {
            if (file.exists()) {
                boolean deleted = getActivity().deleteFile(FILE_NAME);
            }
        }
        try {
            mStorage.Save(this.ListOfShifts, FILE_NAME);
        } catch(NullPointerException e){

        }
    }

    public Boolean doesSwapExist(Shift shiftToPush) {
        if (this.ListOfSwaps != null) {
            for (Swap s : this.ListOfSwaps) {
                if (s != null) {
                    if (shiftToPush.getID().equals(s.getShiftID())) {
                        return true;
                    }
                }
            }

            return false;
        }
        return null;
    }

}






