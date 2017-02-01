package com.example.conor.SwingShift.Login;

import java.util.ArrayList;

/**
 * Created by conor on 19/05/2016.
 */
public class ParseEmployee {

    private String responseToParse;
    private Employee employee;
    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();

    // TODO Fields to populate
    private String ID;
    private String EmpID;
    private String Password;
    private String ContractedHours;
    private String Username;
    private String FirstName;
    private String SecondName;
    private String JobTitle;

    // TODO Array to store response
    public String[] largeResponseArray;
    public String[] mArray;

    public ParseEmployee(String responseToParse) {
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

    public ArrayList<Employee> ParseEmp() {
        mArray = ParseLargeResponse();

        for (int i = 0; i <= mArray.length; i++) {
            try {
                // TODO Populate array with response at comma breaks
                String [] array = mArray[i].split(",");

                if (array[0].equals("")) {
                    ID = array[1].replace("ID:","");
                    EmpID = array[2].replace("EmpID:", "");
                    Password = array[3].replace("Password:", "");
                    ContractedHours = array[4].replace("ContractedHours:","");
                    Username = array[5].replace("Username:","");
                    FirstName = array[6].replace("FirstName:", "");
                    SecondName = array[6].replace("SecondName:", "");
                    JobTitle = array[6].replace("JobTitle:", "");


                }
                else {
                    ID = array[0].replace("ID:", "");
                    EmpID = array[1].replace("EmpID:", "");
                    Password = array[2].replace("Password:", "");
                    ContractedHours = array[3].replace("ContractedHours:", "");
                    Username = array[4].replace("Username:", "");
                    FirstName = array[5].replace("FirstName:", "");
                    SecondName = array[6].replace("SecondName:", "");
                    JobTitle = array[7].replace("JobTitle:", "");
                }

                // TODO generate shift object
                employee = new Employee(ID, EmpID, Password , ContractedHours, Username, FirstName, SecondName, JobTitle);
                employeeArrayList.add(employee);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return employeeArrayList;
    }
}
