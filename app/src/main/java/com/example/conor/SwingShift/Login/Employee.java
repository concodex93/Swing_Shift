package com.example.conor.SwingShift.Login;

import java.io.Serializable;

/**
 * Created by conor on 19/05/2016.
 */
public class Employee implements Serializable {

    private String ID;
    private String EmpID;
    private String Password;
    private String ContractedHours;
    private String Email;
    private String FirstName;
    private String SecondName;
    private String JobTitle;

    public Employee () {}

    public Employee(String id, String empID, String password, String contractedHours, String email,
                    String firstName, String secondName, String jobTitle){
        this.ID = id;
        this.EmpID = empID;
        this.Password = password;
        this.ContractedHours = contractedHours;
        this.Email = email;
        this.FirstName = firstName;
        this.SecondName = secondName;
        this.JobTitle = jobTitle;
    }

    public String getContractedHours() {
        return ContractedHours;
    }

    public void setContractedHours(String contractedHours) {
        ContractedHours = contractedHours;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String username) {
        Email = username;
    }
}
