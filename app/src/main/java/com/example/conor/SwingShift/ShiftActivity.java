package com.example.conor.SwingShift;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.SwipeView.ShiftFragment;
import com.example.conor.SwingShift.SwipeView.SwipeMainActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.sandro.restaurant.Restaurant;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;


public class ShiftActivity extends AppCompatActivity {

    // TODO UI
    ListView listView;

    // TODO DATA STRUCTURES
    ArrayList<Shift> LoadedShifts = new ArrayList<Shift>();
    ArrayList<Shift> ListOfShifts;
    ArrayList<Shift> ListOfAllShifts;

    // TODO CLASSES
    ProgressDialog progressDialog;
    ParseResponseShift parseResponse;
    Shift shift;
    HTTPCall httpCall = new HTTPCall(ShiftActivity.this);
    MyInternalStorage mStorage = new MyInternalStorage(this);

    //TODO  INTENT KEYS
    String KEY = "SWAP";

    //TODO API URLs
    public static final String URL_PUT_SWAP = "http://rosterrest.azurewebsites.net/api/swapvalues/PutSwap";
    public static final String URL_PUT_SHIFT = "http://rosterrest.azurewebsites.net/api/shiftvalues/putshift";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Shifts");

        // TODO  PROGRESS BAR
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ... ");
        progressDialog.show();

