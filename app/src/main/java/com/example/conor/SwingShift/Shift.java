package com.example.conor.SwingShift;

import java.io.Serializable;

/**
 * Created by conor on 11/05/2016.
 */
// {"EmployeeID":1,"StartTime":10,"EndTime":19,"Day":"Monday","Date":"1/12/16","ListOfEmployeesWorkingShift":[]}
public class Shift implements Serializable {

    private String ID;
    private String EmployeeID;
    private String TimeOfShift;
    private String Day;
    private String Date;
    private boolean genSwingPhoto;
    private boolean genStarPhoto;

    public Shift() {};

//    public Shift(String id, String employeeID) {
//        this.EmployeeID = employeeID;
//        this.ID = id;
//    }

    public Shift (String id, String empid, String TimeOfShift, String day, String date, boolean swingPhoto, boolean starPhoto){
        this.ID = id;
        this.EmployeeID = empid;
        this.TimeOfShift = TimeOfShift;
        this.Day = day;
        this.Date = date;
        this.genSwingPhoto = swingPhoto;
        this.genStarPhoto = starPhoto;
    }

    public boolean isGenStarPhoto() {
        return genStarPhoto;
    }

    public void setGenStarPhoto(boolean genStarPhoto) {
        this.genStarPhoto = genStarPhoto;
    }

    public boolean isGenSwingPhoto() {
        return genSwingPhoto;
    }

    public void setGenSwingPhoto(boolean genSwingPhoto) {
        this.genSwingPhoto = genSwingPhoto;
    }

    public String getDay() {
        return Day;
    }

    public String getDate() {
        return Date;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public String getTimeOfShift() {
        return TimeOfShift;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String toStringSift(){
        return  "Day:" + getDay() + "," + "Date:" + getDate() + "StartTime:" + getTimeOfShift();
    }

}
