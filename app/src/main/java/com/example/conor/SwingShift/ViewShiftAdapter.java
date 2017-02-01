package com.example.conor.SwingShift;

/**
 * Created by conor on 12/05/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by conor on 02/05/2016.
 */
public class ViewShiftAdapter extends BaseAdapter {

    // TODO Variables
    ArrayList<Shift> shiftArrayList;
    ArrayList<Swap> swapArrayList;
    private Context mContext;
    private String TimeOfShift;
    private String Day;
    private String Date;
    private String empID;

    public ViewShiftAdapter(Context context){
        mContext = context;
    }

    public ViewShiftAdapter(String timeOfShift, String day, String date){
        this.TimeOfShift = timeOfShift;
        this.Day = day;
        this.Date = date;
    }

    public ViewShiftAdapter(Context context, ArrayList<Shift> shiftList){

        // TODO INITIALIZE ARRAYLIST
        shiftArrayList = new ArrayList<Shift>();
        mContext = context;
        for (Shift s : shiftList){
            try {
                shiftArrayList.add(new Shift(s.getID(), s.getEmployeeID(), s.getTimeOfShift(), s.getDay(), s.getDate(), s.isGenSwingPhoto(), s.isGenStarPhoto()));
            } catch(NullPointerException e){
                //pass
            }
        }

    }

    @Override
    public int getCount() {
        // TODO Return number of elements to return
        return shiftArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // returns object at position
        return shiftArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // returns id of row of which is our array index itself
        // TODO WILL NEED TO RETURN SOMETHING ELSE IF ACCESSING DB
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO get data, put data in view object and return view object
        // TODO Creates new object each time
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.single_row, parent, false);

        // TODO INSTANCIATE UI
        TextView dayTV = (TextView) row.findViewById(R.id.dayTVsr);
        TextView dateTV = (TextView) row.findViewById(R.id.dateTVsr);
        TextView startTimeTV = (TextView) row.findViewById(R.id.startTimeTVsr);
        ImageView imageView3 = (ImageView) row.findViewById(R.id.imageView3);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");

        // TODO CREATE SHIFT OBJECT
        Shift tempShift = shiftArrayList.get(position);
        // array containing date of shift
        String [] dateArray = tempShift.getDate().split("/");
        // creates array containing current date
        double date = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf1.format(date);
        String [] actualDateArray = dateString.split(" ");
        // if current date matches shift date
        try {
            if((Integer.parseInt(dateArray[0])) == Integer.parseInt(actualDateArray[2].replace(",", ""))){
                imageView3.setImageResource(R.mipmap.alarm);
            }
            // if shift is pending
            else if (tempShift.isGenSwingPhoto() && !tempShift.isGenStarPhoto()) {
                imageView3.setImageResource(R.mipmap.swingtransparent);
            }
            // shift resulting from succesful swap
            else if(tempShift.isGenStarPhoto() && !tempShift.isGenSwingPhoto()){
                imageView3.setImageResource(R.mipmap.star_fil);
            }
            // normal shift
            else if(!tempShift.isGenSwingPhoto() && !tempShift.isGenStarPhoto()) {
                imageView3.setImageResource(R.mipmap.staruff);
            }
        } catch (NullPointerException e){}

        dayTV.setText(tempShift.getDay());
        dayTV.setTextColor(Color.BLACK);
        dayTV.setTypeface(typeface);

        dateTV.setText(tempShift.getDate());
        dateTV.setTextColor(Color.BLACK);
        dateTV.setTypeface(typeface);

        startTimeTV.setText(tempShift.getTimeOfShift());
        startTimeTV.setTextColor(Color.BLACK);
        startTimeTV.setTypeface(typeface);

        // TODO RETURN MODIFIED VIEW
        return row;
    }
}
