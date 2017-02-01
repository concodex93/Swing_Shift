package com.example.conor.SwingShift;
import java.util.ArrayList;

/**
 * Created by conor on 11/05/2016.
 *
 *  {"EmployeeID":1,"StartTime":10,"EndTime":19,"Day":"Monday","Date":"1/12/16","ListOfEmployeesWorkingShift":[]}
 */

public class ParseResponseShift {

    private String responseToParse;
    private Shift shift;
    private ArrayList<Shift> ListOfShits = new ArrayList<Shift>();

    // TODO Fields to populate
    private String EmployeeID;
    private String TimeOfShift;
    private String Day;
    private String Date;
    private String ID;
    private String genSwingPhoto;
    private String genStarPhoto;

    // TODO Array to store response
    public String[] largeResponseArray;
    public String[] mArray;

    public ParseResponseShift(String responseToParse) {
        this.responseToParse = responseToParse.replaceAll("[\"\\{\\[\\.\\]]","");
    }

    public String[] ParseLargeResponse() {
        try {
            // TODO Populate array with response at comma breaks
            largeResponseArray = responseToParse.split("\\}");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return largeResponseArray;
    }

    public ArrayList<Shift> Parse() {
        mArray = ParseLargeResponse();

        for (int i = 0; i <= mArray.length; i++) {
            try {
                // TODO Populate array with response at comma breaks
                String [] array = mArray[i].split(",");

                if (array[0].equals("")) {
                    ID = array[1].replace("ID:","");
                    EmployeeID = array[2].replace("EmployeeID:", "");
                    TimeOfShift = array[3].replace("TimeOfShift:", "");
                    Day = array[4].replace("Day:","");
                    Date = array[5].replace("Date:", "");
                    genSwingPhoto = array[6].replace("genSwingPhoto:", "");
                    genStarPhoto = array[7].replace("genStarPhoto:", "");
                }
                else {
                    ID = array[0].replace("ID:","");
                    EmployeeID = array[1].replace("EmployeeID:", "");
                    TimeOfShift = array[2].replace("TimeOfShift:", "");
                    Day = array[3].replace("Day:","");
                    Date = array[4].replace("Date:", "");
                    genSwingPhoto = array[5].replace("genSwingPhoto:", "");
                    genStarPhoto = array[6].replace("genStarPhoto:", "");
                }

                // TODO generate shift object
                shift = new Shift(ID, EmployeeID,TimeOfShift , Day, Date, Boolean.valueOf(genSwingPhoto), Boolean.valueOf(genStarPhoto));
                ListOfShits.add(shift);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ListOfShits;
    }

}