        ImageView imageView = new ImageView (this);
        imageView.setImageResource(R.mipmap.homeee);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShiftActivity.this, SwipeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // TODO INSTANCIATE UI AND OTHER
        listView = (ListView) findViewById(R.id.listView);

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
                shift = (Shift) listView.getItemAtPosition(position);
                setShift(shift);
                AlertForListView();
            }
        });

        // TODO MAKE API CALL
        try {
            if ((LoadedShifts = mStorage.Load(ShiftFragment.FILE_NAME)).size() > 0) {
                ListOfShifts = new ArrayList<Shift>();
                for (Shift s : LoadedShifts) {
                    ListOfShifts.add(s);
                }
                listView.setAdapter(new ViewShiftAdapter(this, ListOfShifts));
                progressDialog.hide();
            } else {

                GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
            }
        } catch (IndexOutOfBoundsException e){
            GET_SHIFTS_FROM_DB(LoginFragment.LoggedInUser);
        }

        GET_SHIFTS_FROM_DB();
        progressDialog.hide();

    }

    // TODO STRING RESPONSE
    public void GET_SHIFTS_FROM_DB(String id) {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/getshiftwithempid/"+id;

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // TODO CREATE OBJECT FROM PARSED DATA
                      // Toast.makeText(ShiftActivity.this, "GET SHIFTS FROM DB?", Toast.LENGTH_LONG).show();
                        CreateObjectFromResponse(response);
                        // TODO PARSE TO JSON TEST
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");
                Toast.makeText(ShiftActivity.this, RESPONSE, Toast.LENGTH_SHORT).show();
                progressDialog.hide();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // TODO STRING RESPONSE
    public void GET_SHIFTS_FROM_DB() {
        // TODO Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // TODO  Request a string response from the provided URL.
        String url = "http://rosterrest.azurewebsites.net/api/shiftvalues/getshifts";

        // TODO Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // TODO CREATE OBJECT FROM PARSED DATA
                        //Toast.makeText(ShiftActivity.this, "GET AGAIN??", Toast.LENGTH_LONG).show();
                        CreateShiftFromResponse(response);
                        // TODO PARSE TO JSON TEST
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String RESPONSE = ("That didn't work!");
                Toast.makeText(ShiftActivity.this, RESPONSE, Toast.LENGTH_SHORT).show();
                progressDialog.hide();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void CreateObjectFromResponse(String response){
        parseResponse = new ParseResponseShift(response);
        ListOfShifts = parseResponse.Parse();
        // TODO pass data to Adapter
        ArrayList<Shift> shiftArrayList = new ArrayList<Shift>();
        for (Shift s : ListOfShifts){
            if(!s.isGenStarPhoto()){
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
                String [] dateShiftArray = s.getDate().split("/");
                double date = System.currentTimeMillis();
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM, yyyy");
                String dateString = sdf1.format(date);
                String [] actaulDateArray = dateString.split(" ");

                int shiftDate = Integer.parseInt(dateShiftArray[0]);
                int currentDate = Integer.parseInt(actaulDateArray[0]);
                int shiftMonth = Integer.parseInt(dateShiftArray[1]);
                String Month = actaulDateArray[1].replace(",", "");
                int currentMonth = MonthHastTable.get(Month);
                if (shiftMonth > currentMonth){
                    shiftArrayList.add(s);
                }
                else if (shiftMonth == currentMonth){
                    if (shiftDate > currentDate){
                        shiftArrayList.add(s);
                    }
                }
            }
        }
        if (shiftArrayList.size() > 0) {
            listView.setAdapter(new ViewShiftAdapter(this, shiftArrayList));
        } else {
            new Restaurant(ShiftActivity.this, "No shifts match swap criteria", Snackbar.LENGTH_LONG).setBackgroundColor(Color.GRAY).show();
        }

    }

    // Converts JSON data from response into Objects and stores them in a list
    public void CreateShiftFromResponse(String response){
        parseResponse = new ParseResponseShift(response);
        ListOfAllShifts = parseResponse.Parse();

    }

    public void AlertForListView() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("'Swing' this shift?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Swap swap = getSwapData();
                        Shift shift_old = getShift();
                        swap.setLookingForShift(true);
                        // Converts a swap object to a shift object
                        Shift shift_new = ConvertSwapToShift(swap);
                            // Checks to see if the shift you want to swaps date matches the current date
                            if (shift_new.getDate().equals(shift_old.getDate())){
                                new Restaurant(ShiftActivity.this, "This shift is commencing today. Select another", Snackbar.LENGTH_LONG)
                                        .setTextColor(Color.WHITE).show();
                            }
                            // makes sure the each shift has the same level of hours
                            String [] shiftTimeArray1 = shift_new.getTimeOfShift().split("-");
                            String [] shiftTimeArray2 = shift_old.getTimeOfShift().split("-");
                            int lengthOfShift1 = Math.abs(Integer.parseInt(shiftTimeArray1[0].replace(":", "")) - Integer.parseInt(shiftTimeArray1[1].replace(":", "")));
                            int lengthOfShift2 = Math.abs(Integer.parseInt(shiftTimeArray2[0].replace(":", "")) - Integer.parseInt(shiftTimeArray2[1].replace(":", "")));

                            if ((lengthOfShift1 - lengthOfShift2) != 0){

                                new Restaurant(ShiftActivity.this, "Shift must contain same level of hours!", Snackbar.LENGTH_LONG)
                                        .setTextColor(Color.WHITE).show();
                            }

                            else {
                                httpCall.UPDATE_SWAP_IN_DB(swap, shift_old, URL_PUT_SWAP, swap.getID());
                                // TODO Need to updated both shifts in the Swap
                                shift_new.setGenStarPhoto(false);
                                shift_new.setGenSwingPhoto(true);
                                shift_old.setGenStarPhoto(false);
                                shift.setGenSwingPhoto(true);

                                httpCall.UPDATE_SHIFT_IN_DB(shift_new,URL_PUT_SHIFT ,shift_new.getID());
                                httpCall.UPDATE_SHIFT_IN_DB(shift_old,URL_PUT_SHIFT ,shift_old.getID());

                                Intent intent = new Intent(ShiftActivity.this, SwipeMainActivity.class);
                                intent.putExtra("REFRESH", true);
                                startActivity(intent);
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
        alert.show();//showing the dialog
    }

    // TODO get Shift Object
    public Shift getShift(){
        Shift shift = this.shift;
        return shift;
        }

    public void setShift(Shift s){
        this.shift = s;
    }

    public Swap getSwapData(){
        Intent intent = getIntent();
        Swap swap = (Swap) intent.getSerializableExtra(KEY);
        return swap;
    }

    public Shift ConvertSwapToShift(Swap s){
        // for each shift in list of all shifts in DB
        for (Shift shift : ListOfAllShifts){
            // if shift empID equals swap emp ID
            if (s.getEmployeedID().equals(shift.getEmployeeID())){
                // if shift ID equals swap shiftID
                if(s.getShiftID().equals(shift.getID())) {
                    return shift;
                }
            }
        }

        return null;
    }
}




