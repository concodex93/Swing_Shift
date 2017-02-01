package com.example.conor.SwingShift;

/**
 * Created by conor on 13/05/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class SwapShiftAdapter extends BaseAdapter {

    // TODO Variables
    ArrayList<Shift> shiftArrayList;
    private Context mContext;

    public SwapShiftAdapter(Context context, ArrayList<Shift> shiftList){

        // TODO INITIALIZE ARRAYLIST
        shiftArrayList = new ArrayList<Shift>();
        mContext = context;

        for (Shift s : shiftList){
            try {
                shiftArrayList.add(new Shift(s.getID(), s.getEmployeeID(), s.getTimeOfShift(),  s.getDay(), s.getDate(), s.isGenSwingPhoto(), s.isGenStarPhoto()));
            } catch(NullPointerException e){

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
        final View row = inflater.inflate(R.layout.single_row2, parent, false);

        // TODO INSTANCIATE UI
        TextView dayTV = (TextView) row.findViewById(R.id.dayTV2);
        TextView dateTV = (TextView) row.findViewById(R.id.dateTV2);
        TextView startTimeTV = (TextView) row.findViewById(R.id.startTimeTV2);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Roboto-Regular.ttf");

        // TODO CREATE SHIFT OBJECT
        Shift tempShift = shiftArrayList.get(position);

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
