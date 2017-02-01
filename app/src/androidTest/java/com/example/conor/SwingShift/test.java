package com.example.conor.SwingShift;

import android.test.InstrumentationTestCase;

import com.example.conor.SwingShift.Login.Employee;
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.SwipeView.ShiftFragment;

import java.util.ArrayList;

/**
 * Created by conor on 03/06/2016.
 */
public class test extends InstrumentationTestCase {

    ShiftActivity mainActivity;
    LoginFragment loginFragment;
    ShiftFragment shiftFragment;

    public void test() throws Exception {
        final int expected = 5;
        final int reality = 5;
        assertEquals(expected, reality);
    }

    public void test_getShifts() throws Exception{
        Shift actual = new Shift();
        mainActivity = new ShiftActivity();
        mainActivity.setShift(actual);
        Shift expected = mainActivity.getShift();
        assertEquals(expected, actual);
    }

    public void test_ConvertSwapToShift() throws Exception{
        mainActivity = new ShiftActivity();
        String response = "[{\"ID\":3,\"EmployeeID\":1001,\"TimeOfShift\":\"10:30-14:30\",\"Day\":\"Thursday\",\"Date\":\"2/5/2016\",\"genSwingPhoto\":false,\"genStarPhoto\":false,\"ListOfShifts\":[]},{\"ID\":5,\"EmployeeID\":1001,\"TimeOfShift\":\"12:30—21:00\",\"Day\":\"Saturday\",\"Date\":\"4/6/2016\",\"genSwingPhoto\":true,\"genStarPhoto\":false,\"ListOfShifts\":[]}]";
        mainActivity.CreateShiftFromResponse(response);
        Swap swap = new Swap("3", "1001");
        Shift expected = mainActivity.ConvertSwapToShift(swap);
        Shift actual = new Shift("3", "1001","10:30-14:30", "Thursday", "2/5/2016", false, false);
        assertEquals(expected.getID(), actual.getID());
        assertEquals(expected.getEmployeeID(), actual.getEmployeeID());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getTimeOfShift(), actual.getTimeOfShift());
        assertEquals(expected.getDay(), actual.getDay());

    }

    public void test_parseShift() throws Exception{
        String response = "{\"ID\":3,\"EmployeeID\":1001,\"TimeOfShift\":\"10:30-14:30\",\"Day\":\"Thursday\",\"Date\":\"2/5/2016\",\"genSwingPhoto\":false,\"genStarPhoto\":false,\"ListOfShifts\":[]}";
        ParseResponseShift parseResponseShift = new ParseResponseShift(response);
        ArrayList<Shift> arrayList = parseResponseShift.Parse();
        Shift expected = arrayList.get(0);
        Shift actual = new Shift("3", "1001", "10:30-14:30", "Thursday", "2/5/2016", false, false);
        assertEquals(expected.getID(), actual.getID());
        assertEquals(expected.getEmployeeID(), actual.getEmployeeID());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getTimeOfShift(), actual.getTimeOfShift());
        assertEquals(expected.getDay(), actual.getDay());

    }

    public void test_parseSwap() throws Exception {
        String response = "[{\"ID\":1,\"EmployeeID\":1002,\"ShiftID\":6,\"LookingForShift\":false,\"EmployeeSwapID\":0,\"ShiftToSwapID\":0,\"Authorized\":false},{\"ID\":18,\"EmployeeID\":1001,\"ShiftID\":8,\"LookingForShift\":false,\"EmployeeSwapID\":0,\"ShiftToSwapID\":0,\"Authorized\":false}]";
        ParseResponseSwap parseResponseSwap = new ParseResponseSwap(response);
        ArrayList<Swap> arrayList = parseResponseSwap.ParseSWAP();
        Swap expected = arrayList.get(0);
        Swap actual = new Swap("1", "1002", "6", false, "0", "0", false);
        assertEquals(expected.getID(), actual.getID());

    }

    public void test_CheckEmployee() throws Exception{
        loginFragment = new LoginFragment();
        String response = "[{\"ID\":1,\"EmpID\":1001,\"Password\":\"abc\",\"ContractedHours\":35,\"Email\":\"Email@375\",\"FirstName\":\"Jack\",\"SecondName\":\"AllTrades\",\"JobTitle\":\"FLOORSTAFF\",\"ListOfShifts\":null},{\"ID\":2,\"EmpID\":1002,\"Password\":\"abc\",\"ContractedHours\":35,\"Email\":\"Email@2\",\"FirstName\":\"Jack\",\"SecondName\":\"AllTrades\",\"JobTitle\":\"FLOORSTAFF\",\"ListOfShifts\":null}]";
        loginFragment.CreateObjectFromResponse(response);
        Employee actual = new Employee("1", "1001", "abc", "35", "Email@375", "Jack", "AllTrades", "FLOORSTAFF");
        Employee expected = loginFragment.CheckEmployeeID(actual.getEmpID());
        assertEquals(expected.getID(), actual.getID());

    }

}
