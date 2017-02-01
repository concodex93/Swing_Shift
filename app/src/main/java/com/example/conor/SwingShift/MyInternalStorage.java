package com.example.conor.SwingShift;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by conor on 18/05/2016.
 * Ref: http://www.mysamplecode.com/2012/06/android-internal-external-storage.html
 */
public class MyInternalStorage extends Application {

    Context context;

    public MyInternalStorage(Context c){
        this.context = c;
    }

    public void Save(ArrayList<Shift> arrayList, String filename) {
        FileOutputStream fos = null;
        File file = null;

        try {

            for(Shift s: arrayList) {

                String shiftID = s.getID();
                String empID = s.getEmployeeID();
                String startTime = s.getTimeOfShift();
                String day = s.getDay();
                String date = s.getDate();

                // TODO Ensures correct spacing
                String _shiftID = shiftID + ",";
                String _empID = empID + ",";
                String _startTime = startTime + ",";
                String _day = day + ",";
                String _date = date + " ";

                fos = context.openFileOutput(filename,MODE_APPEND);
                file = context.getFilesDir();

                fos.write(_shiftID.getBytes());
                fos.write(_empID.getBytes());
                fos.write(_startTime.getBytes());
                fos.write(_day.getBytes());
                fos.write(_date.getBytes());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Toast.makeText(context.getApplicationContext(), "Saved successfully to" + file.toString(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Shift> Load(String filename) {

        String shiftID = null;
        String empID = null;
        String startTime = null;
        String day = null;
        String date = null;
        String genSwingPhoto = null;
        String genStarPhoto = null;

        FileInputStream fis = null;
        String [] arrayOfAllShifts = null;
        ArrayList<Shift> shiftsLoadMethod = new ArrayList<Shift>();

        try {

            fis = context.openFileInput(filename);
            int read = -1;
            String input = null;
            StringBuffer buffer = new StringBuffer();
            while ((read = fis.read()) != -1) {

                input = String.valueOf(buffer.append((char) read));
            }

            if (input != null) {
                arrayOfAllShifts = input.split(" ");
            }

            if (arrayOfAllShifts != null) {
                for (String s : arrayOfAllShifts){

                    String [] arrayEachShift = s.split(",");

                    shiftID = arrayEachShift[0];
                    empID = arrayEachShift[1];
                    startTime = arrayEachShift[2];
                    day = arrayEachShift[3];
                    date = arrayEachShift[4];
                    genSwingPhoto = arrayEachShift[5];
                    genStarPhoto = arrayEachShift[6];

                    Shift shift = new Shift(shiftID, empID, startTime, day, date, Boolean.valueOf(genStarPhoto), Boolean.valueOf(genSwingPhoto));
                    shiftsLoadMethod.add(shift);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return shiftsLoadMethod;
    }


}
