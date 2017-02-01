package com.example.conor.SwingShift;

import java.util.ArrayList;

/**
 * Created by conor on 17/05/2016.
 */
public class ParseResponseSwap {

    // TODO Variables
    private String responseToParse;
    Swap swap;
    private ArrayList<Swap> ListOfSwaps = new ArrayList<Swap>();


    private String ID;
    private String ShiftID;
    private String EmployeeID;
    private String EmployeeSwapID;
    private String ShiftIDToSwap;
    private String LookingForShift;
    private String Authorized;

    // TODO Array to store response
    public String[] largeResponseArray;
    public String[] mArray;

    public ParseResponseSwap(String responseToParse) {
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

    public ArrayList<Swap> ParseSWAP() {
        mArray = ParseLargeResponse();

        for (int i = 0; i <= mArray.length; i++) {
            try {
                // TODO Populate array with response at comma breaks
                String [] array = mArray[i].split(",");

                if (array[0].equals("")) {
                    ID = array[1].replace("ID:", "");
                    EmployeeID = array[2].replace("EmployeeID:", "");
                    ShiftID = array[3].replace("ShiftID:", "");
                    LookingForShift = array[4].replace("LookingForShift:", "");
                    EmployeeSwapID = array[5].replace("EmployeeSwapID:", "");
                    ShiftIDToSwap = array[6].replace("ShiftIDToSwap:", "");
                    Authorized = array[7].replace("Authorized:", "");
                }
                else {
                    ID = array[0].replace("ID:", "");
                    EmployeeID = array[1].replace("EmployeeID:", "");
                    ShiftID = array[2].replace("ShiftID:", "");
                    LookingForShift = array[3].replace("LookingForShift:", "");
                    EmployeeSwapID = array[4].replace("EmployeeSwapID:", "");
                    ShiftIDToSwap = array[5].replace("ShiftIDToSwap:", "");
                    Authorized = array[6].replace("Authorized:", "");
                }

                // TODO generate shift object
                swap = new Swap(ID, EmployeeID, ShiftID, Boolean.valueOf(LookingForShift), EmployeeSwapID, ShiftIDToSwap, Boolean.valueOf(Authorized));
                ListOfSwaps.add(swap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ListOfSwaps;
    }

}
