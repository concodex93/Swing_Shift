package com.example.conor.SwingShift.SwipeView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.ShiftActivity;
import com.example.conor.SwingShift.MyInternalStorage;
import com.example.conor.SwingShift.ParseResponseShift;
import com.example.conor.SwingShift.ParseResponseSwap;
import com.example.conor.SwingShift.R;
import com.example.conor.SwingShift.RefreshActivity;
import com.example.conor.SwingShift.Shift;
import com.example.conor.SwingShift.Swap;
import com.example.conor.SwingShift.SwapShiftAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by conor on 19/05/2016.
 */
public class SwapFragment extends Fragment {


    // TODO UI
    ListView listView2;

    // TODO DATA STRUCTURES
    ArrayList<Swap> ListOfSwaps = new ArrayList<Swap>();
    ArrayList<Shift> ListOfShifts = new ArrayList<Shift>();
    ArrayList<Shift> LoadedShifts = new ArrayList<Shift>();
    ArrayList<Shift> shiftsLoadMethod = new ArrayList<Shift>();
    ArrayList<Shift> ListOfConvertedSwapsToShifts;
    Runnable mHandlerTask;
    Handler mHandler;
    // TODO CLASSES
    String KEY = "name_of_extra";
    ParseResponseShift parseResponse;
    ProgressDialog progressDialog;
    ParseResponseSwap parseResponseSwap;
    public static final String FILE_NAME1 = "mFile819.txt";
    MyInternalStorage mStorage = new MyInternalStorage(SwipeMainActivity.sContext);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swap_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mHandler = new Handler();
        mHandlerTask = new Runnable()
        {
            @Override
            public void run() {
                GET_SHIFT_FROM_DB();
                GET_SWAPS_FROM_DB();
                mHandler.postDelayed(mHandlerTask, 1000 * 60);
            }
        };

        // TODO  PROGRESS BAR
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ... ");
        progressDialog.show();

        listView2 = (ListView) getView().findViewById(R.id.listView4);

        try {
            // checks to see if the file contains data
            if ((LoadedShifts = mStorage.Load(FILE_NAME1)).size() > 0) {
                shiftsLoadMethod = new ArrayList<Shift>();
                for (Shift s : LoadedShifts) {
                    shiftsLoadMethod.add(s);
                }
                // get swaps from DB
                GET_SWAPS_FROM_DB();
                // put list of shifts into adapter
                listView2.setAdapter(new SwapShiftAdapter(getActivity(), shiftsLoadMethod));
                progressDialog.hide();
             // if no data found, make rest calls
            } else {
                // get swaps from DB
                GET_SWAPS_FROM_DB();
                // get shifts from DB
                GET_SHIFT_FROM_DB();
            }
        } catch (IndexOutOfBoundsException | NullPointerException e){

            GET_SWAPS_FROM_DB();
            GET_SHIFT_FROM_DB();
        }

        if (listView2 != null) {
            listView2.setLongClickable(true);
        }

        // TODO SET LONG ON CLICK FOR LIST
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Shift shift = (Shift) listView2.getItemAtPosition(position);
                Swap swap = ConvertShiftToSwap(shift);
                AlertForListView(swap);
                return true;
            }
        });
    }

    public void AlertForListView(final Swap swap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want this shift?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), ShiftActivity.class);
                        // Puts swap object into intent
                        intent.putExtra("SWAP", swap);
                        startActivity(intent);


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
                       // Toast.makeText(getActivity(), "GET SWAPS SWAP", Toast.LENGTH_SHORT).show();
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

    public void GET_SHIFT_FROM_DB() {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/GetShifts";

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO CREATE OBJECT FROM PARSED DATA
                        CreateShiftFromResponse(response);

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

    public void CreateSwapFromResponse(String response){
        parseResponseSwap = new ParseResponseSwap(response);
        //TODO Added to list of swappable shifts
        ListOfSwaps = parseResponseSwap.ParseSWAP();
        // TODO pass data to Adapter
        ArrayList<Shift> tempList = new ArrayList<Shift>();
        for (Swap s : ListOfSwaps){
            if (s!= null) {
                Shift shift = ConvertSwapToShift(s);
                if (s.isLookingForShift()) {
                    if(!s.isAuthorized()) {
                        // checks varies parameters, only adds shifts to list that meet requirements
                        if (!LoginFragment.LoggedInUser.equals(s.getEmployeedID())) {
                            tempList.add(shift);
                        }
                    }
                }
            }
        }
        ListOfConvertedSwapsToShifts = tempList;
        try{
        File dir = getActivity().getFilesDir();
        File file = new File(dir, FILE_NAME1);
        if (file.isFile()) {
            if (file.exists()) {
                boolean deleted = getActivity().deleteFile(FILE_NAME1);
            }
        }
            mStorage.Save(this.ListOfConvertedSwapsToShifts, FILE_NAME1);
        } catch (NullPointerException e){
           //pass
        }
        listView2.setAdapter(new SwapShiftAdapter(getActivity(), ListOfConvertedSwapsToShifts));
        progressDialog.hide();
    }

    public void CreateShiftFromResponse(String response){
        parseResponse = new ParseResponseShift(response);
        //TODO Added to list of swappable shifts
        this.ListOfShifts = parseResponse.Parse();
        progressDialog.hide();

    }

    public Shift ConvertSwapToShift(Swap s){
        for (Shift shift : this.ListOfShifts){
            if (s.getEmployeedID().equals(shift.getEmployeeID())){
                if(s.getShiftID().equals(shift.getID())){
                    return shift;
                }
            }
        }

        return null;
    }

    public Swap ConvertShiftToSwap(Shift shift){
        for (Swap s : this.ListOfSwaps){
            if (s.getEmployeedID().equals(shift.getEmployeeID())){
                if(s.getShiftID().equals(shift.getID())){
                    return s;
                }
            }
        }

        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        File dir = getActivity().getFilesDir();
        File file = new File(dir, FILE_NAME1);
        // File normal file?
        if (file.isFile()) {
            // Does file exist?
            if (file.exists()) {
                // delete file
                boolean deleted = getActivity().deleteFile(FILE_NAME1);
            }
        }
        try
        {
            // create and save to new file
            mStorage.Save(ListOfConvertedSwapsToShifts, FILE_NAME1);
        }
        catch (NullPointerException e){}
        finally
        {
            stopRepeatingTask();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
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
    public void onResume() {
        super.onResume();
        startRepeatingTask();
    }
}
