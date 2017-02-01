package com.example.conor.SwingShift;

import java.io.Serializable;

/**
 * Created by conor on 16/05/2016.
 */
public class Swap implements Serializable {

    private String ID;
    private String ShiftID;
    private String EmployeedID;
    private String EmployeeSwapID;
    private String ShiftIDToSwap;
    private boolean LookingForShift;
    private boolean Authorized;

    public Swap () {};

    public Swap (String shiftID, String employeedID) {
        this.EmployeedID = employeedID;
        this.ShiftID = shiftID;
    }

    public Swap(String id, String empid, String shiftID, boolean lookingForShift, String EmployeeSwapID, String shiftToSwap, boolean authorized){
        this.ID = id;
        this.EmployeedID = empid;
        this.ShiftID = shiftID;
        this.LookingForShift = lookingForShift;
        this.EmployeeSwapID = EmployeeSwapID;
        this.ShiftIDToSwap = shiftToSwap;
        this.Authorized = authorized;
    }

    public String getShiftIDToSwap() {
        return ShiftIDToSwap;
    }

    public void setShiftIDToSwap(String shiftIDToSwap) {
        ShiftIDToSwap = shiftIDToSwap;
    }

    public boolean isAuthorized() {
        return Authorized;
    }

    public void setAuthorized(boolean authorized) {
        Authorized = authorized;
    }

    public String getEmployeedID() {
        return EmployeedID;
    }

    public void setEmployeedID(String employeedID) {
        EmployeedID = employeedID;
    }

    public String getEmployeeSwapID() {
        return EmployeeSwapID;
    }

    public void setEmployeeSwapID(String employeeSwapID) {
        EmployeeSwapID = employeeSwapID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isLookingForShift() {
        return LookingForShift;
    }

    public void setLookingForShift(boolean lookingForShift) {
        LookingForShift = lookingForShift;
    }

    public String getShiftID() {
        return ShiftID;
    }

    public void setShiftID(String shiftID) {
        ShiftID = shiftID;
    }




}
